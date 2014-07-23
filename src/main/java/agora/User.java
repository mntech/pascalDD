package agora;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;
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

@Entity
@Table(name="users")
public class User implements UserDetails {
    public int id;
    public String username;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public Company company;
    public List<Role> roles = new ArrayList<Role>();
    public List<UserQuery> editorsDocketQueries = new ArrayList<UserQuery>();
    public List<UserQuery> rssQueries = new ArrayList<UserQuery>();

    public String toString() {
	return "User[name=" + username + ",roles=" + roles + "]";
    }

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
	return new ArrayList<GrantedAuthority>(roles);
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

    @Transient
    public boolean isEnabled() {
	return true;
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
    public Company getCompany() { return company; }
    
    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="authorities",
	       joinColumns=@JoinColumn(name="user_id"),
	       inverseJoinColumns=@JoinColumn(name="role_id"))
    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) {
	this.roles = roles;
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
}
