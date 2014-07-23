package agora.scraper;

import agora.*;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scraperJobCreator")
@Transactional
public class ScraperJobCreator {
    private static final Log log = LogFactory.getLog(ScraperJobCreator.class);

    @Autowired
    public Data data;
    @Autowired
    public DataJobs dataJobs;

    public void createJobsUS() {
	createJobs("US");
    }

    public void createJobs(String region) {
	int runId = data.newRun();
	int jobs = 0;
	List<Path> paths = data.getActivePaths();
	for (Path p : paths) {
	    jobs++;
	    ScrapeJob j = new ScrapeJob();
	    j.setPath(p);
	    j.region = region;
	    j.runId = runId;
	    dataJobs.saveScrapeJob(j);
	}

	log.info("Created scraper run " + runId + " with " + jobs + " jobs for region " + region);
    }
}
