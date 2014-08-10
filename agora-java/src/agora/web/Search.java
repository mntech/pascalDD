package agora.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import agora.Data;
import agora.DataSearch;
import agora.User;
import agora.UserDetailsService;
import agora.Util;

@Controller
public class Search {
    private static final Log log = LogFactory.getLog(Search.class);

    @Autowired
    private Data data;

    @Autowired
    private DataSearch dataSearch;

    @Autowired
    private UserDetailsService userDetailsService;

    public static class SearchData {
	public String startMonth = "";
	public Integer startYear = 0;
	public String endMonth = "";
	public Integer endYear = 0;
	public String keywords = "";
	public String advertiser = "";
	public String domain = "";
	public Integer offset;
	public Integer pageSize;
	public String aspectRatio = "";
	public java.sql.Date createdSince;
	public String createdSinceLabel;
	public List<String> domains;
	public String useDomainSet;
	public boolean removeHouseAds = false;
	
	public String getStartMonth() { return startMonth; }
	
	public void setStartMonth(String startMonth) {
	    this.startMonth = startMonth;
	}
	
	public Integer getStartYear() { return startYear; }
	
	public void setStartYear(Integer startYear) {
	    this.startYear = startYear;
	}
	
	public String getEndMonth() { return endMonth; }
	
	public void setEndMonth(String endMonth) {
	    this.endMonth = endMonth;
	}
	
	public Integer getEndYear() { return endYear; }
	
	public void setEndYear(Integer endYear) {
	    this.endYear = endYear;
	}
	
	public String getKeywords() { return keywords; }
	
	public void setKeywords(String keywords) {
	    this.keywords = keywords;
	}
	
	public String getAdvertiser() { return advertiser; }
	
	public void setAdvertiser(String advertiser) {
	    this.advertiser = advertiser;
	}
	
	public String getDomain() { return domain; }
	
	public void setDomain(String domain) {
	    this.domain = domain;
	}
	
	public Integer getOffset() { return offset; }
	
	public void setOffset(Integer offset) {
	    this.offset = offset;
	}
	
	public Integer getPageSize() { return pageSize; }
	
	public void setPageSize(Integer pageSize) {
	    this.pageSize = pageSize;
	}
	
	public String getAspectRatio() { return aspectRatio; }
	
	public void setAspectRatio(String aspectRatio) {
	    this.aspectRatio = aspectRatio;
	}

	public java.sql.Date getCreatedSince() { return createdSince; }

	public void setCreatedSince(java.sql.Date createdSince) {
	    this.createdSince = createdSince;
	}

	public void setCreatedSince(long l) {
	    createdSince = new java.sql.Date(l);
	}

	public String getCreatedSinceLabel() { return createdSinceLabel; }
	
	public void setCreatedSinceLabel(String createdSinceLabel) {
	    this.createdSinceLabel = createdSinceLabel;
	}

	public List<String> getDomains() { return domains; }
	
	public void setDomains(List<String> domains) {
	    this.domains = domains;
	}
	
	public String getUseDomainSet() { return useDomainSet; }
	
	public void setUseDomainSet(String useDomainSet) {
	    this.useDomainSet = useDomainSet;
	}

	public boolean isRemoveHouseAds() { return removeHouseAds; }

	public void setRemoveHouseAds(boolean removeHouseAds) {
	    this.removeHouseAds = removeHouseAds;
	}
    }

    /********************************************************************/
    /*  Search Types                                                    */
    /* these just setup what the search page will do when it's accessed */
    /********************************************************************/

    @RequestMapping(value="/keywordSearch.m")
    @PreAuthorize("hasPermission(#user, 'keywordSearch.m')")
    public ModelAndView keywordSearch(HttpSession session) {
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	session.setAttribute("tab", "keywordExplorerTab");
	session.setAttribute("searchResultPage", "searchResults");
	return new ModelAndView("search", m);
    }

    @RequestMapping(value="/tileAndCompareSearch.m")
    @PreAuthorize("hasPermission(#user, 'tileAndCompareSearch.m')")
    public ModelAndView tileAndCompareSearch(HttpSession session) {
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	session.setAttribute("tab", "tileAndCompareTab");
	session.setAttribute("searchResultPage", "tileAndCompareResults");
	return new ModelAndView("search", m);
    }

    @RequestMapping(value="/placementDetailAndMetricSearch.m")
    @PreAuthorize("hasPermission(#user, 'placementDetailAndMetricSearch.m')")
    public ModelAndView placementDetailAndMetricSearch(HttpSession session) {
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	session.setAttribute("tab", "placementDetailAndMetricTab");
	session.setAttribute("searchResultPage", "searchResults");
	return new ModelAndView("search", m);
    }

