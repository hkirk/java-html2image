package gui.ava.html.renderer;

import gui.ava.html.exception.RenderException;
import gui.ava.html.parser.DocumentHolder;
import org.w3c.dom.Document;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import javax.imageio.ImageWriteParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Yoav Aharoni
 */
public class ImageRendererImpl implements ImageRenderer {
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 768;

	private DocumentHolder documentHolder;

	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	private boolean autoHeight = true;

	private String imageFormat = null;
	private float writeCompressionQuality = 1.0f;
	private int writeCompressionMode = ImageWriteParam.MODE_COPY_FROM_METADATA;
	private String writeCompressionType = null;

	public ImageRendererImpl(DocumentHolder documentHolder) {
		this.documentHolder = documentHolder;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public ImageRenderer setWidth(int width) {
		this.width = width;
		return this;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ImageRenderer setHeight(int height) {
		this.height = height;
		return this;
	}

	@Override
	public boolean isAutoHeight() {
		return autoHeight;
	}

	@Override
	public ImageRenderer setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
		return this;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public ImageRenderer setImageType(String imageType) {
		this.imageFormat = imageType;
		return this;
	}

	public float getWriteCompressionQuality() {
		return writeCompressionQuality;
	}

	public ImageRenderer setWriteCompressionQuality(float writeCompressionQuality) {
		this.writeCompressionQuality = writeCompressionQuality;
		return this;
	}

	public int getWriteCompressionMode() {
		return writeCompressionMode;
	}

	public ImageRenderer setWriteCompressionMode(int writeCompressionMode) {
		this.writeCompressionMode = writeCompressionMode;
		return this;
	}

	public String getWriteCompressionType() {
		return writeCompressionType;
	}

	public ImageRenderer setWriteCompressionType(String writeCompressionType) {
		this.writeCompressionType = writeCompressionType;
		return this;
	}

	@Override
	public BufferedImage getBufferedImage(int imageType) {
		Graphics2DRenderer renderer = new Graphics2DRenderer();
		final Document document = documentHolder.getDocument();
		renderer.setDocument(document, document.getDocumentURI());
		Dimension dimension = new Dimension(width, height);
		BufferedImage bufferedImage = new BufferedImage(width, height, imageType);

		if (autoHeight) {
			// do layout with temp buffer
			Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
			renderer.layout(graphics2D, new Dimension(width, height));
			graphics2D.dispose();

			Rectangle size = renderer.getMinimumSize();
			final int autoWidth = (int) size.getWidth();
			final int autoHeight = (int) size.getHeight();
			bufferedImage = new BufferedImage(autoWidth, autoHeight, imageType);
			dimension = new Dimension(autoWidth, autoHeight);
		}

		Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
		renderer.layout(graphics2D, dimension);
		renderer.render(graphics2D);
		graphics2D.dispose();
		return bufferedImage;
	}


	public BufferedImage getBufferedImage() {
		return getBufferedImage(BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void saveImage(OutputStream outputStream, boolean closeStream) {
		save(outputStream, null, closeStream);
	}

	@Override
	public void saveImage(File file) {
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			save(outputStream, file.getName(), true);
		} catch (IOException e) {
			throw new RenderException("IOException while rendering image to " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public void saveImage(String filename) {
		saveImage(new File(filename));
	}

	private void save(OutputStream outputStream, String filename, boolean closeStream) {
		try {
			final String imageFormat = getImageFormat(filename);
			final FSImageWriter imageWriter = getImageWriter(imageFormat);
			final boolean isBMP = "bmp".equalsIgnoreCase(imageFormat);
			final BufferedImage bufferedImage = getBufferedImage(isBMP ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
			imageWriter.write(bufferedImage, outputStream);
		} catch (IOException e) {
			throw new RenderException("IOException while rendering image", e);
		} finally {
			if (closeStream) {
				try {
					outputStream.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	private FSImageWriter getImageWriter(String imageFormat) {
		FSImageWriter imageWriter = new FSImageWriter(imageFormat);
		imageWriter.setWriteCompressionMode(writeCompressionMode);
		imageWriter.setWriteCompressionQuality(writeCompressionQuality);
		imageWriter.setWriteCompressionType(writeCompressionType);
		return imageWriter;
	}

	private String getImageFormat(String filename) {
		if (this.imageFormat != null) {
			return imageFormat;
		}
		if (filename != null) {
			return FormatNameUtil.formatForFilename(filename);
		}
		return FormatNameUtil.getDefaultFormat();
	}
}