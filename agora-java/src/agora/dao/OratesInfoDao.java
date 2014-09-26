package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.OratesInfo;

@Repository("OratesInfoDao")
@Transactional
public class OratesInfoDao {
	@Autowired
	private SessionFactory sf;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sf = sessionFactory;
	}
    public void saveOratesInfo(OratesInfo orates) {
    	sf.getCurrentSession().save(orates);
    }
    public void updateOratesInfo(OratesInfo orates) {
    	
    	sf.getCurrentSession().saveOrUpdate(orates);
	}
	public List<OratesInfo> findOratesInfoAllObject(Integer id) {
		return (List<OratesInfo>) sf.getCurrentSession().createQuery("from OratesInfo where subscription_id = "+id).list();
	}
	public void saveMatrixDataByRowColumn_Name(String column_name,
			String row_name, Integer subId, String value) {
		System.out.println(value);
		if(!value.equals("null")){
			sf.getCurrentSession().createQuery("update OratesInfo  set "+column_name+" = '"+value+"' where Y_names = '"+row_name+"' and subscription_id = '"+subId+"' ").executeUpdate(); 
		}
	}
	public void deleteOratesDataById(Integer subId) {
		sf.getCurrentSession().createQuery("delete from OratesInfo where subscription_id ="+subId).executeUpdate();
		
	}
}