    @RequestMapping(value="/timelineSearch.m")
    @PreAuthorize("hasPermission(#user, 'timelineSearch.m')")
    public ModelAndView timelineSearch(HttpSession session) {
	Map m = new HashMap();
	m.put("domainList", data.getHostList());
	session.setAttribute("tab", "campaignTimelineTab");
	session.setAttribute("searchResultPage", "timelineResults");
	return new ModelAndView("search", m);
    }

    @RequestMapping(value="/adDrilldown.m")
    @PreAuthorize("hasPermission(#user, 'adDrilldown.m')")
    public ModelAndView adDrilldown(@RequestParam Integer imageNum,
				    HttpSession session) {
	Map m = new HashMap();
	SearchData d = (SearchData)session.getAttribute("searchData");

	List<Map> res = (List<Map>)dataSearch.adSearch(d.startMonth, d.startYear, d.endMonth, d.endYear,
						       d.keywords, d.advertiser, d.domain, d.aspectRatio,
						       d.createdSince,
						       d.domains, 0/*not used anyway*/, d.offset, imageNum,
						       /*removeHouseAds=*/false);
	Map ad = res.get(0);
	m.put("ad", ad);
	m.put("results", dataSearch.adDrilldown(imageNum));

	return new ModelAndView("adDrilldown", m);
    }

    private static String quoteForCsv(Object s) {
	return "\"" + ((String)s).replaceAll("\"", "\"\"").replaceAll("\r\n", "\n").replaceAll("\n", "  ") + "\"";
    }

    @RequestMapping(value="/searchCsv.m")
    public void searchCsv(HttpServletResponse res, HttpSession session) throws IOException {
	SearchData d = (SearchData)session.getAttribute("searchData");
	res.setHeader("Content-Type", "text/csv");
	res.setHeader("Content-Disposition", "attachment; filename=\"" + "Agora-Keyword-Search.csv" + "\"");
	BufferedWriter writer = new BufferedWriter(res.getWriter());
	writer.append("Image Number,");
	writer.append("Company,");
	writer.append("Product,");
	writer.append("Image Dim.,");
	writer.append("Image Type,");
	writer.append("First Date,");
	writer.append("Last Date,");
	writer.append("Share,");
	writer.append("Occurences,");
	writer.append("Ad Text,");
	writer.append("Domains \n");

	List<Integer> placementCount = new ArrayList<Integer>();
	List<Map> results = doSearch(d, placementCount);
	updateListWithRelativeAds(results, placementCount.get(0));
	for (Map m : results) {
	    writer.append("#" + m.get("image_num"));
	    writer.append(",");
	    writer.append(quoteForCsv(m.get("company_name")));
	    writer.append(",");
	    writer.append(quoteForCsv(m.get("product")));
	    writer.append(",");
	    writer.append((String)m.get("size"));
	    writer.append(",");
	    writer.append(((String)m.get("filename")).replaceAll(".*\\.", ""));
	    writer.append(",");
	    writer.append(quoteForCsv(""+m.get("createdate"))); // TODO format
	    writer.append(",");
	    writer.append(quoteForCsv(""+m.get("lastDate"))); // TODO format
	    writer.append(",");
	    writer.append((String)m.get("percentAdCount"));
	    writer.append(",");
	    writer.append(""+m.get("adCount")); // TODO format with ,
	    writer.append(",");
	    writer.append(quoteForCsv(m.get("description")));
	    writer.append(",");
	    writer.append((String)m.get("site_list"));
	    writer.append("\n");
	}

	writer.close();
    }

    @RequestMapping(value="/search.m")
    public ModelAndView search(@RequestParam String startMonth,
			       @RequestParam Integer startYear,
			       @RequestParam String endMonth,
			       @RequestParam Integer endYear,
			       @RequestParam String keywords,
			       @RequestParam String advertiser,
			       @RequestParam String domain,
			       @RequestParam(required=false) String useDomainSet,
			       @RequestParam(required=false) Integer offset,
			       @RequestParam(required=false) Integer pageSize,
			       @RequestParam String aspectRatio,
			       @RequestParam Boolean removeHouseAds,
			       @RequestParam(value="domains[]", required=false) ArrayList<String> domainsInput,
			       HttpSession session) {
	List<String> domains = domainsInput;
	SearchData d = new SearchData();
	d.startMonth = startMonth;
	d.startYear = startYear;
	d.endMonth = endMonth;
	d.endYear = endYear;
	d.keywords = keywords;
	d.advertiser = advertiser;
	d.domain = domain;
	d.offset = offset;
	d.pageSize = pageSize;
	d.aspectRatio = aspectRatio;
	d.domains = domains;
	d.useDomainSet = useDomainSet;
	d.removeHouseAds = removeHouseAds;
	session.setAttribute("searchData", d);
	return doSearchView(d, session);
    }

