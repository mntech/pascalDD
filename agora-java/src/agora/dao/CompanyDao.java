package agora.dao;

import agora.User;
import agora.model.Company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("companyDao")
@Transactional
public class CompanyDao {
    @Autowired
    private SessionFactory sf;

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sf = sessionFactory;
    }

    public void saveCompany(Company company) {
    	sf.getCurrentSession().save(company);
    }
    public void updateCompany(Company c) {
    	
    	sf.getCurrentSession().saveOrUpdate(c);
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
    
    public Company getCompanyById(Integer id) {
    	List<Company> company = sf.getCurrentSession().createQuery("from Company where id = :id")
        	    .setInteger("id", id).list();
    	return company.get(0);
    	
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
}