package agora.web;

import agora.*;
import agora.scraper.*;

import java.io.*;
import java.util.*;
import java.sql.*;

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
import org.springframework.ui.ModelMap;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@Secured ({"Admin"})
public class AdminReports {
    private static final Log log = LogFactory.getLog(AdminReports.class);

    @Autowired
    private JdbcTemplate jt;
    @Autowired
    private Data data;
    @Autowired
    private DataJobs dataJobs;

    @RequestMapping(value="/adminReportList.m")
    public ModelAndView adminReportList() {
	Map m = new HashMap();
	m.put("welcomeText", data.getWelcomeText());
	return new ModelAndView("adminReportList", m);
    }

    protected String getSql(String reportName) {
	return FileUtil.readClasspathSqlQuery("/agora/" + reportName + ".sql");
    }

    /**
     * Data Entry Status
     */
    @RequestMapping(value="/adminReportDeStatus.m")
    public ModelAndView adminReportDeStatus() {
	Map model = new HashMap();
	model.put("imageFileCount", jt.queryForInt("select count(*) from imageFile"));
	model.put("dataEntryCount", jt.queryForInt("select count(*) from data_entry"));
	model.put("dataEntryRemaining", jt.queryForInt("select count(f.ftp_image_id) from ftp_images f left join data_entry d on f.ftp_image_id = d.file_number where d.file_number is null"));
	model.put("deSubmitted", jt.queryForInt("select count(*) from data_entry where de_status = 1"));
	model.put("deHold", jt.queryForInt("select count(*) from data_entry where de_status = 2"));
	model.put("deNonAd", jt.queryForInt("select count(*) from data_entry where de_status = 3"));
	model.put("deVideo", jt.queryForInt("select count(*) from data_entry where de_status = 4"));

	model.put("recentDeOverview", jt.query("select date(client_created_at) as DE_Date, count(*) as DE_Count from data_entry where client_created_at > date_add(now(), interval -15 day) group by 1 order by 1 desc", rowsAsLists));
	model.put("recentDeStatus", jt.query("select date(client_created_at) as DE_Date, case de_status when 1 then '1-Ad' when 2 then '2-Hold' when 3 then '3-Non-ad' when 4 then '4-Video' end as DE_Status, count(*) as DE_Count from data_entry where client_created_at > date_add(now(), interval -15 day) group by 1, 2 order by 1 desc, 2", rowsAsLists));
	model.put("recentDeStaff", jt.query("select date(client_created_at) as DE_Date, username, count(*) as DE_Count from data_entry de inner join users u on de.de_user_id = u.user_id where client_created_at > date_add(now(), interval -15 day) group by 1, 2 order by 1 desc, 2", rowsAsLists));
	return new ModelAndView("adminReportDeStatus", model);
    }

    /**
     * Image List
     */
    @RequestMapping(value="/adminReportImageList.m")
    public ModelAndView adminReportImageList(@RequestParam("startImage") Integer startImage) {
	Map m = new HashMap();
	m.put("images", data.getLastImagesStartingFrom(startImage, 20));
	m.put("offset", startImage);
	return new ModelAndView("adminReportImageList", m);
    }

    /**
     * Misc stuff that isnt on its own page yet
     */
    @RequestMapping(value="/adminReportMisc.m")
    public ModelAndView adminReportMisc(@RequestParam(required=false) String report) {
	String queries[][] = {
	    {"deLock", "Data Entry Locking Status", "select locked_by_user_id, username, create_time, count(*) from ftp_images f left join users u on locked_by_user_id = u.user_id left join data_entry d on f.ftp_image_id = d.file_number where d.file_number is null group by 1,2,3"},
	    {"newImages", "New Raw Ads in last 2 weeks", "select date(createdate),count(*) from imageFile where createdate >= current_date - interval 2 week group by 1 order by 1 desc"},
	    {"placementsPerRun", "Placements per run in last 2 weeks", getSql("AdminReport_PlacementsPerRun")},
	    {"deStatusBreakdown", "DE Status Breakdown", "select username, de_status, count(*) from ftp_images f left join data_entry d on f.ftp_image_id = d.file_number left join users u on d.de_user_id = u.user_id group by 1, 2 order by 1,2"},
	    {"currentScraperStatus", "Current Scraper Status: " + jt.queryForObject("select max(run_date) from scraper_run", String.class), getSql("sql/AdminReport_CurrentScraperStatus")}
	};
	Map m = new HashMap();
	Map reports = new HashMap();
	m.put("reports", reports);
	for (String q[] : queries) {
	    String name = q[0];
	    String title = q[1];
	    String query = q[2];

	    if (report != null && !report.equals(name))
		continue;

	    reports.put(title, jt.query(query, rowsAsLists));
	}

	return new ModelAndView("adminReportMisc", m);
    }