    @RequestMapping(value="/backToSearch.m")
    public ModelAndView backToSearch(HttpSession session) {
	SearchData d = (SearchData)session.getAttribute("searchData");
	return doSearchView(d, session);
    }

    /**
     * Perform the search query and return:
     * - list of matching ad info
     * - number of placements across all rows (placementCount)
     */
    private List<Map> doSearch(SearchData d, List<Integer> placementCount) {
	if (placementCount == null)
	    placementCount = new ArrayList<Integer>();

	List<Map> allResults = (List<Map>)dataSearch.adSearch(d.startMonth, d.startYear, d.endMonth, d.endYear,
							      d.keywords, d.advertiser, d.domain, d.aspectRatio,
							      d.createdSince,
							      d.domains, d.pageSize, d.offset, null,
							      d.removeHouseAds);

	/* filter out rows not matching domain criteria */
	if ((d.domain != null && d.domain.length() > 0) || d.domains != null) {
	    for (Iterator<Map> i = allResults.iterator(); i.hasNext(); ) {
		Map m = i.next();
		if (d.domain.length() > 0) {
		    if (!((String)m.get("site_list")).toLowerCase().contains(d.domain.toLowerCase())) {
			i.remove();
			continue;
		    }
		}
		if (d.domains != null) {
		    boolean contains = false;
		    for (String dom : d.domains) {
			if (((String)m.get("host_list")).contains(dom)) {
			    contains = true;
			    break;
			}
		    }
		    if (!contains)
			i.remove();
		}
	    }
	}

	/* get total num of placements in search result */
	int pCounter = 0;
	for (int i = 0; i < allResults.size(); ++i) {
	    Map m = allResults.get(i);
	    int placements = ((BigInteger)m.get("ad_count")).intValue();
	    pCounter += placements;
	}
	placementCount.add(pCounter);

	return allResults;
    }

    private void updateListWithRelativeAds(List<Map> results, int totalAdCount) {
	for (Map m : results) {
	    /* calculate relative ads */
	    int ads = ((BigInteger)m.get("ad_count")).intValue();
	    m.put("percentAdCount", String.format("%03.1f%%", 100.0*((double)ads)/totalAdCount));
	    m.put("adCount", ads);
	}
    }

    private void updateListWithMiniSizes(List<Map> results) {
	for (Map m : results) {
	    /* calculate adjusted size */
	    m.put("smallSize", Util.getSizeWithMax((String)m.get("size"), 200, 60));
	}
    }

    private ModelAndView doSearchView(SearchData d, HttpSession session) {
	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	long beginTs = System.currentTimeMillis();
	final int DEFAULT_PAGE_SIZE = 20;
	Map model = new HashMap();
	if (d.offset == null)
	    d.offset = 0;
	if (d.pageSize == null) {
	    d.pageSize = DEFAULT_PAGE_SIZE;
	    if ("tower".equals(d.aspectRatio)) {
		d.pageSize = 36;
	    } else if ("rect".equals(d.aspectRatio)) {
		d.pageSize = 30;
	    } else if ("banner".equals(d.aspectRatio)) {
		d.pageSize = 27;
	    }
	}
	session.setAttribute("aspectRatio", d.aspectRatio);
	session.setAttribute("util", new Util());

	model.put("domainList", data.getHostList());
	model.put("searchData", d);

	List<Integer> placementCount = new ArrayList<Integer>();

	if ("on".equals(d.useDomainSet)) {
	    d.domains = userDetailsService.getDomainSet(user);
	}

	List<Map> allResults = doSearch(d, placementCount);
	List<Map> results = allResults.subList(d.offset, (allResults.size() < (d.offset + d.pageSize)) ? allResults.size() : (d.offset + d.pageSize));

	session.setAttribute("totalAdCount", placementCount.get(0));

	model.put("totalAdCount", placementCount.get(0));
	model.put("results", results);
	model.put("resultCount", allResults.size());
	model.put("pageSize", d.pageSize);

	updateListWithRelativeAds(results, placementCount.get(0));
	updateListWithMiniSizes(results);

	/* log the search */
	try {
	    long duration = System.currentTimeMillis() - beginTs;
	    dataSearch.logKeywordSearch(user.id,
					d.startMonth, d.startYear, d.endMonth, d.endYear,
					d.keywords, d.advertiser, d.domain, d.domains == null ? "" : d.domains.toString(),
					d.aspectRatio, d.createdSinceLabel,
					d.offset,
					allResults.size(), duration);
	} catch(Exception ex) {
	    log.error("error logging search", ex);
	}
	return new ModelAndView((String)session.getAttribute("searchResultPage"), model);
    }

