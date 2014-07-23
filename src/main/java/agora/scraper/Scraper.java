package agora.scraper;

import agora.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.*;

@Service("scraper")
@Scope("prototype")
public class Scraper extends Thread {
    private static final Log log = LogFactory.getLog(Scraper.class);

    @Autowired
    Data data;
    @Resource
    Map config;
    @Autowired
    DataJobs dataJobs;

    public ScraperServer scraperServer;
    public ScrapeJob scrapeJob = null;
    public String stage = "init";
    public boolean waitToEnd = false;

    /**
     * Read in the JSON file from the scraper
     */
    public Map<Pull, List<AdImage>> readJson(String jsonFilename) throws IOException {
	JsonFactory js = new JsonFactory();
	ObjectMapper om = new ObjectMapper(js);
	File input = new File(jsonFilename);
	List<Map<String, Object>> o = (List)om.readValue(input, new TypeReference<List<Map<String, Object>>>() {});
	Map<Pull, List<AdImage>> pullResults = new HashMap<Pull, List<AdImage>>();
	String groupId = UUID.randomUUID().toString();
	// list of pull urls
	for (Map<String, Object> m : o) {
	    Pull pull = new Pull((String)m.get("id"));
	    pull.groupId = groupId;
	    pull.path = (String)m.get("url");
	    pull.depth = (Integer)m.get("depth");
	    List<AdImage> ads = new ArrayList<AdImage>();
	    pullResults.put(pull, ads);
	    List<Map<String, Object>> jAds = (List)m.get("ads");
	    for (Map<String, Object> e2 : jAds) {
		AdImage img = new AdImage();
		img.pullId = pull.id;
		img.src = (String)e2.get("src");
		img.html = (String)e2.get("html");
		img.dest = (String)e2.get("dest");
		img.srcId = (Integer)e2.get("selId");
		ads.add(img);
		// make sure we have a full url
		// currently only a problem as $(x)[0].src doesn't return
		// an absolute URL for flash files as it does for images
		if (!img.src.startsWith("http")) {
		    String src = pull.path + "/" + img.src;
		    log.debug("Completing img src from '" + img.src + "' to '" + src + "'");
		    img.src = src;
		}
	    }
	}
	return pullResults;
    }

    /**
     * Download all the ads.
     */
    public void processResults(Map<Pull, List<AdImage>> pullResults) {
	for (Map.Entry<Pull, List<AdImage>> e : pullResults.entrySet()) {
	    Pull p = e.getKey();
	    p.runId = scrapeJob.runId;
	    data.save(p);
	    // save pull
	    for (AdImage img : e.getValue()) {
		String srcFilename = (String)config.get("imgFilePath") + "/" + scrapeJob.runId + "/" + img.imageId;
		// download image
		{
		    String command = (String)config.get("imageDownloadCommand");
		    // need to do this since we are using --globoff with curl as other
		    // chars need to be preserved
		    command = command.replace("SRCPATH", img.src.replaceAll(" ", "%20"));
		    command = command.replace("DESTFILE", srcFilename);
		    log.debug("Downloading file: " + command);
		    StringBuilder out = new StringBuilder();
		    int ret = Util.runCommand(command, out, null);
		    assert(ret == 0);
		    log.debug("curl returns: " + out);
		    if (out.toString().split("_").length < 2) {
			log.debug("invalid curl result: " + out + " for url: " + img.src);
			continue;
		    }
		    img.contentType = out.toString().split("_")[1].replaceAll("^(\\w+)/(.+)?[ ;].*$", "$1/$2");
		    if (!Util.isAdContentType(img.contentType)) {
			// try to check the file directly for a more accurate mime type
			String typeCommand = (String)config.get("imageTypeCommand");
			StringBuilder typeOut = new StringBuilder();
			typeCommand = typeCommand.replace("FILENAME", srcFilename);
			ret = Util.runCommand(typeCommand, typeOut, null);
			assert(ret == 0);
			if (Util.isAdContentType(typeOut.toString())) {
			    log.debug("Updating image content type to " + typeOut);
			    img.contentType = typeOut.toString();
			} else {
			    log.debug("content type is not an image: " + img.contentType + " for url: " + img.src);
			    continue;
			}
		    }
		}
		{
		    StringBuilder out = new StringBuilder();
		    int ret = Util.runCommand("md5sum " + srcFilename, out, null);
		    assert(ret == 0);
		    img.md5sum = out.toString().replaceAll("^([^ ]+) .*$", "$1").trim();
		}
		{
		    StringBuilder out = new StringBuilder();
		    String command = (String)config.get("imageSizeCommand");
		    command = command.replace("FILENAME", srcFilename);
		    int ret = Util.runCommand(command, out, null);
		    assert(ret == 0);
		    img.size = out.toString().replaceAll("\n.*", "");
		    if (img.size.equals("1x1")) {
			log.debug("Skipping small img: " + img);
			continue;
		    }
		}
		// save image
		//log.debug("Saving ad image: " + img);
 		data.save(img);
		// copy (if necessary) new image to storage area
		if (img.newImage) {
		    log.debug("Saving new image: " + img.filename);
		    String command = (String)config.get("imgCopyCommand");
		    command = command.replace("SRCFILENAME", srcFilename);
		    command = command.replace("DESTFILENAME", ((String)config.get("imgPermPath")) + "/" + img.filename);
		    Util.runCommandBlindEnsureSuccess(command);
		}
	    }
	}
    }

