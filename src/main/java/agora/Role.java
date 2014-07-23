package agora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements java.io.Serializable, GrantedAuthority {
    public int id;
    public String name;

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
}