    private List<Map> doTopAdvertisers(Integer startMonth,
				       Integer startYear,
				       Integer endMonth,
				       Integer endYear,
				       String domain1,
				       String domain2,
				       String domain3,
				       String sortColumn,
				       String sortOrder) {
	List<Map> allResults = (List<Map>)dataSearch.topAdvertisersSearch(startMonth, startYear, endMonth, endYear,
									  domain1, domain2, domain3, sortColumn, sortOrder, null);

	/* (same as above) */
	int totalAdCount = 0;
	for (int i = 0; i < allResults.size(); ++i) {
	    Map m = allResults.get(i);
	    // int ads = ((BigInteger)m.get("ad_count")).intValue();
	    int ads = ((BigDecimal)m.get("ad_count")).intValue();
	    totalAdCount += ads;
	}

	for (Map m : allResults) {
	    /* calculate relative ads */
	    // int ads = ((BigInteger)m.get("ad_count")).intValue();
	    int ads = ((BigDecimal)m.get("ad_count")).intValue();
	    m.put("percentAdCount", String.format("%03.1f%%", 100.0*((double)ads)/totalAdCount));
	    m.put("adCount", ads);
	}

	return allResults;
    }

    @RequestMapping(value="/topAdvertisersCsv.m")
    public void topAdvertisersCsv(@RequestParam Integer startMonth,
				  @RequestParam Integer startYear,
				  @RequestParam Integer endMonth,
				  @RequestParam Integer endYear,
				  @RequestParam String domain1,
				  @RequestParam String domain2,
				  @RequestParam String domain3,
				  @RequestParam String sortColumn,
				  @RequestParam String sortOrder,
				  HttpServletResponse res) throws IOException {
	List<Map> allResults = doTopAdvertisers(startMonth, startYear, endMonth, endYear, domain1, domain2, domain3, sortColumn, sortOrder);
	res.setHeader("Content-Type", "text/csv");
	res.setHeader("Content-Disposition", "attachment; filename=\"" + "Agora-Top-Advertisers.csv" + "\"");
	BufferedWriter writer = new BufferedWriter(res.getWriter());
	writer.append("Advertiser,");
	writer.append("Chan,");
	writer.append("P+Chan,");
	writer.append("S,");
	writer.append("P,");
	writer.append("L,");
	writer.append("A,");
	writer.append("S,");
	writer.append("H,");
	writer.append("SPLASH Score,");
	writer.append("D1 (" + data.getPath(domain1).getTitle() + "),");
	writer.append("D2 (" + data.getPath(domain2).getTitle() + "),");
	writer.append("D3 (" + data.getPath(domain3).getTitle() + "),");
	writer.append("Domains Seen On\n");

	for (Map m : allResults) {
	    writer.append(quoteForCsv(m.get("company_name")));
	    writer.append(",");
	    writer.append(""+m.get("channels")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+(((Long)m.get("programs")) + ((Long)m.get("channels")))); // TODO format with ,
	    writer.append(",");
	    writer.append(""+m.get("sites")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+m.get("programs")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+m.get("adCount")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+(((BigDecimal)m.get("ad_area")).longValue()/1000000)); // TODO format with ,
	    writer.append(",");
	    writer.append((String)m.get("percentAdCount"));
	    writer.append(",");
	    //  H
	    writer.append(",");
	    //  S M A S H
	    writer.append(",");
	    writer.append(""+m.get("domain1s1")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+m.get("domain2s1")); // TODO format with ,
	    writer.append(",");
	    writer.append(""+m.get("domain3s1")); // TODO format with ,
	    writer.append(",");
	    writer.append((String)m.get("domains_seen_on"));
	    writer.append("\n");
	}

	writer.close();
    }

