package agora;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import agora.model.Subscriber;
@Entity
@Table(name="users")
public class User implements UserDetails {
    public int id;
    public String username;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    private Date subStartDate;
    private String contact;
    private String notes;
    boolean enabled = true;
    Integer deleted = 1;
    public Subscriber company;
    
    public List<Role> roles = new ArrayList<Role>();
    public List<UserQuery> editorsDocketQueries = new ArrayList<UserQuery>();
    public List<UserQuery> rssQueries = new ArrayList<UserQuery>();
    
    public String toString() {
    	return "User[name=" + username + ",roles=" + company.roles + "]";
    }

    @Transient
    @Transactional
    public Collection<GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> roles =  new ArrayList<GrantedAuthority>();
    	List<Role> rolesFromCompany = company.getRoles();
    	
    	if(company.getName().trim().equals("essentia")) {
    		roles.addAll(this.getRoles());
    	} else {
    		roles.addAll(rolesFromCompany);
    	}
    	/*rolesFromCompany.addAll(roles);*/
    	return roles;
    }

    @Transient
    public boolean isAccountNonExpired() {
    	return true;
    }

    @Transient
    public boolean isAccountNonLocked() {
    	return true;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
    	return true;
    }

    @Column(name="enabled")
    public boolean isEnabled() {
    	return enabled;
    }

    /* non UserDetails methods */
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="user_id", unique=true, nullable=false)
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() { return username; }
    
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="first_name")    
    public String getFirstName() { return firstName; }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name="last_name")
    public String getLastName() { return lastName; }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPassword() { return password; }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="email_id")
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @ManyToOne
    @JoinColumn(name="comp_id")
    public Subscriber getCompany() { return company; }
    
    public void setCompany(Subscriber company) {
        this.company = company;
    }

    @OneToMany(cascade=CascadeType.ALL/*, fetch=FetchType.EAGER*/)
    @JoinColumn(name="user_id")
    @Where(clause="type='ED'")
    public List<UserQuery> getEditorsDocketQueries() {
	return editorsDocketQueries;
    }

    public void setEditorsDocketQueries(List<UserQuery> editorsDocketQueries) {
	this.editorsDocketQueries = editorsDocketQueries;
    }

    @OneToMany(cascade=CascadeType.ALL/*, fetch=FetchType.EAGER*/)
    @JoinColumn(name="user_id")
    @Where(clause="type='RSS'")
    public List<UserQuery> getRssQueries() {
	return rssQueries;
    }

    public void setRssQueries(List<UserQuery> rssQueries) {
	this.rssQueries = rssQueries;
    }

    @Column(name="sub_start_date")
	public Date getSubStartDate() {
		return subStartDate;
	}

	public void setSubStartDate(Date subStartDate) {
		this.subStartDate = subStartDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name="is_deleted")
	public Integer isDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

   
    /*
     * Below code is Written for ACL , Not in Scope for now (08-2014).
     * 
    public List<UserPermissionAction> allowedpermisions;
	public List<UserPermissionAction> hiddenpermisions; 
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @Where(clause="state='S'")
    @Fetch(value = FetchMode.SUBSELECT)
	public List<UserPermissionAction> getAllowedpermisions() {
		return allowedpermisions;
	}
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @Where(clause="state='H'")
    @Fetch(value = FetchMode.SUBSELECT)//http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    public List<UserPermissionAction> getHiddenpermisions() {
		return hiddenpermisions;
	}

	public void setAllowedpermisions(List<UserPermissionAction> allowedpermisions) {
		this.allowedpermisions = allowedpermisions;
	}

	public void setHiddenpermisions(List<UserPermissionAction
			
			> hiddenpermisions) {
		this.hiddenpermisions = hiddenpermisions;
	}*/
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="authorities",
	       joinColumns=@JoinColumn(name="user_id"),
	       inverseJoinColumns=@JoinColumn(name="role_id"))
    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) {
    	this.roles = roles;
    }

	
}
