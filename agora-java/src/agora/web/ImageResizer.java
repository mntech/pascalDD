package agora.web;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import javax.imageio.IIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.HashSet;

/**
 * Servlet to serve up sizes of images.
 */
public class ImageResizer extends HttpServlet {
    private static final Log log = LogFactory.getLog(ImageResizer.class);

    private static String imagePath;
    private static String cachePath;

    public void init() throws ServletException {
	imagePath = getServletConfig().getInitParameter("imagePath");
	cachePath = getServletContext().getRealPath(getServletConfig().getInitParameter("cachePath"));
	log.info("Image resizer initialized to: imagePath=" + imagePath + ", cachePath=" + cachePath);
    }

    private static File getCacheFile(String filename, int maxWidth, int maxHeight) {
	return new File(cachePath + "/" + filename + "-" + maxWidth + "x" + maxHeight);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
	String filename = req.getParameter("filename");
	String maxW = req.getParameter("maxwidth");
	String maxH = req.getParameter("maxheight");
	String flip = req.getParameter("flip");
	if (flip == null)
	    flip = "y";
	Integer maxWidth = maxW != null ? Integer.parseInt(maxW) : 10000;
	Integer maxHeight = maxH != null ? Integer.parseInt(maxH) : 10000;
	if (maxWidth == 0) maxWidth = 10000;
	if (maxHeight == 0) maxHeight = 10000;
	String type = filename.replaceAll(".*\\.", "");
	// handle the possibility (althoguoh we prefer not to have them, might as well hendle them)
	// FILENAME.gif_ , etc
	if (type.endsWith("_"))
	    type = type.substring(0, type.length()-1);
	OutputStream out = res.getOutputStream();

	// check cache
	File cacheFile = getCacheFile(filename, maxWidth, maxHeight);
	if (cacheFile.exists()) {
	    //log.debug("Loading from cache: " + cacheFile);
	    FileInputStream fis = new FileInputStream(cacheFile);
	    res.setContentType("image/" + type);
	    try {
		int len;
		byte buf[] = new byte[2048];
		while ((len = fis.read(buf, 0, 2048)) > 0) {
		    out.write(buf, 0, len);
		}
	    } finally {
		fis.close();
	    }
	    return;
	}

	// load image
	BufferedImage origImage = null;
	try {
	    origImage = ImageIO.read(new File(imagePath + "/" + filename));
	} catch(Exception ex) {
	    Throwable cause = ex.getCause();
	    // if (FileNotFoundException.class.isAssignableFrom(cause.getClass())) { // do nothing
	    // 	log.debug("image 404: " + filename);
	    // } else {
		log.error("Image load failed: " + filename, ex);
	    // }
	    res.sendError(404);
	    return;
	}
	int height = origImage.getHeight();
	int width = origImage.getWidth();

	// rotate image if necessary
	if (!flip.equals("n") &&
	    (maxWidth > maxHeight) && // is longer width than height
	    (height > width) &&
	    ((((double)height) / ((double)width)) > 0.66)) {
	    log.debug("Rotating image of w=" + width + ",h=" + height + " requested at maxw=" + maxWidth + ",maxHeight=" + maxHeight);

	    AffineTransform transform = new AffineTransform();
	    transform.translate(0.5 * height, 0.5 * width);
	    transform.rotate(Math.toRadians(-90));
	    transform.translate(-0.5 * width, -0.5 * height);

	    BufferedImage rotatedBI = new BufferedImage(height, width /* <- switched */, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = rotatedBI.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			       RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.setTransform(transform);
	    g.drawImage(origImage, 0, 0, width, height, null);
	    g.dispose();
	    origImage = rotatedBI;
	    width = origImage.getWidth();
	    height = origImage.getHeight();
	}

	// perform scaling
	if (height > maxHeight) {
	    double ratio = ((double)maxHeight)/((double)height);
	    width = (int)(((double)width)*ratio);
	    height = maxHeight;
	}
	if (width > maxWidth) {
	    double ratio = ((double)maxWidth)/((double)width);
	    height = (int)(((double)height)*ratio);
	    width = maxWidth;
	}

        BufferedImage scaledBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.drawImage(origImage, 0, 0, width, height, null);
	g.dispose();

	// save resized image to cache
	if (cachePath != null) {
	    ImageIO.write(scaledBI, type,
			  new File(cachePath + "/" + filename + "-" + maxWidth + "x" + maxHeight));
	}

	// write back to client
	ImageIO.write(scaledBI, type, out);
    }
}