    @RequestMapping(value="/topAdvertisers.m")
    @PreAuthorize("hasPermission(#user, 'topAdvertisers.m')")
    public ModelAndView topAdvertisers(@RequestParam(required=false) Integer startMonth,
				       @RequestParam(required=false) Integer startYear,
				       @RequestParam(required=false) Integer endMonth,
				       @RequestParam(required=false) Integer endYear,
				       @RequestParam(required=false) String includePublishers,
				       @RequestParam(required=false) String domain1,
				       @RequestParam(required=false) String domain2,
				       @RequestParam(required=false) String domain3,
				       @RequestParam(required=false) String sortColumn,
				       @RequestParam(required=false) String sortOrder,
				       @RequestParam(required=false) Integer offset,
				       @RequestParam(required=false) Integer pageSize,
				       HttpSession session) {
	final int DEFAULT_PAGE_SIZE = 40;

	session.setAttribute("tab", "topAdvertisersTab");

	// defaults for landing page (helps if DB caches this query)
	if (startMonth == null) {
	    // default period to three months, if its less than the 10th of the month, start earlier
	    Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    if (day < 10)
		cal.add(Calendar.MONTH, -1);
	    cal.add(Calendar.MONTH, -2);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    startMonth = 1 + cal.get(Calendar.MONTH);
	    startYear = cal.get(Calendar.YEAR);
	    cal.add(Calendar.MONTH, 2);
	    if (day < 10)
		cal.add(Calendar.MONTH, 1);
	    endMonth = 1 + cal.get(Calendar.MONTH);
	    endYear = cal.get(Calendar.YEAR);
	    includePublishers = "true";
	    sortColumn = "programs";
	    sortOrder = "desc";
	}

	if (includePublishers == null)
	    includePublishers = "false";
	if ("".equals(domain1))
	    domain1 = null;
	if ("".equals(domain2))
	    domain2 = null;
	if ("".equals(domain3))
	    domain3 = null;

	if (offset == null)
	    offset = 0;
	if (pageSize == null)
	    pageSize = DEFAULT_PAGE_SIZE;

	Map model = new HashMap();
	model.put("domainList", data.getHostList());

	List<Map> allResults = doTopAdvertisers(startMonth, startYear, endMonth, endYear, domain1, domain2, domain3, sortColumn, sortOrder);

	List<Map> results = allResults.subList(offset, (allResults.size() < (offset + pageSize)) ? allResults.size() : (offset + pageSize));

	model.put("results", results);
	model.put("resultCount", allResults.size());
	model.put("pageSize", pageSize);
	model.put("offset", offset);

	model.put("startMonth", startMonth);
	model.put("startYear", startYear);
	model.put("endMonth", endMonth);
	model.put("endYear", endYear);
	model.put("includePublishers", includePublishers);
	model.put("domain1", domain1);
	model.put("domain2", domain2);
	model.put("domain3", domain3);
	if (domain1 != null)
	    model.put("domain1name", data.getPath(domain1).getTitle());
	if (domain2 != null)
	    model.put("domain2name", data.getPath(domain2).getTitle());
	if (domain3 != null)
	    model.put("domain3name", data.getPath(domain3).getTitle());
	model.put("sortColumn", sortColumn);
	model.put("sortOrder", sortOrder);

	return new ModelAndView("topAdvertisers", model);
    }

    @RequestMapping(value="/missingAdvertisersInput.m")
    @PreAuthorize("hasPermission(#user, 'missingAdvertisersInput.m')")
    public ModelAndView missingAdvertisersInput(HttpSession session) {
	session.setAttribute("tab", "missingAdvertisersTab");

	Map model = new HashMap();
	model.put("domainList", data.getHostList());
	return new ModelAndView("missingAdvertisers", model);
    }

    @RequestMapping(value="/newCreative.m")
    @PreAuthorize("hasPermission(#user, 'newCreative.m')")
    public ModelAndView newCreative(@RequestParam(required=false, defaultValue="1month") String period,
				    @RequestParam(required=false, defaultValue="0") Integer offset,
				    @RequestParam(required=false, defaultValue="false") Boolean removeHouseAds,
				    HttpSession session) {
	session.setAttribute("tab", "newCreativeTab");
	session.setAttribute("searchResultPage", "newCreative");

	Calendar cal = Calendar.getInstance();
	if (period.equals("1week")) {
	    cal.add(Calendar.DAY_OF_MONTH, -7);
	} else if (period.equals("2week")) {
	    cal.add(Calendar.DAY_OF_MONTH, -7*2);
	} else if (period.equals("3week")) {
	    cal.add(Calendar.DAY_OF_MONTH, -7*3);
	} else if (period.equals("1month")) {
	    cal.add(Calendar.MONTH, -1);
	} else if (period.equals("3month")) {
	    cal.add(Calendar.MONTH, -3);
	} else if (period.equals("6month")) {
	    cal.add(Calendar.MONTH, -6);
	}

	SearchData d = new SearchData();
	d.setCreatedSince(cal.getTimeInMillis());
	d.setCreatedSinceLabel(period);
	d.setOffset(offset);
	d.setRemoveHouseAds(removeHouseAds);
	session.setAttribute("searchData", d);
	return doSearchView(d, session);
    }
}
