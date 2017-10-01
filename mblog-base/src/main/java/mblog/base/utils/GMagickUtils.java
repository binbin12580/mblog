//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mblog.base.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

public class GMagickUtils {
	private static Logger log = Logger.getLogger(GMagickUtils.class);
	public static String GMAGICK_HOME = "gmagick.home";
	public static String gmHome;

	public GMagickUtils() {
	}

	public static void validate(File ori, String dest) throws IOException {
		File destFile = new File(dest);
		if(ori == null) {
			throw new NullPointerException("Source must not be null");
		} else if(dest == null) {
			throw new NullPointerException("Destination must not be null");
		} else if(!ori.exists()) {
			throw new FileNotFoundException("Source \'" + ori + "\' does not exist");
		} else if(ori.isDirectory()) {
			throw new IOException("Source \'" + ori + "\' exists but is a directory");
		} else if(ori.getCanonicalPath().equals(destFile.getCanonicalPath())) {
			throw new IOException("Source \'" + ori + "\' and destination \'" + dest + "\' are the same");
		} else if(destFile.getParentFile() != null && !destFile.getParentFile().exists() && !destFile.getParentFile().mkdirs()) {
			throw new IOException("Destination \'" + dest + "\' directory cannot be created");
		} else if(destFile.exists() && !destFile.canWrite()) {
			throw new IOException("Destination \'" + dest + "\' exists but is read-only");
		}
	}

	public static int[] getSize(String dest) throws IOException {
		File destFile = new File(dest);
		BufferedImage src = ImageIO.read(destFile);
		int w = src.getWidth();
		int h = src.getHeight();
		return new int[]{w, h};
	}

	private static IMOperation getIMO(Integer width, Integer height) {
		IMOperation op = new IMOperation();
		op.addImage();
		if(height == null) {
			op.resize(width);
		} else {
			op.resize(width, height);
		}

		op.quality(Double.valueOf(85.0D));
		op.addImage();
		return op;
	}

	public static void scale(String ori, String dest, int width, int height) throws IOException, InterruptedException, IM4JavaException {
		File destFile = new File(dest);
		if(destFile.exists()) {
			destFile.delete();
		}

		IMOperation imo = getIMO(Integer.valueOf(width), Integer.valueOf(height));
		ConvertCmd cmd = new ConvertCmd(true);
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.indexOf("win") >= 0) {
			cmd.setSearchPath(getGMagickHome());
		}

		cmd.setErrorConsumer(StandardStream.STDERR);
		cmd.run(imo, new Object[]{ori, dest});
	}

	public static boolean scaleImage(String ori, String dest, int maxSize) throws IOException, InterruptedException, IM4JavaException {
		File oriFile = new File(ori);
		validate(oriFile, dest);
		BufferedImage src = ImageIO.read(oriFile);
		int w = src.getWidth();
		int h = src.getHeight();
		log.debug("origin with/height " + w + "/" + h);
		int size = Math.max(w, h);
		int tow = w;
		int toh = h;
		if(size > maxSize) {
			if(w > maxSize) {
				tow = maxSize;
				toh = h * maxSize / w;
			} else {
				tow = w * maxSize / h;
				toh = maxSize;
			}
		}

		log.debug("scaled with/height : " + tow + "/" + toh);
		scale(ori, dest, tow, toh);
		return true;
	}

	public static boolean scaleImageByWidth(String ori, String dest, int maxWidth) throws IOException, InterruptedException, IM4JavaException {
		File oriFile = new File(ori);
		validate(oriFile, dest);
		BufferedImage src = ImageIO.read(oriFile);
		int w = src.getWidth();
		int h = src.getHeight();
		log.debug("origin with/height " + w + "/" + h);
		int tow = w;
		int toh = h;
		if(w > maxWidth) {
			tow = maxWidth;
			toh = h * maxWidth / w;
		}

		log.debug("scaled with/height : " + tow + "/" + toh);
		scale(ori, dest, tow, toh);
		return true;
	}

	public static boolean scaleImage(String ori, String dest, int width, int height) throws IOException, InterruptedException, IM4JavaException {
		File oriFile = new File(ori);
		validate(oriFile, dest);
		BufferedImage src = ImageIO.read(oriFile);
		int w = src.getWidth();
		int h = src.getHeight();
		int tow;
		int toh;
		if(width < w && height < h) {
			tow = width;
			toh = height;
		} else {
			tow = w;
			toh = h;
		}

		scale(ori, dest, tow, toh);
		return true;
	}

	public static boolean truncateImage(String ori, String dest, int x, int y, int width, int height) throws IOException, InterruptedException, IM4JavaException {
		File oriFile = new File(ori);
		validate(oriFile, dest);
		IMOperation op = new IMOperation();
		op.addImage(new String[]{ori});
		op.crop(Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(x), Integer.valueOf(y));
		op.addImage(new String[]{dest});
		ConvertCmd convert = new ConvertCmd(true);
		convert.run(op, new Object[0]);
		return true;
	}

	public static boolean truncateImage(String ori, String dest, int x, int y, int size) throws IOException, InterruptedException, IM4JavaException {
		return truncateImage(ori, dest, x, y, size, size);
	}

	public static boolean truncateImageCenter(String ori, String dest, int side) throws IOException, InterruptedException, IM4JavaException {
		return truncateImage(ori, dest, side, side);
	}

	public static boolean truncateImage(String ori, String dest, int width, int height) throws IOException, InterruptedException, IM4JavaException {
		File oriFile = new File(ori);
		validate(oriFile, dest);
		BufferedImage src = ImageIO.read(oriFile);
		int w = src.getWidth();
		int h = src.getHeight();
		int min = Math.min(w, h);
		int side = Math.min(width, height);
		IMOperation op = new IMOperation();
		op.addImage(new String[]{ori});
		if(w > width || h > height) {
			if(min < side) {
				op.gravity("center").extent(Integer.valueOf(width), Integer.valueOf(height));
			} else {
				op.resize(Integer.valueOf(width), Integer.valueOf(height), Character.valueOf('^')).gravity("center").extent(Integer.valueOf(width), Integer.valueOf(height));
			}
		}

		op.addImage(new String[]{dest});
		ConvertCmd convert = new ConvertCmd(true);
		convert.run(op, new Object[0]);
		return true;
	}

	private static String getGMagickHome() {
		if(gmHome == null) {
			gmHome = System.getProperty(GMAGICK_HOME);
		}

		return gmHome;
	}
}
