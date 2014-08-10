package agora.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import agora.Actions;
import agora.Role;
import agora.UserPermissionAction;
import agora.User;

@Component("permissionEvaluator")
/**
 * Permission at user level take precedence over role level
 * @author jagbirs
 *
 */
public class EmrPermissionEvaluator  implements PermissionEvaluator  {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		User user = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		List<UserPermissionAction> permissions = user.getHiddenpermisions();
		
		for(UserPermissionAction _p : permissions) {
			if(_p.getAction().getActionUrl().equalsIgnoreCase(permission.toString())) {
				return false;
			}
		}
		
		permissions = user.getAllowedpermisions();
		for(UserPermissionAction _p : permissions) {
			if(_p.getAction().getActionUrl().equalsIgnoreCase(permission.toString())) {
				return true;
			}
		}
		
		for(Role _r : user.getRoles()) {
			List<Actions> actions = _r.getPermisions();
			for(Actions _a : actions) {
				if(_a.getActionUrl().equalsIgnoreCase(permission.toString())) {
					return true;
				}
			}
			
		}
			
		return false;
	}

	@Override
	public boolean hasPermission(Authentication arg0, Serializable arg1,
			String arg2, Object arg3) {
		return false;
	}

}
