package gui.ava.html.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

/**
 * @author Yoav Aharoni
 */
public interface ImageRenderer extends LayoutHolder {
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

	ImageRendererImpl clearCache();

	float getWriteCompressionQuality();

	ImageRenderer setWriteCompressionQuality(float writeCompressionQuality);

	int getWriteCompressionMode();

	ImageRenderer setWriteCompressionMode(int writeCompressionMode);

	String getWriteCompressionType();

	ImageRenderer setWriteCompressionType(String writeCompressionType);
}
