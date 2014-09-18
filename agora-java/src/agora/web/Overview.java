package agora.web;

import agora.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.ModelMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class Overview {
    private static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private Data data;

    @RequestMapping(value="/login.m")
    public String login() {
	Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if (user instanceof User)
	    return "redirect:keywordSearch.m";
	else
	    return "login";
    }

    @RequestMapping(value="/tos.m")
    public String tos() {
	return "tos";
    }

    @RequestMapping(value="/welcome.m")
    @PreAuthorize("hasPermission(#user, 'welcome.m')")
    public ModelAndView welcome(HttpSession session) {
	Map m = new HashMap();
	m.put("welcomeText", data.getWelcomeText());
	session.removeAttribute("tab");
	return new ModelAndView("welcome", m);
    }

    @RequestMapping(value="/help.m")
    public String help(HttpSession session) {
	session.setAttribute("tab", "helpTab");
	return "help";
    }

    @RequestMapping(value="/pull.m")
    public ModelAndView pull(@RequestParam("id") String id) {
	Pull p = data.getPull(id);
	Map m = new HashMap();
	m.put("pull", p);
	m.put("images", data.getPullImages(id));
	return new ModelAndView("pull", m);
    }

    @RequestMapping(value="/pathlist.m")
    public ModelAndView pathlist() {
	List paths = data.getPaths();
	Map m = new HashMap();
	m.put("paths", paths);
	return new ModelAndView("pathlist", m);
    }

    @RequestMapping(value="/pullGroup.m")
    public ModelAndView pullGroup(@RequestParam("id") String id) {
	Map m = new HashMap();
	m.put("pulls", data.getPullsInGroup(id));
	m.put("placements", data.getImagesInGroup(id));
	return new ModelAndView("pullGroup", m);
    }

    @RequestMapping(value="/overallSiteReport1.m")
    public ModelAndView overallSiteReport1() {
	Map m = new HashMap();
	return new ModelAndView("overallSiteReport1", m);
    }

    @RequestMapping(value="/demoRecentImages.m")
    public ModelAndView demoRecentImages() {
	Map m = new HashMap();
	m.put("tower", data.getLatestDemoImages("tower", 14));
	for (Map<String, String> m1 : (List<Map<String, String>>)m.get("tower")) {
	    m1.put("size", Util.getSizeWithMax((String)m1.get("size"), 50, 200));
	}
	m.put("banner", data.getLatestDemoImages("banner", 12));
	for (Map<String, String> m1 : (List<Map<String, String>>)m.get("banner")) {
	    m1.put("size", Util.getSizeWithMax((String)m1.get("size"), 238, 58));
	}
	m.put("rect", data.getLatestDemoImages("rect", 12));
	for (Map<String, String> m1 : (List<Map<String, String>>)m.get("rect")) {
	    m1.put("size", Util.getSizeWithMax((String)m1.get("size"), 112, 112));
	}
	return new ModelAndView("demoRecentImages", m);
    }
}
