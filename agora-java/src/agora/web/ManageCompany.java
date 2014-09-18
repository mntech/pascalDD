package agora.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agora.UserDetailsService;
import agora.dao.CompanyDao;
import agora.dao.SubscriberDao;
import agora.model.Company;

@Controller
public class ManageCompany {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private SubscriberDao subscriberDao;
	
	@Autowired
    private CompanyDao companyDao;
	
	
	@RequestMapping(value="/manage-company", method=RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@Transactional
	public  String getpermissions(ModelMap map) {
		
		return "manage-company";
    }
	
	//TODO TODAY For Company TAB
	public class Company_VM {
		public Integer id;
		public String title;
		public Boolean drag=true;
		public Integer childNumber;
		public Company_VM (Integer id2,String title) {
			this.title=title;
			this.id=id2;
		}
		public Company_VM (Integer id,String title,Integer childNumber) {
			this.title = title;
			this.id = id;
			this.childNumber  = childNumber;
		}
	}
	
	public class CompanyListVM {
		public Integer totalUnAssignedNumber;
		public Integer totalParentNumber;
		public Integer totalChildNumber;
		public List<Company_VM> unAssignedList;
		public List<Company_VM> assignedList;
		public List<Company_VM> assignedChildList;
	}
	
	@RequestMapping(value="/loadLists", method=RequestMethod.GET)
	@ResponseBody
	public CompanyListVM loadLists() {
		CompanyListVM result = new CompanyListVM();
		List<Company> unAssignedList = companyDao.findUnAssignedNames();
		List<Company> assignedList = companyDao.findAssignedNames();
		List<Company> assignedChildList = companyDao.findAssignedChildNames();
		result.totalUnAssignedNumber = unAssignedList.size();
		result.totalParentNumber = assignedList.size();
		result.totalChildNumber = assignedChildList.size();
		result.unAssignedList = new ArrayList<Company_VM>();
		for(Company ds : unAssignedList) {
			result.unAssignedList.add(new Company_VM(ds.id,ds.name));
		}
		result.assignedList = new ArrayList<Company_VM>();
		for(Company ds : assignedList) {
			result.assignedList.add(new Company_VM(ds.id,ds.name,companyDao.findAssignedChildNames(ds.id).size()));
		}
		result.assignedChildList = new ArrayList<Company_VM>();
		for(Company ds : assignedChildList) {
			result.assignedChildList.add(new Company_VM(ds.id,ds.name));
		}
		return result;
	}
	
	
	@RequestMapping(value="/removeparentsubscription/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void removeparentsubscription(@PathVariable Integer id) {
		List<Company> compList = companyDao.getCompanyByParentid(id);
		for(Company comp : compList) {
			companyDao.removeChildsParent(comp.id);
		}
		companyDao.removeParent(id);
	}
	
	@RequestMapping(value="/removechildsubscription/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void removechildsubscription(@PathVariable Integer id) {
		companyDao.removechildsubscription(id);
	}
	@RequestMapping(value="/addparentsubscription/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void addparentsubscription(@PathVariable Integer id) {
		 companyDao.addParentSubscription(id);
	}
	
	@RequestMapping(value="/addchildsubscription/{id}/{pid}", method=RequestMethod.GET)
	@ResponseBody
	public void addchildsubscription(@PathVariable Integer cid, @PathVariable Integer pid) {
		companyDao.addChildSubscription(cid, pid);
	}
	
	//TODO END Company Tab
	
	
}
