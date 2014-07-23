package agora.scraper;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("dataJobs")
@Transactional
public class DataJobs {
    private static final Log log = LogFactory.getLog(DataJobs.class);

    @Autowired
    private SessionFactory sf;

    public void saveScraperServer(ScraperServer server) {
	sf.getCurrentSession().saveOrUpdate(server);
    }

    public void saveScrapeJob(ScrapeJob job) {
	sf.getCurrentSession().saveOrUpdate(job);
    }

    public ScraperServer findServer(String name) {
	List<ScraperServer> servers = (List<ScraperServer>)sf.getCurrentSession().createQuery("from ScraperServer where name = :name").setString("name", name).list();
	if (servers.size() == 1)
	    return servers.get(0);
	else
	    return null;
    }

    public List<ScrapeJob> getScrapeJobQueue() {
	return (List<ScrapeJob>)sf.getCurrentSession().createQuery("from ScrapeJob where scraperServer is null ").list();
    }

    public List<ScraperServer> getScraperServers() {
	return (List<ScraperServer>)sf.getCurrentSession().createQuery("from ScraperServer order by region, name, location").list();
    }

    public List<ScrapeJob> getScrapeJobsForScraperServer(int scraperServerId) {
	return (List<ScrapeJob>)sf.getCurrentSession().createQuery("from ScrapeJob where endTime is null and scraperServer.id = :id").setInteger("id", scraperServerId).list();
    }

    public ScrapeJob getNextJob(ScraperServer scraperServer) {
	List<ScrapeJob> jobs = (List<ScrapeJob>)sf.getCurrentSession()
	    .createQuery("from ScrapeJob where scraperServer is null and region = :region and path.hostset = :hostset order by id")
	    .setMaxResults(1)
	    .setString("region", scraperServer.region)
	    .setString("hostset", scraperServer.hostset)
	    // lock job record to prevent concurrent starts of the same job
	    //.setLockMode("ScraperJob", LockMode.PESSIMISTIC_WRITE)
	    // setLockMode DOESNT WORK - https://hibernate.onjira.com/browse/HHH-5275
	    .setLockOptions(new LockOptions(LockMode.UPGRADE))
	    .list();
	if (jobs.size() == 0)
	    return null;
	ScrapeJob j = jobs.get(0);
	j.scraperServer = scraperServer;
	j.startTime = new Date();
	saveScrapeJob(j);
	return j;
    }
}
