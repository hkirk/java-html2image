package gui.ava.html.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

/**
 * @author Yoav Aharoni
 */
public interface ImageRenderer {
	int getWidth();

	ImageRenderer setWidth(int width);

	int getHeight();

	ImageRenderer setHeight(int height);

	boolean isAutoHeight();

	ImageRenderer setAutoHeight(boolean autoHeight);

	String getImageFormat();

	ImageRenderer setImageType(String imageType);

	BufferedImage getBufferedImage(int imageType);

	BufferedImage getBufferedImage();

	void saveImage(OutputStream outputStream, boolean closeStream);

	void saveImage(String filename);

	void saveImage(File file);
}
