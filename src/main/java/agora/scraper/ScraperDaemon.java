package agora.scraper;

import java.util.*;
import java.net.*;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service("scraperDaemon")
public class ScraperDaemon {
    private static final Log log = LogFactory.getLog(ScraperDaemon.class);

    @Autowired
    DataJobs dataJobs;
    @Resource
    Map config;
    @Autowired
    ApplicationContext applicationContext;

    private List<Scraper> scrapers = new ArrayList<Scraper>();

    ScraperServer scraperServer;

    private void runConsole() throws Exception {
	ServerSocket ssock;
	int port = 8024;
	while (true) {
	    try {
		ssock = new ServerSocket(port, 1);
		break;
	    } catch(Exception ex) {
		log.error("Console bind to port " + port + " failed", ex);
	    }
	    port--;
	    if (port < 8000) {
		log.error("Cannot bind to a usable port");
		throw new RuntimeException("Cannot bind to a usable port");
	    }
	}
	ssock.setReuseAddress(true);
	log.info("Console bound to port " + port);
	while (true) {
	    Socket client = ssock.accept();
	    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    while (true) {
		out.print("> ");
		out.flush();
		String command = in.readLine();
		if (command == null || command.equals("q")) {
		    out.println("Goodbye");
		    client.close();
		    break;
		} else if (command.equals("shutdown")) {
		    out.println("Shutting down. some non-daemon threads may continue running");
		    client.close();
		    ssock.close();
		    return;
		}
		handleConsoleCommand(command, out);
	    }
	}
    }

    private void handleConsoleCommand(String command, PrintWriter out) {
	if (command.equals("help")) {
	    out.println("----- help -----");
	    out.println("end_thread:\t stop scraper thread (or `all') when current job completes");
	    out.println("new_thread:\t create a new scraper thread");
	    out.println("scraper_details");
	    out.println("scraper_stats");
	    out.println("status");
	} else if (command.startsWith("end_thread ")) {
	    String sname = command.split(" ")[1];
	    for (Scraper s : scrapers) {
		if ("all".equals(sname) || s.getName().equals(sname)) {
		    s.waitToEnd = true;
		    out.println("Signaled " + sname + " to end");
		    return;
		}
	    }
	    out.println("Could not find scraper named " + sname);
	} else if (command.equals("new_thread")) {
	    Scraper s = runNewScraper();
	    out.println("Started new scraper: " + s.getName());
	} else if (command.equals("scraper_details")) {
	    scraperDetails(out);
	} else if (command.equals("scraper_stats")) {
	    scraperStats(out);
 	} else if (command.equals("status")) {
	    out.println("Running scrapers " + scrapers.size());
	} else {
	    out.println("unknown command");
	}
    }

    private void scraperDetails(PrintWriter out) {
	for (Scraper s : scrapers) {
	    out.println("----------------");
	    out.println("Scraper details: " + s.getName() + (s.waitToEnd ? " (waiting to end)" : ""));
	    out.println("Thread state: " + s.getState());
	    out.println("Current job: " + s.scrapeJob);
	    out.println("Scraper stage: " + s.stage);
	}
    }

    private void scraperStats(PrintWriter out) {
	out.println("+--------+-------+----------------+----------------+");
	out.println("| Thread | Job   |     State      |   Stage        |");
	out.println("+--------+-------+----------------+----------------+");
	for (Scraper s : scrapers) {
	    out.printf("|%7s |%6d |%15s |%15s |%n", s.getName(),
		       (s.scrapeJob == null ? -1 : s.scrapeJob.getId()),
		       s.getState(), s.stage);
	}
	out.println("+--------+-------+----------------+----------------+");
    }

    private Scraper runNewScraper() {
	Scraper scraper = (Scraper)applicationContext.getBean("scraper");
	scraper.setName("s_" + (1 + scrapers.size()));
	scraper.scraperServer = scraperServer;
	scrapers.add(scraper);
	log.info("Starting scraper: " + scraper.getName());
	scraper.start();
	return scraper;
    }

    public void run() throws Exception {
	// register with server
	String hostname = java.net.InetAddress.getLocalHost().getHostName();
	scraperServer = dataJobs.findServer(hostname);
	if (scraperServer == null) {
	    scraperServer = new ScraperServer();
	    scraperServer.name = hostname;
	}
	scraperServer.region = (String)config.get("scraper.region");
	scraperServer.location = (String)config.get("scraper.location");
	scraperServer.hostset = (String)config.get("scraper.hostset");
	scraperServer.setLastUpdated(new Date());
	dataJobs.saveScraperServer(scraperServer);

	// naive way to keep scraper server updated
	Runnable r = new Runnable() {
		public void run() {
		    while (true) {
			try {
			    scraperServer.setLastUpdated(new Date());
			    dataJobs.saveScraperServer(scraperServer);
			    Thread.sleep(120*1000);
			} catch(Exception ex) {
			    log.error("Error during scraper_server update", ex);
			}
		    }
		}
	    };
	Thread t = new Thread(r);
	t.setDaemon(true);
	t.start();

	// start job threads
	int concurrentJobs = Integer.parseInt((String)config.get("scraper.concurrent_jobs"));
	for (int i = 0; i < concurrentJobs; ++i) {
	    runNewScraper();
	}

	// start console
	while (true) {
	    log.info("Starting console");
	    try {
		runConsole();
		log.warn("Pausing for console restart");
		Thread.sleep(5000);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }
}