package agora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hosts")
public class Path {
    public String id;
    public String path;
    public boolean active;
    public String title;
    public String comment;
    public int depth;
    public String hostset;

    public String toString() {
	return id + " / " + path;
    }

    @Id
    @Column(unique=true, nullable=false, insertable=false, updatable=false)
    public String getId() { return id; }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column
    public String getPath() { return path; }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column
    public boolean getActive() { return active; }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Column
    public String getTitle() { return title; }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column
    public String getComment() { return comment; }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column
    public int getDepth() { return depth; }

    public void setDepth(int d) {
	depth = d;
    }

    @Column
    public String getHostset() { return hostset; }

    public void setHostset(String hostset) {
        this.hostset = hostset;
    }
}

