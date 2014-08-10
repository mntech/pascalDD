package agora.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import agora.Actions;
import agora.Json;
import agora.Role;
import agora.User;
import agora.UserDetailsService;
import agora.UserPermissionAction;

@Controller
public class Activator {

	@Autowired
    private UserDetailsService userDetailsService;
	
	
	@RequestMapping(value="/manage-permission", method=RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	//@ResponseBody 
	@Transactional
	public  String getpermissions(ModelMap map) {
		List<User> users = userDetailsService.getAllUser();
		List<Role> roles = userDetailsService.getAllRole();
		List<Actions> actions = userDetailsService.getAllActions();
		map.addAttribute("rolesAsJson", Json.stringify(Json.toJson(buildRolesJson(roles, actions))));
		map.addAttribute("usersAsJson", Json.stringify(Json.toJson(buildUserJson(users, actions, roles))));
		
		return "manage-permission";
    }
	
	@RequestMapping(value="/user-permission/{mode}/{user}/{permission}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  String changeuserpermission(@PathVariable boolean mode, @PathVariable int user, @PathVariable int permission ) {
		userDetailsService.ChangeUserPermission(mode, user, permission);
		return "";
    }
	
	@RequestMapping(value="/user-role/{mode}/{user}/{role}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  String changeuserrole(@PathVariable boolean mode, @PathVariable int user, @PathVariable int role) {
		userDetailsService.ChangeUserRole(mode, user, role);
		return "";
    }
	
	@RequestMapping(value="/role-permission/{mode}/{role}/{permission}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  String changerolepermission(@PathVariable boolean mode, @PathVariable int role, @PathVariable int permission) {
		userDetailsService.ChangeRolePermission(mode,role,permission);
		return "";
    }
	
	private List<ByRole> buildRolesJson(List<Role> roles, List<Actions> actions) {
		List<ByRole> byRoles = new ArrayList<Activator.ByRole>(); 
		for(Role _r : roles) {
			ByRole byRole = new ByRole();
			byRole.id = _r.getId();
			byRole.rn = _r.name;
			byRole.hasActions = new ArrayList<HasAction>();
			
			for(Actions _a : actions) {
				HasAction hasAction = new HasAction();
				hasAction.id = _a.getId();
				hasAction.an = _a.getActionName();
				hasAction.st = 0;
				if(_r.getPermisions() != null && _r.getPermisions().contains(_a)) {
					hasAction.st = 1;
				}
				byRole.hasActions.add(hasAction);
			}
			byRoles.add(byRole);
		}
		
		return byRoles;
	}
	
	private List<ByUser> buildUserJson(List<User> users, List<Actions> actions, List<Role> roles) {
		List<ByUser> byUsers = new ArrayList<Activator.ByUser>(); 
		for(User _u : users) {
			ByUser byUser = new ByUser();
			byUser.id = _u.id;
			byUser.un = (_u.getFirstName()==null?"":_u.getFirstName()) + " " + (_u.getLastName()==null?"":_u.getLastName()) + "(" + _u.username + ")";
			byUser.hasActions = new ArrayList<HasAction>();
			byUser.hasRoles = new ArrayList<HasRole>();
			
			for(Role _r : roles) {
				HasRole role = new HasRole();
				role.id = _r.getId();
				role.rn = _r.name;
				role.st = 0;
				if(_u.getRoles().contains(_r)) {
					role.st = 1;
				}
				byUser.hasRoles.add(role);
			}
			for(Actions _a : actions) {
				HasAction hasAction = new HasAction();
				hasAction.id = _a.getId();
				hasAction.an = _a.getActionName();
				hasAction.st = -1;
				
				if(_u.getHiddenpermisions() != null)
				for(UserPermissionAction _upa : _u.getHiddenpermisions()) {
					if(_upa.getAction().equals(_a)) {
						hasAction.st = 0;
						break;
					}
				}
				
				if(_u.getAllowedpermisions() != null)
				for(UserPermissionAction _upa : _u.getAllowedpermisions()) {
					if(_upa.getAction().equals(_a)) {
						hasAction.st = 1;
						break;
					}
				}
				
				byUser.hasActions.add(hasAction);
			}
			byUsers.add(byUser);
		}
		
		return byUsers;
	}
	
	class ByUser {
		public int id;
		public String un;
		public List<HasAction> hasActions;
		public List<HasRole> hasRoles;
	}
	
	class ByRole {
		public int id;
		public String rn;
		public List<HasAction> hasActions; 
	}
	
	class HasAction {
		public int st;
		public Long id;
		public String an;
	}
	
	class HasRole {
		public int st;
		public int id;
		public String rn;
	}
}