    @RequestMapping(value="/adminScraperRunStatus.m")
    public ModelAndView scraperRunStatus() {
	Map model = new HashMap();
	// model.put("");
	return null;
    }

    @RequestMapping(value="/adminReportOverview.m")
    public ModelAndView overview() {
	Map m = new HashMap();
	m.put("hostCount", data.getDataCount("hosts"));
	// m.put("pullCount", data.getDataCount("pulls"));
	// m.put("pulls24h", data.getDataCount("pulls where pulltime > date_sub(now(), interval 24 hour)"));
	// m.put("placementCount", data.getDataCount("image"));
	// m.put("imageCount", data.getDataCount("imageFile"));
	// m.put("latestPulls", data.getLatestPulls());
	// m.put("latestPullData", data.getLatestPullData());
	return new ModelAndView("adminReportOverview", m);
    }

    private static final ResultSetExtractor rowsAsLists = new ResultSetExtractor() {
	    public Object extractData(ResultSet rs) throws SQLException {
		List names = new ArrayList();
		List rows = new ArrayList();
		int cols = rs.getMetaData().getColumnCount();
		rows.add(names);
		while (rs.next()) {
		    if (names.size() == 0) {
			for (int i = 0; i < cols; ++i) {
			    names.add(rs.getMetaData().getColumnLabel(1+i));
			}
		    }
		    List r = new ArrayList();
		    rows.add(r);
		    for (int i = 0; i < cols; ++i) {
			r.add(rs.getObject(1+i));
		    }
		}
		return rows;
	    }
	};

    @RequestMapping(value="/adminReportViewImage.m", method=RequestMethod.GET)
    public ModelAndView adminReportViewImage(@RequestParam("image_num") String imageNum) {
	Map m = new HashMap();
	String id = null;
	int num = -1;
	try {
	    num = Integer.parseInt(imageNum);
	} catch(NumberFormatException ex) {
	    id = imageNum;
	}
	String placementOverviewQ = getSql("AdminReport_ViewImagePlacementOverview");
	m.put("placementOverview", jt.query(placementOverviewQ, new Object[] {num, id}, rowsAsLists));
	String detailsQ = getSql("AdminReport_ViewImageDetails");
	m.put("details", jt.queryForMap(detailsQ, new Object[] {num, id}));
	return new ModelAndView("adminReportViewImage", m);
    }

    @RequestMapping(value="/adminReportViewImage.m", method=RequestMethod.POST)
    public String adminReportViewImage(@RequestParam("id") String id, @RequestParam("followup_reason") String followupReason) {
	data.setImageFollowup(id, followupReason);
	return "redirect:adminReportViewImage.m?image_num=" + id;
    }

    @RequestMapping(value="/adminReportUpdateWelcomeText.m")
    public String adminReportUpdateWelcomeText(@RequestParam("welcome_text") String welcomeText) {
	data.setWelcomeText(welcomeText);
	return "redirect:adminReportList.m";
    }

    @RequestMapping(value="/adminHosts.m")
    public ModelAndView adminReportHosts() {
	Map m = new HashMap();
	m.put("paths", data.getPaths());
	return new ModelAndView("adminHosts", m);
    }

    @RequestMapping(value="/adminHostAdd.m")
    public String adminReportHostAdd(@RequestParam String path, @RequestParam String title, @RequestParam String comment) {
	data.addHost(path, title, comment);
	return "redirect:adminHosts.m";
    }

    @RequestMapping(value="/adminScraperRuntimeStatus.m")
    public ModelAndView adminScraperRuntimeStatus() {
	Map m = new HashMap();
	List<ScraperServer> servers = dataJobs.getScraperServers();
	for (ScraperServer ss : servers) {
	    ss.scrapeJobs = dataJobs.getScrapeJobsForScraperServer(ss.id);
	}
	m.put("scraperServerList", servers);
	m.put("scrapeJobQueue", dataJobs.getScrapeJobQueue());
	return new ModelAndView("adminScraperRuntimeStatus", m);
    }
}