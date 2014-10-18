package agora.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agora.model.MediaInfo;

@Repository("MediaInfoDao")
@Transactional
public class MediaInfoDao {
	private static final String ORIGINAL_IMAGE = "Original_image.";
	private static final String LOGO_THUMBNAIL = "Logo_thumbnail.";
	@Autowired
	private SessionFactory sf;
	static String rootDir = "/home/mediasub";//TODO
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sf = sessionFactory;
	}
    public void saveMediaInfo(MediaInfo sub) {
    	sf.getCurrentSession().save(sub);
    }
    public void updateMediaInfo(MediaInfo sub) {
    	
    	sf.getCurrentSession().saveOrUpdate(sub);
	}
	public List<MediaInfo> findMediaInfoAllObject() {
		return (List<MediaInfo>) sf.getCurrentSession().createQuery("from MediaInfo where parent_id = id").list();
	}
	
	public  List<MediaInfo> findMediaInfoDetailByMediaInfoId(Integer subId) {
		return (List<MediaInfo>)sf.getCurrentSession().createQuery("from MediaInfo where parent_id <> id and parent_id = "+subId ).list();
	}

	public MediaInfo findMediaInfoDetailById(Integer id) {
		return (MediaInfo)sf.getCurrentSession().createQuery("from MediaInfo where subscription_id= "+id).uniqueResult();
	}

	public MediaInfo findMediaInfoDetailByIdaAndName(int id, String title) {
		return (MediaInfo)sf.getCurrentSession().createQuery("from MediaInfo where title='"+title+"' and subscription_id= "+id ).uniqueResult();
	}
	

	
	public MediaInfo getMediainfoByTitle(String title) {
		List l = sf.getCurrentSession().createQuery("from MediaInfo where title = :title")
			    .setString("title", title).list();
			if (l.size() > 1)
			    throw new RuntimeException("Cannot find a unique MediaInfo");
			else if (l.size() == 1)
			    return (MediaInfo)l.get(0);
			else
			    return null;
	}

	public MediaInfo getMediainfoById(Integer id) {
		List<MediaInfo> mediainfo = sf.getCurrentSession().createQuery("from MediaInfo where id = :id")
        	    .setInteger("id", id).list();
    	return mediainfo.get(0);
	}
	public void deleteSubscriptionInfoById(Integer id) {
		sf.getCurrentSession().createQuery("delete from MediaInfo where subscription_id ="+id).executeUpdate();
		
	}
	public String saveUploadFile(MultipartFile file, Integer subID) {
		String[] fileArr = file.getOriginalFilename().split("\\.");
		String fileExt = fileArr[fileArr.length-1];
	
		createFacilityDir(rootDir, subID);
		
		String fileName = rootDir + File.separator + subID + File.separator + LOGO_THUMBNAIL + fileExt;
		String originalFileName = rootDir + File.separator + subID+File.separator+ORIGINAL_IMAGE + fileExt;
		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());
				File f = new File(fileName);
				Thumbnails.of(originalImage)
				.size(124, 124)
				.toFile(f);
				File _f = new File(originalFileName);
				Thumbnails.of(originalImage).scale(1.0).
				toFile(_f);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			return fileExt;
	}
	
	public static void createFacilityDir(String rootDir, Integer subID) {
		File file3 = new File(rootDir + File.separator+subID);
		if (!file3.exists()) {
			file3.mkdirs();
		}
	}
	public String getImagePathByIdForThumbnail(Integer id, String fileExt) {
		return rootDir + File.separator + id + File.separator + LOGO_THUMBNAIL + fileExt;
	}
	public String getImagePathByIdForOriginal(Integer id, String fileExt) {
		return rootDir + File.separator + id + File.separator + ORIGINAL_IMAGE + fileExt;
		
	}

}