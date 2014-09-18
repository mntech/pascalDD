package agora.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import agora.Role;

@Entity
@Table(name="subscriber")
public class Subscriber implements java.io.Serializable {
    private Integer id;
    private String name = "";
    private String email = "";
    private Integer deleted = 1;
    private String notes = "";
    private String phone = "";
    private String contact_name = "";
    private Date createDate;
    public List<Role> roles = new ArrayList<Role>();
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="comp_id", unique=true, nullable=false)
    public Integer getId() { return id; }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="is_deleted")
    public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

    @Column(name="company_name")
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }
    
      
    @Column(name="email")
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        this.email = email;
    }

   
    
    
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="subscriber_role",
	       joinColumns=@JoinColumn(name="comp_id"),
	       inverseJoinColumns=@JoinColumn(name="role_id"))
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Role> getRoles() { return roles; }
    
    @Column(name="notes")
    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Column(name="contact_name")
	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	@Column(name="created_at")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setRoles(List<Role> roles) {
    	this.roles = roles;
    }

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
