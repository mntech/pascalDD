package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.Subscriber;
import agora.model.Subscription;
import agora.web.ManageSubscription.SubcriptionClass;

@Repository("ManageSubscriptionDao")
@Transactional
public class ManageSubscriptionDao {
	@Autowired
	private SessionFactory sf;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sf = sessionFactory;
	}
    public void saveSubscription(Subscription sub) {
    	sf.getCurrentSession().save(sub);
    }
    public void updateSubscription(Subscription sub) {
    	
    	sf.getCurrentSession().saveOrUpdate(sub);
	}
	public List<Subscription> findSubscriptionAllObject() {
		return (List<Subscription>) sf.getCurrentSession().createQuery("from Subscription where parent_id = id").list();
	}
	
	public  List<Subscription> findSubscriptionDetailBySubcriptionId(Integer subId) {
		return (List<Subscription>)sf.getCurrentSession().createQuery("from Subscription where parent_id <> id and parent_id = "+subId ).list();
	}

	public Subscription findSubscriptionDetailById(Integer id) {
		return (Subscription)sf.getCurrentSession().createQuery("from Subscription where id = "+id).uniqueResult();
	}

	public Subscription getsubscriptionByTitle(String title) {
		List l = sf.getCurrentSession().createQuery("from Subscription where title = :title")
			    .setString("title", title).list();
			if (l.size() > 1)
			    throw new RuntimeException("Cannot find a unique Subscription");
			else if (l.size() == 1)
			    return (Subscription)l.get(0);
			else
			    return null;
	}

	public Subscription getsubscriptionById(Integer id) {
		List<Subscription> subscription = sf.getCurrentSession().createQuery("from Subscription where id = :id and parent_id= "+id)
        	    .setInteger("id", id).list();
		if(subscription.size() != 0){ 
			return subscription.get(0);
		}else{
			return null;
		}
		
	}
	public Subscription deletesubscriptionById(Integer id) {
		sf.getCurrentSession().createQuery("delete from Subscription where id = "+id+" or parent_id = "+id).executeUpdate();
		return null;
		
	}
	public List<Subscription> findSubscriptionAllObjectById(Integer id) {
		return (List<Subscription>) sf.getCurrentSession().createQuery("from Subscription where parent_id = "+id).list();
	}
	public List<Subscription> getAllSubscription() {
		return (List<Subscription>) sf.getCurrentSession().createQuery("from Subscription").list();
	}
	
	

}