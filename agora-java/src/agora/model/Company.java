package agora.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import agora.Role;

@Entity
@Table(name="company")
public class Company implements java.io.Serializable {
    public Integer id;
    public String name = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String providence = "";
    private String state = "";
    private String zip = "";
    private String country = "";
    private String phone = "";
    private String phone800 = "";
    private String fax = "";
    private String webSite = "";
    private String email = "";
    private Company parent;

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="comp_id", unique=true, nullable=false)
    public Integer getId() { return id; }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="company_name")
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Transient
    public String getAddressLine1() { return addressLine1; }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    
    @Transient
    public String getAddressLine2() { return addressLine2; }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    @Transient
    public String getCity() { return city; }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Transient
    public String getProvidence() { return providence; }
    
    public void setProvidence(String providence) {
        this.providence = providence;
    }
    
    @Transient
    public String getState() { return state; }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Transient
    public String getZip() { return zip; }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    @Transient
    public String getCountry() { return country; }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Transient
    public String getPhone() { return phone; }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Transient
    public String getPhone800() { return phone800; }
    
    public void setPhone800(String phone800) {
        this.phone800 = phone800;
    }
    
    @Transient
    public String getFax() { return fax; }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    @Transient
    public String getWebSite() { return webSite; }
    
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    
    @Transient
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="parent_id")
    public Company getParent() { return parent; }
    
    public void setParent(Company parent) {
        this.parent = parent;
    }
    
    public List<Role> roles = new ArrayList<Role>();
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="company_role",
	       joinColumns=@JoinColumn(name="comp_id"),
	       inverseJoinColumns=@JoinColumn(name="role_id"))
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Role> getRoles() { return roles; }
    
    public void setRoles(List<Role> roles) {
    	this.roles = roles;
    }

	public static List<Company> findUnAssignedNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
