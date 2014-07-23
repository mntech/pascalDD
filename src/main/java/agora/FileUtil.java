package agora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    /**
     * Load a named SQL query file from the classpath. Spaces
     * are added at the end of each line.
     */
    public static String readClasspathSqlQuery(String fileName) {
	StringBuffer contents = new StringBuffer();

	try {
	    InputStreamReader isr = new InputStreamReader(FileUtil.class.getResourceAsStream(fileName));
	    BufferedReader rdr = new BufferedReader(isr);
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
}