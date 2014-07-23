package agora.web;

import agora.*;
import java.io.*;
import java.util.*;
import java.math.*;
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
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.*;
import org.springframework.ui.ModelMap;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class UserController {
    private static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private Data data;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value="/domainSet.m", method=RequestMethod.GET)
    public ModelAndView domainSetGet() {
	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	m.put("domainSet", userDetailsService.getDomainSet(user));
	m.put("tab", "domainSetsTab");
	return new ModelAndView("domainSet", m);
    }

    @RequestMapping(value="/domainSet.m", method=RequestMethod.POST)
    public ModelAndView domainSetSave(
			       @RequestParam(value="domains[]") ArrayList<String> domainSet
) {
	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	userDetailsService.saveDomainSet(user, domainSet);
	m.put("domainSet", userDetailsService.getDomainSet(user));
	m.put("saved", 1);
	m.put("tab", "domainSetsTab");
	return new ModelAndView("domainSet", m);
    }
}