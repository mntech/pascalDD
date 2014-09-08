package agora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * This class is no longer used, This class is made to have ACL permission. 
 * Other code this is not in user but commented is in User.java
 */
//@Entity
@Table(name="user_action")
public class UserPermissionAction {

	@Id
	@Column(name="permission_id")
	private Long id;
	
	@Column(name="state")
	private String state;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="action_id")
	private Actions action; 
	
	public UserPermissionAction(){}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return action.getActionUrl();
	}

	
	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}
	
	


}
