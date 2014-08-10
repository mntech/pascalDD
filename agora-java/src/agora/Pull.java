package agora;

import java.util.UUID;
import java.util.Date;

public class Pull {
    public String id;
    public String hostId;
    public String path;
    public String groupId;
    public int depth;
    public int runId;
    public transient Date pulltime;
    public transient int ads;

    public Pull() {}

    public Pull(String hostId) {
	this.hostId = hostId;
	id = UUID.randomUUID().toString();
    }

    public String toString() {
	return "Pull: " + id + "\n\t" +
	    "hostId: " + hostId + "\n\t" +
	    "path: " + path;
    }
    
    public String getId() { return id; }
    
    public String getHostId() { return hostId; }
 
    public Date getPulltime() { return pulltime; }
 
    public String getPath() { return path; }
 
    public int getAds() { return ads; }
    
    public int getDepth() { return depth; }
    
    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    public int getRunId() { return runId; }
    
    public void setRunId(int runId) {
        this.runId = runId;
    }
}