    /**
     * Run the PhantomJS scraper to get the list of ads to download.
     */
    public String scrapeSite(Path p) {
	String filename = p.id + "-" + new SimpleDateFormat("yyyyMMdd-HH_mm_ss").format(new Date()) + ".txt";
	String outfile = ((String)config.get("outFilePath")) + "/" + scrapeJob.runId + "/" + filename;
	try {
	    // pull ad links
	    final Thread thisThread = Thread.currentThread();
	    final int MAX_TRIES = 2;
	    for (int tryCount = 1; tryCount < MAX_TRIES + 1; ++tryCount) {
		String command = (String)config.get("pullCommand");
		command = command.replace("URL", p.path);
		command = command.replace("HOSTID", p.id);
		command = command.replace("DEPTH", ""+p.depth);
		command = command.replace("OUTFILE", outfile);
		command = command.replace("LOGFILE", ((String)config.get("logFilePath")) + "/" + scrapeJob.runId + "/" + filename);
		log.debug("Running: " + command);
		// timeout if the scrape takes too long
		Runnable r = new Runnable() {
			public void run() {
			    try {
				Thread.sleep(25*60000);
				thisThread.interrupt();
			    } catch(InterruptedException ex) {
			    }
			}
		    };
		Thread timeout = new Thread(r);
		timeout.start();
		// try running scraper
		try {
		    Util.runCommandBlindEnsureSuccess(command);
		    break;
		} catch(RuntimeException ex) {
		    log.debug("caught exception while scraping", ex);
		    // rethrow if its not the interrupt() we caused
		    if (ex.getCause() != null &&
			!InterruptedException.class.isAssignableFrom(ex.getCause().getClass()))
			throw ex;
		    log.error("timed out, try #" + tryCount, ex);
		} finally {
		    timeout.interrupt();
		}
		// cleanup stuck phantomjs processes
		try {
		    Util.runCommandBlindEnsureSuccess((String)config.get("killallPhantomCommand"));
		} catch(Exception ex) {
		    // no big deal
		    log.debug("Failed to killall phantomjs processes", ex);
		}
		if (tryCount == MAX_TRIES) {
		    log.error("Failed pulling, max try count reached");
		    return null;
		}
	    }
	    return outfile;
	} catch(Exception ex) {
	    log.error("error pulling path: " + p, ex);
	    return null;
	}
    }

    public void runJob() {
	// create dirs for run
	for (String path : new String[] {"logFilePath", "outFilePath", "imgFilePath"}) {
	    String p = (String)config.get(path);
	    p += "/" + scrapeJob.runId;
	    new File(p).mkdir();
	}

	// perform scraping
	Path p = data.getPath(scrapeJob.hostId);
	log.info("Starting scrape job " + scrapeJob + "   " + p);
	stage = "scrapeSite";
	String outfile = scrapeSite(p);
	if (outfile != null) {
	    try {
		stage = "readJson";
		Map<Pull, List<AdImage>> pullResults = readJson(outfile);
		stage = "processResults";
		processResults(pullResults);
	    } catch (IOException ex) {
		log.error("Error during scrape: " + p);
		log.error("" + scrapeJob);
		log.error("Error processing results", ex);
	    }
	} else {
	    log.warn("Scraper outfile is null for job " + scrapeJob);
	}
	scrapeJob.endTime = new Date();
	dataJobs.saveScrapeJob(scrapeJob);
	log.info("Completed scrape job " + scrapeJob + "   " + p);
	scrapeJob = null;
	stage = "no job";
    }

    public void run() {
	log.info("Starting scraper for " + scraperServer.region + "-" + scraperServer.hostset + ": " + getName());
	while (true) {
	    // end if requested
	    if (waitToEnd) {
		log.info("Thread stopping by request: " + getName());
		return;
	    }

	    // start next job, sleep if none
	    while (scrapeJob == null) {
		try {
		    scrapeJob = dataJobs.getNextJob(scraperServer);
		} catch(Exception ex) {
		    log.error("Failed getting scrape job", ex);
		}
		if (scrapeJob == null) {
		    try {
			Thread.sleep(60000);
		    } catch(InterruptedException ex) { }
		}
	    }

	    runJob();
	}
    }
}
