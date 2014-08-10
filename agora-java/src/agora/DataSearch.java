package agora;

import java.io.*;
import java.sql.*;
import javax.sql.DataSource;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

// http://static.springsource.org/spring/docs/2.0.x/api/org/springframework/jdbc/core/JdbcTemplate.html
@Repository("dataSearch")
public class DataSearch {
    private static final Log log = LogFactory.getLog(DataSearch.class);
    @Autowired
    private JdbcTemplate jdbc;

    protected String getSql(String reportName) {
	return FileUtil.readClasspathSqlQuery("/agora/" + reportName + ".sql");
    }

    /**
     * Bind a MySQL session variable.
     *
     * @param varname The session variable to bind to.
     * @param value The value to assign.
     */
    protected void setSessionVariable(String varname, Object value) {
	String sql = "set @" + varname + " = ?";
	if (value == null)
	    value = "";
	jdbc.update(sql, new Object[] {value});
    }

    @Transactional
    public List adSearch(String startMonth, Integer startYear, String endMonth, Integer endYear,
			 String keywords, String advertiser, String domain, String aspectRatio,
			 java.sql.Date createdSince,
			 final List<String> domains,
			 int count, int offset, Integer imageNum, boolean removeHouseAds) {
	/* we have to append this because MySQL doesn't allow them to be user variables, see bug #56811 */
	String sql = getSql("AdSearch");// + " limit " + count + " offset " + offset;

	// create a temp table with the search domains.....
	jdbc.execute(new ConnectionCallback() {
	    public Object doInConnection(Connection c) throws SQLException {
		Statement s = c.createStatement();
		s.execute("drop table if exists tmp_adsearch_domains");
		s.execute("create temporary table tmp_adsearch_domains (id char(36))");
		if (domains == null)
		    return null;
		PreparedStatement ps = c.prepareStatement("insert into tmp_adsearch_domains values (?)");
		for (String d : domains) {
		    ps.setString(1, d);
		    ps.addBatch();
		}
		ps.executeBatch();
		return null;
	    }
	});

	setSessionVariable("domainCount", domains == null ? 0 : domains.size());
	setSessionVariable("startMonth", startMonth);
	setSessionVariable("startYear", startYear);
	setSessionVariable("endMonth", endMonth);
	setSessionVariable("endYear", endYear);
	setSessionVariable("keywords", keywords);
	setSessionVariable("advertiser", advertiser);
	setSessionVariable("domain", domain);
	setSessionVariable("aspectRatio", aspectRatio);
	setSessionVariable("createdSince", createdSince);
	setSessionVariable("imageNum", imageNum);
	setSessionVariable("removeHouseAds", removeHouseAds ? 1 : 0);
	// these should be in the SQL file, but it returns multiple result sets, not compatible with jdbc.queryForList()
	jdbc.update("set @startDate = str_to_date(concat('1,',@startMonth,',',@startYear),'%d,%M,%Y')");
	jdbc.update("set @endDate = (str_to_date(concat('1,',@endMonth,',',@endYear),'%d,%M,%Y') + interval 1 month) - interval 1 day");
	jdbc.update("set @daysInSearchPeriod = datediff(@endDate, @startDate)");

	List res = jdbc.queryForList(sql);
	return res;
    }

    public List adDrilldown(int imageNum) {
	String sql = getSql("sql/AdDrilldown");
	return jdbc.queryForList(sql, new Object[] {imageNum});
    }

    public void logKeywordSearch(int userId,
				 String startMonth, Integer startYear,
				 String endMonth, Integer endYear,
				 String keywords, String advertiser,
				 String domain, String domains, String aspectRatio,
				 String createdSinceLabel,
				 int offset,
				 int resultCount, long duration) {
	jdbc.update("insert into keyword_search_log " +
		    "(user_id, start_month, start_year, end_month, end_year, " +
		      "keywords, advertiser, domain, domains, aspect_ratio, created_since, offset, result_count, duration) " +
		    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
		    new Object[] {userId, startMonth, startYear, endMonth, endYear,
				  keywords, advertiser, domain, domains, aspectRatio, createdSinceLabel,
				  offset, resultCount, duration});
    }

    @Transactional
    public List topAdvertisersSearch(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear,
				     String domain1id, String domain2id, String domain3id,
				     String sortColumn, String sortOrder,
				     List resultCount) {
	/*

SEVERE: Servlet.service() for servlet [web] in context with path [/A3lifesci] threw exception [Request processing failed; nested exception
is java.lang.NullPointerException] with root cause
java.lang.NullPointerException
        at agora.DataSearch.topAdvertisersSearch(DataSearch.java:117)

SEVERE: Servlet.service() for servlet [web] in context with path [/A3lifesci] threw exception [Request processing failed; nested exception is java.lang.NullPointerException] with root cause
java.lang.NullPointerException
        at agora.DataSearch.topAdvertisersSearch(DataSearch.java:123)

	*/
	sortColumn = sortColumn.replaceAll(";", ".").replaceAll("-", ".").replaceAll("'", ".");
	if (!(sortOrder.equals("asc") || sortOrder.equals("desc")))
	    throw new IllegalArgumentException("Invalid sortOrder");

	// TODO XXX SQL INJECTION POSSIBLE - ESCAPE THESE
	String sql = getSql("sql/TopAdvertisers") + " order by " + sortColumn + " " + sortOrder;

	setSessionVariable("startDate", String.format("%d-%02d-01", startYear, startMonth));
	setSessionVariable("endDate", String.format("%d-%02d-01", endYear, endMonth));
	setSessionVariable("domain1id", domain1id);
	setSessionVariable("domain2id", domain2id);
	setSessionVariable("domain3id", domain3id);

	List res = jdbc.queryForList(sql);
	if (resultCount != null)
	    resultCount.add(jdbc.queryForInt("select found_rows()"));
	return res;
    }
}
