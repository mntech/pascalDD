package agora;

import java.util.Date;

public class AdImage {
    public String imageId = java.util.UUID.randomUUID().toString();
    public String imageFileId;
    public String html;
    public String dest;
    public String md5sum;
    public String pullId;
    public String filename;
    public String src;
    public Integer srcId;
    public String size;
    public transient boolean newImage = false;
    public transient Date createDate;
    // public transient String sites;
    public transient String contentType;
    // public transient Integer placements;

    public String toString() {
	return "AdImage: " + imageId + "\n\t" +
	    "imageFileId: " + imageFileId + "\n\t" +
	    "html: " + html + "\n\t" +
	    "dest: " + dest + "\n\t" +
	    "md5sum: " + md5sum + "\n\t" +
	    "pullId: " + pullId + "\n\t" +
	    "filename: " + filename + "\n\t" +
	    "src: " + src + "\n\t" + 
	    "srcId: " + srcId + "\n\t" +
	    "size: " + size;
    }
    
    public int hashCode() {
	return html.hashCode();
    }
    
    public String getImageId() { return imageId; }
    
    public String getImageFileId() { return imageFileId; }
    
    public String getHtml() { return html; }
    
    public String getDest() { return dest; }
    
    public String getMd5sum() { return md5sum; }
    
    public String getPullId() { return pullId; }
    
    public String getFilename() { return filename; }
 
    public boolean getNewImage() { return newImage; }
 
    public Date getCreateDate() { return createDate; }
 
    // public String getSites() { return sites; }
    
    public Integer getSrcId() { return srcId; }
    
    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }
    
    public String getSrc() { return src; }
    
    public void setSrc(String src) {
        this.src = src;
    }
    
    public String getSize() { return size; }
    
    public void setSize(String size) {
        this.size = size;
    }
 
 // public Integer getPlacements() { return placements; }
 
 // public void setPlacements(Integer placements) {
 //     this.placements = placements;
 // }
}
