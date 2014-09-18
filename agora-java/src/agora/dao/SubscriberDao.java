package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.Subscriber;

@Repository("subscriberDao")
@Transactional
public class SubscriberDao {
    @Autowired
    private SessionFactory sf;

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sf = sessionFactory;
    }

    public void saveCompany(Subscriber company) {
    	sf.getCurrentSession().save(company);
    }
    public void updateCompany(Subscriber c) {
    	
    	sf.getCurrentSession().saveOrUpdate(c);
	}
    public List<String> getCompanyNameList() {
	return (List<String>)sf.getCurrentSession().createQuery("select name from Subscriber order by name").list();
    }

    public List<Subscriber> getCompanyList() {
	return (List<Subscriber>)sf.getCurrentSession().createQuery("from Subscriber order by name").list();
    }

   
    
    public Subscriber getCompanyById(Integer id) {
    	List<Subscriber> company = sf.getCurrentSession().createQuery("from Subscriber where id = :id")
        	    .setInteger("id", id).list();
    	if(company.size() != 0){
    		return company.get(0);
    	}else{
    		return null;
    	}
    	
    	
    }

    public Subscriber getCompanyByName(String name) {
	List l = sf.getCurrentSession().createQuery("from Subscriber where name = :companyName")
	    .setString("companyName", name).list();
	if (l.size() > 1)
	    throw new RuntimeException("Cannot find a unique company");
	else if (l.size() == 1)
	    return (Subscriber)l.get(0);
	else
	    return null;
    }

    public List<Subscriber> findCompanies(String nameSubstring) {
	return null;
    }
}