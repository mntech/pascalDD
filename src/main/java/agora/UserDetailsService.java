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
    }

    public List<Map<String, Integer>> getEditorsDocketQueryCounts() {
	// a list of queryString,count pairs for editors docket queries
	return null;
    }

    public List<User> getUsersContainingEditorsDocketQueryString(String queryString) {
	return null;
    }
}