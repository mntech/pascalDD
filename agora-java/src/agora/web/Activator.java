package agora.web;

import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import agora.Actions;
import agora.Json;
import agora.Role;
import agora.User;
import agora.UserDetailsService;
import agora.dao.SubscriberDao;
import agora.model.Subscriber;
@Controller
public class Activator {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private SubscriberDao subscriberDao;
	
	
	@RequestMapping(value="/manage-permission", method=RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	//@ResponseBody 
	@Transactional
	public  String getpermissions(ModelMap map) {
		List<Role> roles = userDetailsService.getAllRole();
		List<Actions> actions = userDetailsService.getAllActions();
		map.addAttribute("rolesAsJson", Json.stringify(Json.toJson(buildRolesJson(roles, actions))));
		
		return "manage-permission";
    }
	
	@RequestMapping(value="/get-companies", method=RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody 
	@Transactional
	public  List<ByCompany> getCompanies(ModelMap map) {
		 return buildCompanyJson(subscriberDao.getCompanyList(), userDetailsService.getAllRole());
	}
	
	
	private List<ByCompany> buildCompanyJson(List<Subscriber> companies, List<Role> roles) {
		List<ByCompany> byCompanies = new ArrayList<Activator.ByCompany>();	
		for(Subscriber _c: companies) {
			ByCompany byCompany = new ByCompany();
			byCompany.i = _c.getId();
			if(_c.getDeleted() != null){
				byCompany.a = _c.getDeleted().toString();
			}else{
				byCompany.a = "1";
			}
				
			byCompany.n = _c.getName();
			byCompany.ph = _c.getPhone();
			try{
				if(_c.getCreateDate() != null){
		    	 String dt = new SimpleDateFormat("MM/dd/yyyy").format(_c.getCreateDate());
		    	 if(dt != null){
		    		 byCompany.cr = dt.toString();
		    	 }
				}
		    } catch(Exception e) {
		    	e.printStackTrace();	
		    }
			byCompany.cp = _c.getContact_name();
			byCompany.nt = _c.getNotes();
			byCompany.em = _c.getEmail();
			byCompany.rs = new ArrayList<HasRole>();
			for(Role _r : roles) {
				HasRole role = new HasRole();
				role.i = _r.id;
				role.c = 0;
				role.n = _r.name;
				if(_c.roles.contains(_r)) {
					role.c = 1;
				}
				byCompany.rs.add(role);
			}
			byCompanies.add(byCompany);
		}
		return byCompanies;
	}


	@RequestMapping(value="/company-role/{mode}/{company}/{role}", method=RequestMethod.POST)
	@ResponseBody
	public  String changeCompanyRole(@PathVariable boolean mode, @PathVariable int company, @PathVariable int role) {
		userDetailsService.ChangeCompanyRole(mode, company, role);
		return "";
    }
	
	@RequestMapping(value="/get-company/{company}", method=RequestMethod.GET)
	@ResponseBody
	public List<CompanyVM> getCompanyDetails(@PathVariable String company) {
		Subscriber cmps = subscriberDao.getCompanyByName(company);
		
		return null;
		
	}
	@RequestMapping(value="/get-users/{company}", method=RequestMethod.GET)
	@ResponseBody
	public  List<UserVM> getCompanyUser(@PathVariable int company) {
		List<User> users = userDetailsService.getUserByCompany(company);
		List<UserVM> usersVM = new ArrayList<Activator.UserVM>();
		
		for(User u : users) {
			UserVM userVM = new UserVM();
			userVM.fn = (u.firstName == null || u.firstName.length() ==0) ? "" : u.firstName;
			userVM.ln = ((u.lastName == null || u.lastName.length() ==0) ? "" : u.lastName);
			userVM.e = u.email;
			userVM.c = u.getCompany().getName();
			userVM.i = u.id;
			userVM.n = u.firstName + " " + u.lastName; 
			if(u.isDeleted() != null){
				userVM.a = u.isDeleted().toString();
			}
			if(u.getCompany().getName().equalsIgnoreCase("essentia")) {
				if(userVM.roles == null) {
					userVM.roles = new ArrayList<HasRole>();
				}
				List<Role> roles = userDetailsService.getAllRole();
				for (Role _r: roles) {
					HasRole hs = new HasRole();
					hs.i = _r.id;
					hs.c = 0;
					hs.n = _r.name;
					if(u.getRoles().contains(_r)) {
						hs.c = 1;
					}
					userVM.roles.add(hs);
				}
			}
			
			userVM.un = u.username;
			userVM.pw = u.password;
			try {
		    	  String dt = new SimpleDateFormat("MM/dd/yyyy").format( u.getSubStartDate());
		    	  userVM.sd = dt.toString();
		    	} catch(Exception e) {
		    		
		    	}
			
			usersVM.add(userVM);
		}
		return usersVM;
    }
	
	@RequestMapping(value="/get-users", method=RequestMethod.GET)
	@ResponseBody
	public  List<UserVM> getAllUser() {
		List<User> users = userDetailsService.getAllUser();
		List<UserVM> usersVM = new ArrayList<Activator.UserVM>();
		
		for(User u : users) {
			if(u.company != null) {
				UserVM userVM = new UserVM();
				userVM.e = u.email;
				userVM.i = u.id;
				userVM.fn = (u.firstName == null || u.firstName.length() ==0) ? "" : u.firstName;
				userVM.ln = ((u.lastName == null || u.lastName.length() ==0) ? "" : u.lastName);
				userVM.n = userVM.fn + " " + userVM.ln; 
				userVM.un = u.username;
				userVM.pw = u.password;
				if(u.isDeleted() != null){
					userVM.a = u.isDeleted().toString();
				}
				try{
					if(u.getSubStartDate() != null){
			    	 String dt = new SimpleDateFormat("MM/dd/yyyy").format( u.getSubStartDate());
			    	 if(dt != null){
			    		 userVM.sd = dt;
			    	 }
					}
			    } catch(Exception e) {
			    	e.printStackTrace();	
			    }
				userVM.c = u.getCompany() == null? "" : u.getCompany().getName();
				usersVM.add(userVM);
			}
		}
		return usersVM;
    }
	
	@RequestMapping(value="/updateCompany/{name}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  String updateCompany(@PathVariable String name, @RequestBody CompanyVM cmp) {
		System.out.println(cmp.em+"-------");
		Subscriber comp = subscriberDao.getCompanyByName(name);
		comp.setEmail(cmp.em);
		comp.setPhone(cmp.ph);
		comp.setContact_name(cmp.cp);
		comp.setNotes(cmp.nt);
		/*try {
	    	  String dt = new SimpleDateFormat("MM/dd/yyyy").format( cmp.cr);
	    	  comp.setCreateDate(dt);
	    	} catch(Exception e) {
	    		
	    	}*/
		subscriberDao.updateCompany(comp);
		return "Company Updated sucessfully";
		
	}
	@RequestMapping(value="/activeCompany/{id}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  List<UserVM> activateCompany(@PathVariable Integer id) {
		Subscriber cmp = subscriberDao.getCompanyById(id);
		UserVM vm = new UserVM();
		List<UserVM> userList = new ArrayList<UserVM>();
		cmp.setDeleted(1);
		subscriberDao.updateCompany(cmp);
		List<User> users = userDetailsService.getUserByCompany(cmp.getId());
		//Jagbir: If company is made active , User need not to be made active.
		/*if(users.size() != 0){
			for(User usr : users) {
				usr.setDeleted(1);
				userDetailsService.updateUser(usr);
				vm = getUser(usr);
				userList.add(vm);
			}
		}*/
		
		return userList;
		
	}
	@RequestMapping(value="/deleteCompany/{id}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  List<UserVM> deleteCompany(@PathVariable Integer id) {
		UserVM vm = new UserVM();
		List<UserVM> userList = new ArrayList<UserVM>();
		Subscriber cmp = subscriberDao.getCompanyById(id);
		cmp.setDeleted(0);
		subscriberDao.updateCompany(cmp);
		List<User> users = userDetailsService.getUserByCompany(cmp.getId());
		if(users.size() != 0){
			for(User usr : users) {
				usr.setDeleted(0);
				userDetailsService.updateUser(usr);
				vm = getUser(usr);
				userList.add(vm);
			}
		}
		return userList;
	}
	
	@RequestMapping(value="/activeUserOfCompany/{id}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  UserVM activateUserOfCompany(@PathVariable int id) {
		User usr = userDetailsService.getUserById(id);
		usr.setDeleted(1);
		userDetailsService.updateUser(usr);
		UserVM vm = getUser(usr);
		return vm;
	}
	@RequestMapping(value="/deleteUserOfCompany/{id}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  UserVM deleteUserOfCompany(@PathVariable int id) {
		User usr = userDetailsService.getUserById(id);
		usr.setDeleted(0);
		userDetailsService.updateUser(usr);
		UserVM vm = getUser(usr);
		return vm;
		
		
	}	
	@RequestMapping(value="/saveUser/{id}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  String saveUser(@PathVariable int id,@RequestBody UserVM user) {
		User usr = userDetailsService.getUserById( id);
    	usr.firstName = user.fn;
    	usr.lastName = user.ln;
    	usr.password = user.pw;
    	if(!usr.username.equals(user.un)) {
    		if(userDetailsService.getUserByUsername(user.un)!=null){
    			return "Username already in use";
    		};
    	}
    	usr.username = user.un;
    	usr.email = user.e;
    	try {
    	  Date dt = new SimpleDateFormat("MM/dd/yyyy").parse(user.sd);
    	  usr.setSubStartDate(dt);
    	} catch(Exception e) {
    		
    	}
    	userDetailsService.updateUser(usr);
    	for(int j = 0; j<user.roles.size(); j++) {
    		userDetailsService.ChangeUserRole(user.roles.get(j).c == 1?true:false, usr.getId(), user.roles.get(j).i);
    	}
		return "User information updated";
    }
	
	@RequestMapping(value="/saveCompany", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  ByCompany saveCompany(@RequestBody CompanyVM cmp) {
		
		if (subscriberDao.getCompanyByName(cmp.n) != null){
			return null;
		}
		Subscriber comp = new Subscriber();
		comp.setEmail(cmp.em);
		comp.setName(cmp.n);
		comp.setPhone(cmp.ph);
		comp.setContact_name(cmp.cp);
		comp.setNotes(cmp.nt);
		try {
	    	  Date dt = new SimpleDateFormat("MM/dd/yyyy").parse(cmp.cr);
	    	  comp.setCreateDate(dt);
	    	} catch(Exception e) {
	    		
	    	}
		subscriberDao.saveCompany(comp);
		Subscriber c = subscriberDao.getCompanyByName(cmp.n);
		ByCompany byCompany = getCompanyStruncture(c);
	    
		return byCompany;
		
	}
	public ByCompany getCompanyStruncture(Subscriber cmp){
		ByCompany byCompany = new ByCompany();
		byCompany.n = cmp.getName();	
		byCompany.cp = cmp.getContact_name();
		try{
			if(cmp.getCreateDate() != null){
	    	 String dt = new SimpleDateFormat("MM/dd/yyyy").format(cmp.getCreateDate());
	    	 if(dt != null){
	    		 byCompany.cr = dt;
	    	 }
			}
	    } catch(Exception e) {
	    	e.printStackTrace();	
	    }
		byCompany.ph = cmp.getPhone();
		byCompany.nt = cmp.getNotes();
		byCompany.em = cmp.getEmail();
		if(cmp.getDeleted() != null){
			byCompany.a = cmp.getDeleted().toString();
		}else{
			
		}
		byCompany.rs = new ArrayList<HasRole>();
		List<Role> roles = userDetailsService.getAllRole();
		for(Role _r : roles) {
			HasRole role = new HasRole();
			role.i = _r.id;
			role.c = 0;
			role.n = _r.name;
			byCompany.rs.add(role);
		}
		byCompany.i = subscriberDao.getCompanyByName(cmp.getName()).getId();
		return byCompany;
	}
	@RequestMapping(value="/saveUser", method=RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public  UserVM saveNewUser(@RequestBody UserVM user) {
		if(userDetailsService.getUserByUsername(user.un)!=null){
			return null;
		};
		
		User usr = new User();
		Subscriber cmp = subscriberDao.getCompanyByName(user.c);
		
    	usr.firstName = user.fn;
    	usr.lastName = user.ln;
    	usr.password = user.pw;
    	usr.username = user.un;
    	usr.email = user.e;
    	usr.company = cmp; 
    	try {
    	  Date dt = new SimpleDateFormat("MM/dd/yyyy").parse(user.sd);
    	  usr.setSubStartDate(dt);
    	} catch(Exception e) {
    		
    	}
    	usr.setNotes(user.nts);
    	usr.setNotes(user.ct);
    	userDetailsService.updateUser(usr);
    	for(int j = 0; j<user.roles.size(); j++) {
    		userDetailsService.ChangeUserRole(user.roles.get(j).c == 1? true:false, usr.getId(), user.roles.get(j).i);
    	}
    	UserVM vm = getUser(usr);
		return vm;
    }
	public UserVM getUser(User u){
		UserVM userVM = new UserVM();
		userVM.e = u.email;
		userVM.i = u.id;
		userVM.fn = (u.firstName == null || u.firstName.length() ==0) ? "" : u.firstName;
		userVM.ln = ((u.lastName == null || u.lastName.length() ==0) ? "" : u.lastName);
		userVM.n = userVM.fn + " " + userVM.ln; 
		userVM.un = u.username;
		userVM.pw = u.password;
		if(u.isDeleted() != null){
			userVM.a = u.isDeleted().toString();
		}
		try{
			if(u.getSubStartDate() != null){
	    	 String dt = new SimpleDateFormat("MM/dd/yyyy").format( u.getSubStartDate());
	    	 if(dt != null){
	    		 userVM.sd = dt;
	    	 }
			}
	    } catch(Exception e) {
	    	e.printStackTrace();	
	    }
		userVM.c = u.getCompany() == null? "" : u.getCompany().getName();
		return userVM;
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
	
	
	
	class ByCompany {
		public String a;
		public String em;
		public String nt;
		public String cp;
		public String cr;
		public String ph;
		public int i;
		public String n;
		public List<HasRole> rs; 
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
	
	static class HasRole {
		public int c;
		public int i;
		public String n;
	}
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	static class UserVM {
		public String ca;
		public String a;
		public String ct;
		public String nts;
		public UserVM() {}
		public int i;
		public String n;
		public String e;
		public String un;
		public String ln;
		public String fn;
		public String pw;
		public String c;
		public String sd;
		public List<HasRole> roles;
	}
	
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	static class CompanyVM {
		public String cn;
		public String n;
		public CompanyVM() {}
		public int ci;
		public String ph;
		public String em;
		public String cr;
		public String nt;
		public String cp;
		public String a;
	}

    @RequestMapping(value="public/isValidSession.m", method=RequestMethod.POST)
    public void isValidSession(@RequestParam String key, HttpSession session,HttpServletResponse response) {
    	try {

    		
			Map m = new HashMap();
    		Key dkey = generateKey();
    		
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, dkey);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(key);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
    		System.out.println(key+"hhhhhhhhhhhhh "+decryptedValue);
    		String[] st = decryptedValue.split("-");
    		String user = st[0];
    		String password = st[1];
    		long time = Long.valueOf(st[2]);
    		long interval = Long.valueOf(System.currentTimeMillis())-time; 
    		User _user = userDetailsService.getUserByUnamePassword(user, password);
    		if(_user != null){
    			response.getOutputStream().println("true");
    		}else{
    			response.getOutputStream().println("false");
    		}
				
    		
	    		
		}catch(Exception e) { 
			try {
				response.getOutputStream().println("false");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    	
    }
    @RequestMapping(value="/agoraE.m")
    @PreAuthorize("hasPermission(#user, 'agoraE.m')")
    public ModelAndView AgoraE(HttpSession session) {
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println(user.getUsername());
    	String cipherText =null;
    	String plainData=user.getUsername()+"-"+user.getPassword()+"-"+System.currentTimeMillis();
    	byte[] byteCipherText = null;
    	String encryptedValue = null;
    	try { 
    		
    		Key key = generateKey();
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(plainData.getBytes());
            encryptedValue = new BASE64Encoder().encode(encVal);
            
            
    		System.out.println("\n Plain Data : "+plainData+" \n Cipher Data : "+encryptedValue);
    		session.setAttribute("encryptedValue", encryptedValue);
    		
    	}
    	catch(Exception e) { }
    	Map m = new HashMap();
    	m.put("key", encryptedValue.replace("+", "%2b"));
	return new ModelAndView("agoraE", m);
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec("TheBestSecretkey".getBytes(), "AES");
        return key;
    }
}
