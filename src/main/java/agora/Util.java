package agora;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* Util v1
 */
public class Util {
    private static final Log log = LogFactory.getLog(Util.class);

    public static String read(InputStream is) {
	try {
	    InputStreamReader r = new InputStreamReader(is);
	    char buf[] = new char[1024];
	    StringBuilder out = new StringBuilder();
	    for (int i = r.read(buf); i > -1; i = r.read(buf))
		out.append(buf, 0, i);
	    return out.toString();
	} catch(IOException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public static String getResource(String rsrc) {
	return read(Util.class.getResourceAsStream(rsrc));
    }

    public static String getFileExtention(String filename) {
	return filename.replaceFirst("^.*\\.", ".").replaceAll("/.*$", "").replaceAll("\\?.*$", "");
    }

    public static String getExtensionForMimeType(String type) {
	if (type.startsWith("image/")) {
	    return type.split("/")[1].replaceAll("\\W", "_");
	} else {
	    return "swf";
	}
    }

    public static boolean isAdContentType(String type) {
	return type.startsWith("image/") ||
	    type.equals("application/x-shockwave-flash");
    }

    public static void runCommandBlindEnsureSuccess(String command) {
	StringBuilder out = new StringBuilder();
	StringBuilder err = new StringBuilder();
	int ret = runCommand(command, out, err);
	if (ret != 0) {
	    throw new RuntimeException("Command failed, exit value = " + ret + "\n\t" +
				       "command: " + command + "\n\t" +
				       "***********stdout: " + out + "\n\t" +
				       "***********stderr: " + err);
	}
	log.debug("***************stdout: " + out);
	log.debug("***************stderr: " + err);
    }

    public static int runCommand(String command, StringBuilder out, StringBuilder err) {
	log.debug("runCommand: " + command);
	Process p = null;
	int ret = 1;
	if (out == null)
	    out = new StringBuilder();
	if (err == null)
	    err = new StringBuilder();
	try {
	    p = Runtime.getRuntime().exec(command);
	    ret = p.waitFor();
	} catch(Exception ex) {
	    throw new RuntimeException(ex);
	}
	try {
	    char buf[] = new char[1024];
	    InputStreamReader r1 = new InputStreamReader(p.getInputStream());
	    InputStreamReader r2 = new InputStreamReader(p.getErrorStream());
	    for (int i = r1.read(buf); i > -1; i = r1.read(buf))
		out.append(buf, 0, i);
	    for (int i = r2.read(buf); i > -1; i = r2.read(buf))
		err.append(buf, 0, i);
	} catch(IOException ex) {
	    throw new RuntimeException(ex);
	}
	return ret;
    }

    public static String getSizeWithMax(String size, int maxWidth, int maxHeight) {
	int w = Integer.parseInt(size.split("x")[0]);
	int h = Integer.parseInt(size.split("x")[1]);
	if (maxHeight > 0 && h > maxHeight) {
	    double ratio = ((double)maxHeight) / h;
	    h = (int)(ratio * h);
	    w = (int)(ratio * w);
	    size = "" + w + "x" + h;
	}
	if (maxWidth > 0 && w > maxWidth) {
	    double ratio = ((double)maxWidth) / w;
	    h = (int)(ratio * h);
	    w = (int)(ratio * w);
	    size = "" + w + "x" + h;
	}
	return size;
    }
}