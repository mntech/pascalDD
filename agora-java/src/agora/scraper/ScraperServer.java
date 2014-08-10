package agora.scraper;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="scraper_servers")
public class ScraperServer {
    public int id;
    public String name;
    public String region;
    public String location;
    public String hostset;
    public Date lastUpdated;
    public List<ScrapeJob> scrapeJobs;
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="server_id", unique=true, nullable=false)
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="region")
    public String getRegion() { return region; }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    @Column(name="location")
    public String getLocation() { return location; }
    
    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name="hostset")
    public String getHostset() { return hostset; }

    public void setHostset(String hostset) {
	this.hostset = hostset;
    }

    @Column(name="last_updated", insertable=false, updatable=true)
    public Date getLastUpdated() { return lastUpdated; }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Transient
    public List<ScrapeJob> getScrapeJobs() {
	return scrapeJobs;
    }
}
