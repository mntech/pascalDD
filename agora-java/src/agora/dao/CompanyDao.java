package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.Company;

@Repository("companyDao")
@Transactional
public class CompanyDao {
    @Autowired
    private  SessionFactory sf;

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sf = sessionFactory;
    }

    public void saveCompany(Company company) {
	sf.getCurrentSession().save(company);
    }

    public List<Company> getCompanyByID(Integer id) {
    	return (List<Company>)sf.getCurrentSession().createQuery("from Company where id = "+id).list();
	}
    public List<String> getCompanyNameList() {
	return (List<String>)sf.getCurrentSession().createQuery("select name from Company order by name").list();
    }

    public List<Company> getCompanyList() {
	return (List<Company>)sf.getCurrentSession().createQuery("from Company order by name").list();
    }

    public Company getCompany(Integer id) {
	return (Company)sf.getCurrentSession().load(Company.class, id);
    }
    
    public List<Company> getCompanyByParentid(Integer id){
    	return (List<Company>)sf.getCurrentSession().createQuery("from Company where parent_id = "+id).list();
    }
    public Company getCompanyByName(String name) {
	List l = sf.getCurrentSession().createQuery("from Company where name = :companyName")
	    .setString("companyName", name).list();
	if (l.size() > 1)
	    throw new RuntimeException("Cannot find a unique company");
	else if (l.size() == 1)
	    return (Company)l.get(0);
	else
	    return null;
    }

    public List<Company> findCompanies(String nameSubstring) {
	return null;
    }

	public List<Company> findUnAssignedNames() {
		return (List<Company>)sf.getCurrentSession().createQuery(" from Company where parent_id is null").list();
	}

	public List<Company> findAssignedNames() {
		return (List<Company>)sf.getCurrentSession().createQuery(" from Company where parent_id =  comp_id").list();
	}

	public List<Company> findAssignedChildNames() {
		return (List<Company>)sf.getCurrentSession().createQuery(" from Company where parent_id  <>  comp_id").list();
	}

	public List<Company> findAssignedChildNames(Integer id) {
		return (List<Company>)sf.getCurrentSession().createQuery(" from Company where parent_id  ="+id ).list();
	}


	public void removeChildsParent(Integer id) {
		sf.getCurrentSession().createQuery("UPDATE Company SET parent_id = NULL WHERE comp_id = "+id).executeUpdate();
	}

	public void removeParent(Integer parentId) {
		sf.getCurrentSession().createQuery("UPDATE Company SET parent_id = NULL WHERE parent_id = "+parentId).executeUpdate();
	}

	public void removechildsubscription(Integer id) {
		sf.getCurrentSession().createQuery("UPDATE Company SET parent_id = NULL WHERE comp_id = "+id).executeUpdate();
	}
	public void addParentSubscription(Integer id) {
		sf.getCurrentSession().createQuery("UPDATE Company SET parent_id = comp_id WHERE comp_id = "+id).executeUpdate();
		
	}

	public void addChildSubscription(Integer cid, Integer pid) {
		sf.getCurrentSession().createQuery("UPDATE Company SET  parent_id = "+pid+"  WHERE comp_id = "+cid).executeUpdate();
		
	}

	
}