package agora.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agora.model.PratesInfo;

@Repository("PratesInfoDao")
@Transactional
public class PratesInfoDao {
	@Autowired
	private SessionFactory sf;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sf = sessionFactory;
	}
    public void savePratesInfo(PratesInfo orates) {
    	sf.getCurrentSession().save(orates);
    }
    public void updatePratesInfo(PratesInfo orates) {
    	
    	sf.getCurrentSession().saveOrUpdate(orates);
	}
	public List<PratesInfo> findPratesInfoAllObject(Integer id) {
		return (List<PratesInfo>) sf.getCurrentSession().createQuery("from PratesInfo where subscription_id = "+id).list();
	}
	public void savePratesDataByRowColumn_Name(String column_name,
			String row_name, Integer subId, String value) {
		System.out.println(value);
		if(!value.equals("null") && !value.equals("undefined")){
			sf.getCurrentSession().createQuery("update PratesInfo  set "+column_name+" = '"+value+"' where prates_Yname = '"+row_name+"' and subscription_id = '"+subId+"' ").executeUpdate(); 
		}
	}
	public void deletePratesDataById(Integer subId) {
		sf.getCurrentSession().createQuery("delete from PratesInfo where subscription_id ="+subId).executeUpdate();
		
	}
}