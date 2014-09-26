package agora.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import agora.dao.ManageSubscriptionDao;
import agora.dao.MediaInfoDao;
import agora.dao.OratesInfoDao;
import agora.model.MediaInfo;
import agora.model.OratesInfo;
import agora.model.Subscription;

@Controller
public class ManageSubscription {

	@Autowired
	private ManageSubscriptionDao subscriptionDao;

	@Autowired
	private MediaInfoDao mediaInfoDao;

	@Autowired
	private OratesInfoDao oratesInfoDao;
	
	@RequestMapping(value = "/manage-subscription", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@Transactional
	public String subscriptionHomePage(ModelMap map) {
		return "manage-subscription";
	}
	public static Integer subID;
	@RequestMapping(value = "/loadsubmedia/{id}/{title}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@Transactional
	@ResponseBody
	public MediaInfo loadsubmedia(@PathVariable int id, @PathVariable String title) {
		
		MediaInfo sb = mediaInfoDao.findMediaInfoDetailByIdaAndName(id, title);
		subID = sb.getId();
		System.out.println(subID+"---");
		return sb;
	}

	@RequestMapping(value = "/loadmedia", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@Transactional
	@ResponseBody
	public List<SubcriptionClass> loadmedia() {
		List<SubcriptionClass> list = new ArrayList<SubcriptionClass>();
		List<Subscription> subscription = subscriptionDao.findSubscriptionAllObject();
		int i = 1;
		for (Subscription s : subscription) {
			SubcriptionClass sub = new SubcriptionClass();
			// sub.label=s.title;
			sub.id = "role" + i;
			sub.subscriptionId = s.id;
			if (i == 1) {
				sub.collapsed = false;
			}
			List<Subscription> s1 = subscriptionDao
					.findSubscriptionDetailBySubcriptionId(s.id);
			int k = 1;
			for (Subscription s2 : s1) {

				SubcriptionClass sub1 = new SubcriptionClass();
				sub1.id = "role" + i + k;
				sub1.label = s2.title;
				sub1.children = null;
				sub1.parent_id = s2.parent_id;
				sub1.title = s2.title;
				sub1.subscriptionId = s2.id;
				k++;
				sub.children.add(sub1);
			}
			sub.count = sub.children.size();
			sub.label = s.title + "(" + sub.children.size() + ")";
			sub.title = s.title;
			sub.parent_id = s.parent_id;
			i++;
			list.add(sub);
		}
		return list;
	}

	public static class SubcriptionClass {
		public Integer parent_id;
		public String title;
		public String label;
		public String id;
		public Integer subscriptionId;
		public Boolean collapsed = true;
		public Integer count;
		public List<SubcriptionClass> children = new ArrayList<SubcriptionClass>();
	}

	@RequestMapping(value = "/updateSubscription", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public MediaInfo updateSubscription(@RequestBody MediaInfo sub) {
		MediaInfo subs = mediaInfoDao.getMediainfoById(sub.id);
		subs.setUrl(sub.getUrl());
		subs.setCategory(sub.getCategory());

		subs.setSubId(sub.getSubId());
		subs.setIncludes(sub.getIncludes());
		subs.setAbout(sub.getAbout());
		subs.setCompany(sub.getCompany());
		subs.setPrimary_language(sub.getPrimary_language());
		subs.setScheduled_NL_frequency(sub.getScheduled_NL_frequency());
		subs.setScheduled_SC_frequency(sub.getScheduled_SC_frequency());
		subs.setCurrency(sub.getCurrency());
		subs.setSc(sub.getSc());
		subs.setNewsletter(sub.getNewsletter());
		subs.setComments(sub.getComments());
		subs.setMedia_kit(sub.getMedia_kit());
		subs.setAccess(sub.getAccess());
		subs.setClientele(sub.getClientele());
		
		mediaInfoDao.updateMediaInfo(subs);
		return subs;

	}

	@RequestMapping(value = "/saveParentCompany", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public SubcriptionClass saveParentCompany(@RequestBody Subscription sub) {
		Subscription subs = new Subscription();
		subs.title = sub.title;
		subscriptionDao.updateSubscription(subs);
		subs.setParent_id(subs.id);
		subscriptionDao.updateSubscription(subs);
		
		MediaInfo m = new MediaInfo();
		m.setTitle(subs.title);
		m.setSubscription(subs);
		mediaInfoDao.saveMediaInfo(m);
		
		//Save into OratesInfo Table
		OratesInfo orates = new OratesInfo();
		List<String> yName = getYnameList();
		for(int i = 0; i<yName.size(); i++){
			orates.setY_names(yName.get(i));
			orates.setSubscription(subs);
			oratesInfoDao.saveOratesInfo(orates);
		}
		
		SubcriptionClass sb = new SubcriptionClass();
		sb.subscriptionId = subs.parent_id;
		sb.count = 0;
		sb.label = subs.title + "(0)";
		sb.title = subs.title;
		return sb;
	}

	
	@RequestMapping(value = "/deleteCompanyById/{subId}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public SubcriptionClass deleteCompany(@PathVariable Integer subId) {
		Subscription _sb = subscriptionDao.findSubscriptionDetailById(subId);
		List<Subscription> sub = subscriptionDao.findSubscriptionAllObjectById(subId);
		
		oratesInfoDao.deleteOratesDataById(subId);
		if(sub.size() != 0){
			for(Subscription subs : sub){
				oratesInfoDao.deleteOratesDataById(subs.id);
			}
		}
		subscriptionDao.deletesubscriptionById(subId);
		
		mediaInfoDao.deleteSubscriptionInfoById(subId);
		if(sub.size() != 0){
			for(Subscription subs : sub){
				mediaInfoDao.deleteSubscriptionInfoById(subs.id);
			}
			
			return null;
		}else{
			
			Subscription s = subscriptionDao.getsubscriptionById(_sb.parent_id);
			SubcriptionClass sb = new SubcriptionClass();
			sb.subscriptionId = s.parent_id;
			sb.collapsed = false;
			List<Subscription> s1 = subscriptionDao
					.findSubscriptionDetailBySubcriptionId(s.id);
			for (Subscription s2 : s1) {
				SubcriptionClass sub1 = new SubcriptionClass();
				sub1.label = s2.title;
				sub1.children = null;
				sub1.parent_id = s2.parent_id;
				sub1.title = s2.title;
				sub1.subscriptionId = s2.id;
				sb.children.add(sub1);
			}
			sb.count = sb.children.size();
			sb.label = s.title + " (" + sb.children.size() + ")";
			sb.title = s.title;
			return sb;
		}
	}
	
	
	@RequestMapping(value = "/saveCompany/{subId}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public SubcriptionClass saveCompany(@PathVariable Integer subId,
			@RequestBody Subscription sub) {
		
		Subscription s = subscriptionDao.getsubscriptionById(subId);
		if(s == null){
			return null;
		}else {
			Subscription subs = new Subscription();
			subs.parent_id = s.id;
			subs.title = sub.title;
			subscriptionDao.updateSubscription(subs);
			
			MediaInfo m = new MediaInfo();
			m.setTitle(subs.title);
			m.setSubscription(subs);
			mediaInfoDao.saveMediaInfo(m);
			
			//Save into OratesInfo Table
			OratesInfo orates = new OratesInfo();
			List<String> yName = getYnameList();
			for(int i = 0; i<yName.size(); i++){
				orates.setY_names(yName.get(i));
				orates.setSubscription(subs);
				oratesInfoDao.saveOratesInfo(orates);
			}
			
			SubcriptionClass sb = new SubcriptionClass();
			sb.subscriptionId = s.id;
			sb.collapsed = false;
			List<Subscription> s1 = subscriptionDao
					.findSubscriptionDetailBySubcriptionId(subs.parent_id);
			for (Subscription s2 : s1) {
				SubcriptionClass sub1 = new SubcriptionClass();
				sub1.label = s2.title;
				sub1.children = null;
				sub1.parent_id = s2.parent_id;
				sub1.title = s2.title;
				sub1.subscriptionId = s2.id;
				sb.children.add(sub1);
			}
			sb.count = sb.children.size();
			sb.label = s.title + " (" + sb.children.size() + ")";
			sb.title = s.title;
			return sb;
		}
	}
	
	@RequestMapping(value = "/uploadandsave", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public String uploadandsave(@RequestParam("file") MultipartFile file) {
		String fileExt = mediaInfoDao.saveUploadFile(file, subID);
		MediaInfo mI = mediaInfoDao.getMediainfoById(subID);
		mI.setImage_name(fileExt);
		mediaInfoDao.updateMediaInfo(mI);
		return "sucess";
	}
	
	
	@RequestMapping(value = "/getImagePath/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public FileSystemResource getImagePath(@PathVariable Integer id) {
		System.out.println(id);
		if(id != null){
			MediaInfo mI = mediaInfoDao.getMediainfoById(id);
			String filepath = mediaInfoDao.getImagePathByIdForThumbnail(id, mI.getImage_name());
			System.out.println(filepath);
			FileSystemResource f = new FileSystemResource(new File(filepath));
			return f;
		}else{
			return null;
		}
		
	}
	
	@RequestMapping(value = "/getOriginalImagePath/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public FileSystemResource getOriginalImagePath(@PathVariable Integer id) {	
		if(id != null){
			MediaInfo mI = mediaInfoDao.getMediainfoById(id);
			String originalpath = mediaInfoDao.getImagePathByIdForOriginal(id, mI.getImage_name());
			FileSystemResource f = new FileSystemResource(new File(originalpath));
			return f;
		}else{
			return null;
		}
	}
	
	//TODO Orates Work
	public List<String> getYnameList(){
		List<String> yName = new ArrayList();
		yName.add("Dimensions");
		yName.add("Newsletter");
		yName.add("ePt (4x monthly)");
		yName.add("sourcing");
		yName.add("Management");
		yName.add("(1x monthly)");
		yName.add("ePr(1x monthly)");
		yName.add("PTE e-alert");
		yName.add("(4 x monthly)");
		yName.add("Targeted");
		yName.add("Run of Site");
		yName.add("Featured Article");
		yName.add("White Paper");
		yName.add("Virtual Poster");

		yName.add("Placement");
		yName.add("Lead Generation");
		yName.add("16-page bound");
		yName.add("8-page tipped");
		yName.add("8-page bound");
		yName.add("4-page tipped");
		yName.add("4-page bound ");
		
		return yName;
		
	}
	@RequestMapping(value = "/getXnamelist", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public List<String> getXnamelist() {
	List<String> xName = new ArrayList();
	xName.add("LeaderBoard");
	xName.add("Banner");
	xName.add("Skyscraper");
	xName.add("Sponsored_Links_ROS");
	xName.add("Interstitial_Pop_Up");
	xName.add("Page_Peel");
	xName.add("Page_Push");
	xName.add("Video_Ad");
	xName.add("Logo");
	xName.add("Box_Ad");
	xName.add("Sponsor_Posts_per_post");
	xName.add("Small_Box");
	xName.add("Marketplace");

	xName.add("Custom");
	xName.add("Text_Ads");
	xName.add("Featured_Products");
	xName.add("Text_65_Words");
	xName.add("Button");
	xName.add("Box");
	xName.add("Rectangle");
	
	xName.add("e_solution_Broadcast");
	xName.add("Footer");
	xName.add("Top_position");
	xName.add("Button_footer");
	xName.add("Showcase");
	xName.add("Banner_Footer");
	xName.add("Featured_Profile_Pdt");
	
	xName.add("large_rectangle");
	xName.add("e_solution_Broadcast_Footer");
	xName.add("Hosting");
	xName.add("Exhibit_Hall");
	xName.add("Pdt_List");
	xName.add("Insert_Footer");
	return xName;	
	}
	
	
	
	@RequestMapping(value = "/getMatrixDataById/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public List<OratesInfo> getMatrixDataById(@PathVariable Integer id) {
		return oratesInfoDao.findOratesInfoAllObject(id);
		
		
	}
	
	@RequestMapping(value = "/saveMatrixData/{column_name}/{row_name}/{id}/{value}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Agora_Admin')")
	@ResponseBody
	public String saveMatrixData(@PathVariable String column_name, @PathVariable String row_name, @PathVariable Integer id, @PathVariable String value ) {
		oratesInfoDao.saveMatrixDataByRowColumn_Name(column_name, row_name,  id, value);
		return "sucess";
	}

}
