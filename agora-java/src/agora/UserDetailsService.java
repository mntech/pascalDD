package agora;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDetailsService")
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private SessionFactory sf;

    public UserDetails loadUserByUsername(String username) {
	List<User> users = sf.getCurrentSession().createQuery("from User where username = :username and is_deleted = 1")
	    .setString("username", username)
	    .list();
	if (users.size() != 1)
	    return null;
	
	return users.get(0);
    }

    public void saveDomainSet(User user, List<String> domains/* ids */) {
	// domain_set_id = 0 "default" domain set for now
	// clear old
	int domainSetId = 0;
	jt.update("delete from user_domain_set where user_id = ? and domain_set_id = ?",
		  new Object[] {user.id, domainSetId});
	for(String d : domains) {
	    jt.update("insert into user_domain_set (user_id, domain_set_id, domain_id) values (?,?,?)",
		      new Object[] {user.id, domainSetId, d});
	}
    }

    public List<String> getDomainSet(User user /*, String setName = default */) {
	int domainSetId = 0;
	return jt.queryForList("select domain_id from user_domain_set where user_id = ? and domain_set_id = ?",
			       new Object[] {user.id, domainSetId}, String.class);
    }

    public void saveUser(User u) {
    	sf.getCurrentSession().save(u);
    }
    
    public void updateUser(User u) {
    	
    	sf.getCurrentSession().saveOrUpdate(u);
	}
    
    
    
    /*public void deleteUser(User user) {
    	user.setDeleted(2);
    	sf.getCurrentSession().saveOrUpdate(user);
    }*/
    
    public User getUserById(int id) {
    	List<User> users = sf.getCurrentSession().createQuery("from User where id = :id")
        	    .setInteger("id", id).list();
    	return users.get(0);
    }
    
    public User getUserByUsername(String username) {
    	List<User> users = sf.getCurrentSession().createQuery("from User where username = :username")
        	    .setString("username", username).list();
    	if(!users.isEmpty()){
    		return users.get(0);
    	}else{
    		return null;
    	}
    }

    public List<Map<String, Integer>> getEditorsDocketQueryCounts() {
	// a list of queryString,count pairs for editors docket queries
	return null;
    }

    public List<User> getUsersContainingEditorsDocketQueryString(String queryString) {
	return null;
    }

	public List<User> getAllUser() {
		sf.getCurrentSession().flush(); 
		sf.getCurrentSession().clear();
		List<User> users = sf.getCurrentSession().createQuery("from User").list();
		sf.getCurrentSession().refresh(users.get(0));
		return users;
	}
	
	public List<Role> getAllRole() {
		return sf.getCurrentSession().createQuery("from Role").list();
	}
	
	public List<Actions> getAllActions() {
		return sf.getCurrentSession().createQuery("from Actions").list();
	}

	public void ChangeRolePermission(boolean mode, int role, int permission) {
		/*Actions action = (Actions) sf.getCurrentSession().createQuery("from Actions where id = :id")
			    .setInteger("id", permission).uniqueResult();
		Role r = (Role) sf.getCurrentSession().createQuery("Select r from Role r , IN(r.permisions) p where r.id =:id and p.id = :action")
	    .setLong("action", action.getId()).setInteger("id", role).uniqueResult();*/
		
		int count = jt.queryForInt("select count(*) from role_action  where role_id = ? and action_id = ?",
				new Object[] {role, permission});
		
		if(count == 0 && mode == true) {
			jt.update("insert into role_action (role_id, action_id) values (?,?)",
				      new Object[] {role, permission});
		}
		
		if(count == 1 && mode == false) {
			jt.update("delete from role_action where role_id = ? and action_id = ?",
					  new Object[] {role, permission});
		}
		
	}
	
	/*
	 * This method is written for ACL  
	 */
	/*public void ChangeUserPermission(boolean mode, int user, int permission) {
		
		int count = jt.queryForInt("select count(*) from user_action  where user_id = ? and action_id = ?",
				new Object[] {user, permission});
		
		if(count == 0) {
			jt.update("insert into user_action (user_id, action_id, state) values (?,?,?)",
				      new Object[] {user, permission, mode?"S":"H"});
		}
		
		if(count == 1) {
			jt.update("delete from user_action where user_id = ? and action_id = ?",
					  new Object[] {user, permission});
			
			jt.update("insert into user_action (user_id, action_id, state) values (?,?,?)",
				      new Object[] {user, permission, mode?"S":"H"});
		}
		
	}*/
	
	public void ChangeUserRole(boolean mode, int user, int role) {
		
		int count = jt.queryForInt("select count(*) from authorities  where user_id = ? and role_id = ?",
				new Object[] {user, role});
		
		if(count == 0 && mode == true) {
			jt.update("insert into authorities (role_id, user_id) values (?,?)",
				      new Object[] {role, user});
		}
		
		if(count == 1 && mode == false) {
			jt.update("delete from authorities where role_id = ? and user_id = ?",
					  new Object[] {role, user});
		}
		
	}
	
	public void ChangeCompanyRole(boolean mode, int company, int role) {
		
		int count = jt.queryForInt("select count(*) from company_role  where comp_id = ? and role_id = ?",
				new Object[] {company, role});
		
		if(count == 0 && mode == true) {
			jt.update("insert into company_role (role_id, comp_id) values (?,?)",
				      new Object[] {role, company});
		}
		
		if(count == 1 && mode == false) {
			jt.update("delete from company_role where role_id = ? and comp_id = ?",
					  new Object[] {role, company});
		}
		
	}

	public List<User> getUserByCompany(int company) {
		List<User> users = sf.getCurrentSession().createQuery("from User where comp_id = :company").
				setInteger("company", company).list();
		return users;
		
	}

	
}