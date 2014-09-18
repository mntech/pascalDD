package agora;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements java.io.Serializable, GrantedAuthority {
    public int id;
    public String name;

   
    private List<Actions> permisions; 
    
    @Transient
    public String getAuthority() {
    	return name;
    }
    
    @Id
    @Column(name="role_id")
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="role_action",
	       joinColumns=@JoinColumn(name="role_id"),
	       inverseJoinColumns=@JoinColumn(name="action_id"))
    @Fetch(value = FetchMode.SUBSELECT)//http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
	public List<Actions> getPermisions() {
		return permisions;
	}

	public void setPermisions(List<Actions> permisions) {
		this.permisions = permisions;
	}
	
	@Override
	public boolean equals(Object obj) {
		return id == ((Role)obj).id;
	}
}
