package agora;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// http://static.springsource.org/spring/docs/2.0.x/api/org/springframework/jdbc/core/JdbcTemplate.html
@Repository("data")
@Transactional
public class Data {
    private static final Log log = LogFactory.getLog(Data.class);
    @Autowired
    private DataSource ds;
    @Autowired
    private JdbcTemplate jdbc;

    protected String getSql(String fileName) {
	return FileUtil.readClasspathSqlQuery("/agora/sql/" + fileName + ".sql");
    }

    /**
     * Bind a MySQL session variable.
     *
     * @param varname The session variable to bind to.
     * @param value The value to assign.
     */
    protected void setSessionVariable(String varname, Object value) {
	String sql = "set @" + varname + " = ?";
	jdbc.update(sql, new Object[] {value});
    }

    private RowMapper pullMapper = new RowMapper() {
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Pull p = new Pull();
		p.id = rs.getString(1);
		p.runId = rs.getInt(2);
		p.pulltime = rs.getTimestamp(3);
		p.path = rs.getString(4);
		p.hostId = rs.getString(5);
		p.groupId = rs.getString(6);
		p.ads = rs.getInt(7);
		return p;
	    }
	};
    private RowMapper imageMapper = new RowMapper() {
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		int colCnt = rs.getMetaData().getColumnCount();
		AdImage i = new AdImage();
		i.imageId = rs.getString(1);
		i.imageFileId = rs.getString(2);
		i.createDate = rs.getTimestamp(3);
		i.filename = rs.getString(4);
		i.dest = rs.getString(5);
		return i;
	    }
	};
    private RowMapper pathMapper = new RowMapper() {
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Path p = new Path();
		p.id = rs.getString(1);
		p.path = rs.getString(2);
		p.active = rs.getInt(3) == 1;
		p.title = rs.getString(4);
		p.comment = rs.getString(5);
		p.depth = rs.getInt(6);
		return p;
	    }
	};

    public List<Path> getPaths() {
	return jdbc.query("select id, path, active, title, comment, depth from hosts order by title", new Object[] {}, pathMapper);
    }

    public List<Path> getActivePaths() {
	return jdbc.query("select id, path, active, title, comment, depth from hosts where active = 1", new Object[] {}, pathMapper);
    }

    public List<Path> getPathsRemainingForRun(int runId) {
	String query = getSql("pathsRemaining");
	return jdbc.query(query, new Object[] {runId}, pathMapper);
    }

    public int getDataCount(String query) {
	return jdbc.queryForInt("select count(*) from " + query);
    }

    public List<AdImage> getPullImages(String pullId) {
	JdbcTemplate t = jdbc;
	List<AdImage> images = t.query("select i.id, i.imageFileId, f.createdate, f.filename, i.clickUrl " +
				       "from image i inner join imageFile f on i.imageFileId = f.id and pullId = ? " +
				       "order by f.createdate", new Object[] {pullId}, imageMapper);
	// for (AdImage i : images) {
	//     i.sites = (String)t.queryForObject("select group_concat(distinct h.path order by h.path separator \", \") " +
	// 				       "from image i inner join pulls p on i.pullId = p.id and i.imageFileId = ? " +
	// 				       "inner join hosts h on p.hostId = h.id",
	// 				       new Object[] {i.imageFileId}, String.class);
	// }
	return images;
    }

    public List<AdImage> getImagesInGroup(String groupId) {
	JdbcTemplate t = jdbc;
	List<AdImage> images = t.query("select i.id, i.imageFileId, f.createdate, f.filename, i.clickUrl " +
				       "from image i inner join imageFile f on i.imageFileId = f.id " +
				       "inner join pulls p on p.id = pullId and pullGroupId = ? " +
				       "order by f.createdate desc", new Object[] {groupId}, imageMapper);
	return images;
    }

    public Pull getPull(String id) {
	return (Pull)jdbc.queryForObject("select p.id, p.run_id, p.pulltime, p.path, p.hostId, p.pullGroupId, -1 from " +
					 "pulls p inner join hosts h on p.hostId = h.id and p.id = ?",
					 new Object[] {id}, pullMapper);
    }

    public List<Pull> getPullsInGroup(String groupId) {
	return jdbc.query("select p.id, p.run_id, p.pulltime, p.path, p.hostId, p.pullGroupId, -1 from pulls p where pullGroupId = ?",
			  new Object[] {groupId}, pullMapper);
    }

    public Path getPath(String pathId) {
	try {
	    return (Path)jdbc.queryForObject("select id, path, active, title, comment, depth from hosts where id = ?",
					     new Object[] {pathId}, pathMapper);
	} catch(IncorrectResultSizeDataAccessException ex) {
	    return null;
	}
    }

    public void save(Pull pull) {
	jdbc.update("insert into pulls (id, run_id, hostId, path, pullGroupId, depth, pulltime) values (?, ?, ?, ?, ?, ?, now())",
		    new Object[] {pull.id, pull.runId, pull.hostId, pull.path, pull.groupId, pull.depth});
    }

    /**
     * Save an AdImage. The following changes are made:
     * - imageFile record is added if one doesn't exist for this md5sum
     * - imageFileId is set on the input object
     * - image record is added (placement)
     */
    public void save(AdImage i) {
	JdbcTemplate t = jdbc;
	// check if we have a file for this md5 or not, create one if necessary
	try {
	    i.imageFileId = t.queryForObject("select id from imageFile where md5 = ?",
					     new Object[] {i.md5sum}, String.class);
	} catch(IncorrectResultSizeDataAccessException ex) {
	    i.imageFileId = java.util.UUID.randomUUID().toString();
	    i.newImage = true;
	    i.filename = i.imageFileId + "." + Util.getExtensionForMimeType(i.contentType);
	    t.update("insert into imageFile (id, md5, filename, size, createdbypullid, createdate) values (?, ?, ?, ?, ?, now())",
		     new Object[] {i.imageFileId, i.md5sum, i.filename, i.size, i.pullId});
	}

	// create the placement record
	t.update("insert into image (id, pullId, imageFileId, clickUrl, snippet, src, srcId) " +
		 "values (?, ?, ?, ?, ?, ?, ?)",
		 new Object[] {i.imageId, i.pullId, i.imageFileId, i.dest, i.html, i.src, i.srcId});
    }

    /**
     * Insert a new 'scraper run' record and return the id.
     */
    public int newRun() {
	return (Integer)jdbc.execute(new ConnectionCallback() {
		public Object doInConnection(Connection con) throws SQLException {
		    con.createStatement().executeUpdate("insert into scraper_run (run_date) values (CURRENT_DATE)");
		    ResultSet rs = con.createStatement().executeQuery("select @@last_insert_id");
		    rs.next();
		    Integer id = rs.getInt(1);
		    rs.close();
		    return id;
		}
	    });
    }

    public List<Pull> getLatestPulls() {
	return jdbc.query("select p.id, p.run_id, p.pulltime, p.path, p.hostId, p.pullGroupId, count(i.id) as placementCount " +
			  "from hosts h inner join pulls p on p.hostId = h.id " +
			  "left join image i on i.pullId = p.id " +
			  "group by 1, 2, 3 order by p.pulltime desc limit 10", pullMapper);
    }

    public List getLatestPullData() {
	/*
select h.id, h.path, max(p.pulltime) as pulltime, max(p.pullGroupId) as pullGroupId, count(distinct p.id) as urls, count(distinct i.id) as placements from pulls p
inner join hosts h on p.hostId = h.id
left join image i on i.pullId = p.id
inner join (
select max(pulltime) as maxPulltime, max(pullGroupId) as pullGroupId, p.hostId from pulls p inner join (select h.id as hostId,max(p.pulltime) as maxPulltime from hosts h inner join pulls p on h.id = p.hostId group by 1) as m on p.pulltime = m.maxPulltime group by 3
) as m on p.pullGroupId = m.pullGroupId
group by 1,2 order by 3 desc limit 20
	 */
	String query = "select h.id, h.path, max(p.pulltime) as pulltime, max(p.pullGroupId) as pullGroupId, count(distinct p.id) as urls, count(distinct i.id) as placements from pulls p inner join hosts h on p.hostId = h.id left join image i on i.pullId = p.id inner join ( select max(pulltime) as maxPulltime, max(pullGroupId) as pullGroupId, p.hostId from pulls p inner join (select h.id as hostId,max(p.pulltime) as maxPulltime from hosts h inner join pulls p on h.id = p.hostId group by 1) as m on p.pulltime = m.maxPulltime group by 3) as m on p.pullGroupId = m.pullGroupId group by 1,2 order by 3 desc";
	return jdbc.queryForList(query);
    }

    public List getPullReport1() {
	return null;
    }

    public List<AdImage> getLastImagesStartingFrom(Integer offset, Integer count) {
	return jdbc.query("select '', f.id, f.createdate, f.filename, '' from imageFile f order by createdate desc limit " + offset + "," + count, imageMapper);
    }

    public List getLatestDemoImages(String aspectRatio, Integer count) {
	return jdbc.queryForList("select filename, size from imageFile f inner join data_entry d on f.image_num = d.file_number where de_status = 1 and aspect_ratio = ? order by createdate desc limit " + count, new Object[] {aspectRatio});
    }

    public List getHostList() {
	return jdbc.queryForList("select title, id from hosts where active = 1 order by lower(title)");
    }

    public String getWelcomeText() {
	return jdbc.queryForObject("select welcome_text from welcome_text", String.class);
    }

    public void setWelcomeText(String welcomeText) {
	jdbc.update("update welcome_text set welcome_text = ?", new Object[] {welcomeText});
    }

    public void setImageFollowup(String id, String followupReason) {
	jdbc.update("update imageFile set needs_followup = 1, followup_reason = ? where id = ?", new Object[] {followupReason, id});
    }

    public void addHost(String path, String title, String comment) {
	String id = UUID.randomUUID().toString();
	jdbc.update("insert into hosts (id, path, title, comment) values (?, ?, ?, ?)", new Object[] {id, path, title, comment});
    }

    @Transactional
    public void updateAggregateData(int runId) {
	setSessionVariable("runId", runId);
	String updateQuery = getSql("fct_run_host_depth_ad_update");
	jdbc.update(updateQuery);
    }

    public void setScrapeTime(int runId, Path p, long scrapeSecs) {
	jdbc.update("replace into scraper_stats (run_id, host_id, depth, scrape_secs) values (?, ?, ?, ?)", new Object[] {runId, p.id, p.depth, scrapeSecs});
    }

    public void setProcessTime(int runId, Path p, long processSecs) {
	jdbc.update("update scraper_stats set process_secs = ? where run_id = ? and host_id = ?", new Object[] {processSecs, runId, p.id});
    }
}
