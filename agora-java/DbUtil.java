import java.util.*;
import java.util.regex.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * Util to update db records with a given cursor.
 *
 * This file is completely self-contained. (Other than references to MySQL JDBC classes in `updateFlashSizes')
 *
 * javac -cp lib/mysql-connector-java-5.1.17-bin.jar DbUtil.java && java -cp .:lib/mysql-connector-java-5.1.17-bin.jar DbUtil
 */
public class DbUtil {

    /**
     * Load a file from the file system and return the contents as a string.
     * Lines are separated by spaces to prevent SQL syntax errors.
     */
    static String loadFile(String fileName) {
        StringBuffer contents = new StringBuffer();

        try {
            BufferedReader rdr = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = rdr.readLine()) != null)
                contents.append(line).append(" ");
            rdr.close();
        } catch(IOException ioe) {
            String message = "Failed to read '" + fileName +
                "' report: " + ioe.getMessage();
            throw new RuntimeException(message, ioe);
        }

        return contents.toString();
    }

    /**
     * Update the size of the Flash files based on the code
     * snipped of the placement.
     */
    static void updateFlashSizes(ResultSet rs) throws Exception {
	String id = rs.getString("id");
	String snippet = rs.getString("snippet");
	String size = rs.getString("size");
	if (snippet == null) { // no placements... :(
	    System.out.println("No snipp for imag file: " + id);
	    return;
	}

	/* hack for mysql (doesnt allow updating a resultset with a computed field in it) */
	Field f = com.mysql.jdbc.UpdatableResultSet.class.getDeclaredField("isUpdatable");
	f.setAccessible(true);
	f.setBoolean(rs, true);
	Field f_fields = com.mysql.jdbc.ResultSetImpl.class.getDeclaredField("fields");
	f_fields.setAccessible(true);
	com.mysql.jdbc.Field mysql_fields[] = (com.mysql.jdbc.Field[])f_fields.get(rs);

	// everything after here must hit the finally statement at the end
	f_fields.set(rs, Arrays.copyOfRange(mysql_fields, 0, 2));

	try {
	    Matcher m_h;
	    Matcher m_w;
	    if (snippet.contains("height=\"") && snippet.contains("width=\"")) {
		m_h = Pattern.compile(".*height=\"(\\d+)?(px)?\".*").matcher(snippet);
		m_w = Pattern.compile(".*width=\"(\\d+)?(px)?\".*").matcher(snippet);
	    } else if (snippet.contains("height:") && snippet.contains("width:")) {
		m_h = Pattern.compile("height:\\s*(\\d+)(px)?").matcher(snippet);
		m_w = Pattern.compile("width:\\s*(\\d+)(px)?").matcher(snippet);
		// weird snippet for imageFileId = 'b40442f7-327d-42cb-8476-f24a16c23d92'
	    } else if (snippet.matches(".*_biocomp_\\d+x\\d+_.*")) {
		m_h = Pattern.compile("_biocomp_\\d+x(\\d+)_").matcher(snippet);
		m_w = Pattern.compile("_biocomp_(\\d+)x\\d+_").matcher(snippet);
	    } else {
		return;
	    }
	    if (!(m_h.find() && m_w.find())) {
		System.err.println("Cannot match width/height for image file: " + id);
		return;
	    }
	    int h = Integer.parseInt(m_h.group(1));
	    int w = Integer.parseInt(m_w.group(1));
	    size = "" + w + "x" + h;
	    rs.updateString("size", "" + w + "x" + h);
	    rs.updateRow();
	} catch(Exception ex) {
	    throw ex;
	} finally {
	    Field s = com.mysql.jdbc.UpdatableResultSet.class.getDeclaredField("updateSQL");
	    s.setAccessible(true);
	    String sql = (String)s.get(rs);
	    System.out.println("sql: " + sql);

	    // flip it back so we can access the 3rd field next time
	    f_fields.set(rs, mysql_fields);
	}
	System.out.println("Updated " + id + " to " + size);
    }
    static String SQL_updateFlashSizes = "select id, size, (select snippet from image where imageFileId = imageFile.id limit 1) as snippet from imageFile where filename like '%.swf' and size = ''";

    /**
     * Fill in the missing sizes for non-flash files (older ones)
     */
    static void updateSizes(ResultSet rs) throws Exception {
	String filename = rs.getString("filename");
	Process p = Runtime.getRuntime().exec("identify -format %wx%h\\n " +
					      "/home/jbalint/agora/images/" + filename);
	p.waitFor();
	InputStreamReader r = new InputStreamReader(p.getInputStream());
	char buf[] = new char[15];
	r.read(buf);
	String size = new String(buf).replaceAll("\\n.*", "");
	rs.updateString("size", size);
	rs.updateRow();
	System.out.println("Updated " + filename + " to " + size);
    }
    static String SQL_updateSizes = "select * from imageFile where size is null and filename not like '%swf'";

    /**
     * Update the aspect ratio field based on the size.
     */
    static void updateAspectRatio(ResultSet rs) throws Exception {
	Integer width = Integer.parseInt(rs.getString("size").split("x")[0]);
	Integer height = Integer.parseInt(rs.getString("size").split("x")[1]);
	String asp;
	if (width > (height * 2))
	    asp = "banner";
	else if (height > (width * 2))
	    asp = "tower";
	else
	    asp = "rect";
	rs.updateString("aspect_ratio", asp);
	rs.updateRow();
	System.out.println("Updated: " + rs.getString("filename") + " to " + asp);
    }
    static String SQL_updateAspectRatio = "select * from imageFile where aspect_ratio is null and size <> ''";

    /**
     * Update the width/height of image files based on the size field.
     */
    static void updateWidthAndHeight(ResultSet rs) throws Exception {
	String size = rs.getString("size");
	String width = size.split("x")[0];
	String height = size.split("x")[1];
	String filename = rs.getString("filename");
	rs.updateString("width", width);
	rs.updateString("height", height);
	rs.updateRow();
	System.out.println("Updated: " + filename + " (" + size + ") to w=" + width + ", h=" + height);
    }
    static String SQL_updateWidthAndHeight = "select * from imageFile where size != '' and size is not null and width = 0";

    static int fileNum = 0;
    static void pre_assignImageFileNumbers(Connection c) throws Exception {
	ResultSet rs = c.createStatement().executeQuery("select max(image_num) from imageFile");
	rs.next();
	fileNum = rs.getInt(1);
	assert(fileNum != 0);
	rs.close();
    }
    static void assignImageFileNumbers(ResultSet rs) throws Exception {
	String id = rs.getString("id");
	rs.updateInt("image_num", ++fileNum);
	rs.updateRow();
	System.out.println("Assigned " + fileNum + " to " + id);
    }
    static String SQL_assignImageFileNumbers = "select id, image_num from imageFile";

    static void lookupAndRunProcedure(String procedureName, Connection conn) throws Exception {
	Field field = DbUtil.class.getDeclaredField("SQL_" + procedureName);
	field.setAccessible(true);
	Method method = DbUtil.class.getDeclaredMethod(procedureName, ResultSet.class);
	method.setAccessible(true);

	int rowsRetrieved = 0, rowsUpdated = 0;
	if (procedureName.equals("assignImageFileNumbers")) {
	    pre_assignImageFileNumbers(conn);
	}

	Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					      ResultSet.CONCUR_UPDATABLE);
	ResultSet rs = stmt.executeQuery((String)field.get(null));
	while(rs.next()) {
	    rowsRetrieved++;
	    method.invoke(null, rs);
	    try {
	    	if (rs.rowUpdated())
	    	    rowsUpdated++;
	    } catch(java.sql.SQLFeatureNotSupportedException ex) {
	    	continue;
	    }
	}
	rs.close();
	stmt.close();
	System.out.println("Rows retrieved: " + rowsRetrieved);
	System.out.println("Rows updated: " + rowsUpdated);
    }

    public static void main(String args[]) throws Exception {
	// defaults, valid on reporting server only
	String hostname = "localhost:3307";
	String username = "agora_scrape";
	String password = "6Ra2RuTr";
	if (args.length < 1) {
	    System.err.println("Usage: {0} [-h <host[:port]> -u <username> -p <password> <procedureName>");
	    System.exit(1);
	}
	Class.forName("com.mysql.jdbc.Driver").newInstance();

	List<String> restArgs = new ArrayList<String>();

	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equals("-h")) {
		hostname = args[++i];
	    } else if (args[i].equals("-u")) {
		username = args[++i];
	    } else if (args[i].equals("-p")) {
		password = args[++i];
	    } else {
		restArgs.add(args[i]);
	    }
	}

	String jdbcUrl = String.format("jdbc:mysql://%s/agora_scrape?allowMultiQueries=true&sessionVariables=group_concat_max_len=50000",
				       hostname);
	System.out.println("JDBC URL: " + jdbcUrl);
	Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

	args = restArgs.toArray(new String[0]);

	if (args[0].equals("file")) {
	    if (args.length < 2) {
		System.err.println("Filename must be given");
	    }
	    System.out.println("Executing file: " + args[1]);
	    String query = loadFile(args[1]);
	    Statement stmt = conn.createStatement();
	    boolean b = stmt.execute(query);
	    while (true) {
		if (stmt.getUpdateCount() == -1)
		    break;
		if (b) {
		    System.out.println("Result set");
		} else {
		    System.out.println("Updated " + stmt.getUpdateCount() + " rows");
		}
		b = stmt.getMoreResults();
	    }
	    stmt.close();
	} else {
	    lookupAndRunProcedure(args[0], conn);
	}
	conn.close();
    }
}
