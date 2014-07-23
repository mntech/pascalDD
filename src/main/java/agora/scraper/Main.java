/******************************
 * Old "one-run" scraper launcher
 **************************************/
package agora.scraper;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import agora.Data;
import agora.Path;

public class Main {
    private static final Log log = LogFactory.getLog(Main.class);

    static void runScrape(final Map config, final Data data) {
	int runId = 0;

	List<Path> paths;

	// create a new run in the db
	runId = data.newRun();
	paths = data.getActivePaths();

	for (final Path p : paths) {
	    long start = System.currentTimeMillis();

	    // final String outfile = scrapeSite(runId, p, config);
	    final String outfile = null;

	    long runtime = System.currentTimeMillis() - start;
	    data.setScrapeTime(runId, p, runtime / 1000);

	    final int runId2 = runId;
	    new Thread(new Runnable() {
		    public void run() {
			try {
			    long start = System.currentTimeMillis();

			    // Map<Pull, List<AdImage>> pullResults = readJson(outfile);
			    // processResults(pullResults, data, (Properties)config, runId2);

			    long runtime = System.currentTimeMillis() - start;
			    data.setProcessTime(runId2, p, runtime / 1000);
			} catch(Exception ex) {
			    log.error("error processing results: " + p, ex);
			}
		    }
		}).start();
	}

	log.info("Updating aggregate data");
	data.updateAggregateData(runId);
    }

    public static void main(String args[]) throws Exception {
	org.apache.log4j.PropertyConfigurator.configure("build/WEB-INF/log4j.properties");

	FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("build/WEB-INF/applicationContext.xml");
	Map config = (Map)ctx.getBean("config");
	Data data = (Data)ctx.getBean("data");

	String scraperOutFile = null;
	int runId = 0;

	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equals("--scraperoutfile")) {
		if (i + 1 == args.length) {
		    System.err.println("Please specify value for scraperoutfile");
		    System.exit(1);
		}
		scraperOutFile = args[++i];
	    } else if (args[i].equals("--runid")) {
		if (i + 1 == args.length) {
		    System.err.println("Please specify run id");
		    System.exit(1);
		}
		try {
		    runId = Integer.parseInt(args[++i]);
		} catch(NumberFormatException ex) {
		    System.err.println("Invalid run id, must be an integer");
		    System.exit(1);
		}
	    } else if (args[i].equals("--runfullscraper")) {
		if (args.length != 1) {
		    System.err.println("Please don't specify any additional arguments with --runfullscraper");
		    System.exit(1);
		}
		runScrape(config, data);
	    }
	    // TODO add option to only *scrape* a site, given a host_id
	}

	// we only need to process one results file
	// if (runId > 1 && scraperOutFile != null) {
	//     System.out.println("Processing only runId = " + runId + " file = " + scraperOutFile);
	//     Map<Pull, List<AdImage>> pullResults = readJson(scraperOutFile);
	//     processResults(pullResults, data, (Properties)config, runId);
	// }
    }
}
