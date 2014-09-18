package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.MediaInfo;

@Repository("MediaInfoDao")
@Transactional
public class MediaInfoDao {
	@Autowired
	private SessionFactory sf;

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

}