package agora.scraper;

import agora.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ScraperDaemonLauncher {
    private static final Log log = LogFactory.getLog(ScraperDaemonLauncher.class);

    public static void main(String args[]) throws Exception {
	org.apache.log4j.PropertyConfigurator.configure("build/WEB-INF/log4j.properties");

	FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("build/WEB-INF/applicationContext.xml");
	ScraperDaemon scraperDaemon = (ScraperDaemon)ctx.getBean("scraperDaemon");
	scraperDaemon.run();
    }
}
