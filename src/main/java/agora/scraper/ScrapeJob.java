package agora.scraper;

import agora.*;
import java.util.Date;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="scraper_jobs")
public class ScrapeJob {
    public int id;
    public String hostId;
    public ScraperServer scraperServer;
    public String region;
    public Date startTime;
    public Date endTime;
    public Date createTime;
    public int runId;
    public Path path;

    public String toString() {
	return "ScrapeJob(id=" + id + ",hostId=" + hostId + ",region=" + region + ",runId=" + runId + ")";
    }
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="job_id", unique=true, nullable=false)
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="hostId", insertable=false, updatable=false)
    public String getHostId() { return hostId; }
    
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="server_id")
    public ScraperServer getScraperServer() { return scraperServer; }
    
    public void setScraperServer(ScraperServer scraperServer) {
        this.scraperServer = scraperServer;
    }
    
    @Column(name="region")
    public String getRegion() { return region; }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    @Column(name="start_time")
    public Date getStartTime() { return startTime; }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    @Column(name="end_time")
    public Date getEndTime() { return endTime; }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name="create_time", insertable=false, updatable=false)
    public Date getCreateTime() { return createTime; }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name="run_id")
    public int getRunId() { return runId; }
    
    public void setRunId(int runId) {
        this.runId = runId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="hostId")
    public Path getPath() { return path; }
    
    public void setPath(Path path) {
        this.path = path;
    }
}
