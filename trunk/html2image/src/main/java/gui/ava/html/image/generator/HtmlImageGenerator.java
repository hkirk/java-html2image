package gui.ava.html.image.generator;

import gui.ava.html.image.util.FormatNameUtil;
import gui.ava.html.image.util.SynchronousHTMLEditorKit;
import gui.ava.html.link.LinkHarvester;
import gui.ava.html.link.LinkInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageGenerator {
	private JEditorPane editorPane;
	static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	public HtmlImageGenerator() {
		editorPane = createJEditorPane();
	}

	public ComponentOrientation getOrientation() {
		return editorPane.getComponentOrientation();
	}

	public void setOrientation(ComponentOrientation orientation) {
		editorPane.setComponentOrientation(orientation);
	}

	public Dimension getSize() {
		return editorPane.getSize();
	}

	public void setSize(Dimension dimension) {
		editorPane.setSize(dimension);
	}

	public void loadUrl(URL url) {
		try {
			editorPane.setPage(url);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while loading %s", url), e);
		}
	}

	public void loadUrl(String url) {
		try {
			editorPane.setPage(url);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while loading %s", url), e);
		}
	}

	public void loadHtml(String html) {
		editorPane.setText(html);
		onDocumentLoad();
	}

	public String getLinksMapMarkup(String mapName) {
		final StringBuilder markup = new StringBuilder();
		markup.append("<map name=\"").append(mapName).append("\">\n");
		for (LinkInfo link : getLinks()) {
			final List<Rectangle> bounds = link.getBounds();
			for (Rectangle bound : bounds) {
				final int x1 = (int) bound.getX();
				final int y1 = (int) bound.getY();
				final int x2 = (int) (x1 + bound.getWidth());
				final int y2 = (int) (y1 + bound.getHeight());
				markup.append(String.format("<area coords=\"%s,%s,%s,%s\" shape=\"rect\"", x1, y1, x2, y2));
				for (Map.Entry<String, String> entry : link.getAttributes().entrySet()) {
					String attName = entry.getKey();
					String value = entry.getValue();
					markup.append(" ").append(attName).append("=\"").append(value.replace("\"", "&quot;")).append("\"");
				}
				markup.append(">\n");
			}
		}
		markup.append("</map>\n");
		return markup.toString();
	}

	public List<LinkInfo> getLinks() {
		final LinkHarvester harvester = new LinkHarvester(editorPane);
		return harvester.getLinks();
	}

	public void saveAsHtmlWithMap(String file, String imageUrl) {
		saveAsHtmlWithMap(new File(file), imageUrl);
	}

	public void saveAsHtmlWithMap(File file, String imageUrl) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
			writer.append("<html>\n<head></head>\n");
			writer.append("<body style=\"margin: 0; padding: 0; text-align: center;\">\n");
			final String htmlMap = getLinksMapMarkup("map");
			writer.write(htmlMap);
			writer.append("<img border=\"0\" usemap=\"#map\" src=\"");
			writer.append(imageUrl);
			writer.append("\"/>\n");
			writer.append("</body>\n</html>");
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while saving '%s' html file", file), e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ignore) {
				}
			}
		}

	}

	public void saveAsImage(String file) {
		saveAsImage(new File(file));
	}

	public void saveAsImage(File file) {

		BufferedImage img = getBufferedImage();
		try {
			final String formatName = FormatNameUtil.formatForFilename(file.getName());
			ImageIO.write(img, formatName, file);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
		}
	}

	protected void onDocumentLoad() {
	}

	public Dimension getDefaultSize() {
		return DEFAULT_SIZE;
	}

	public BufferedImage getBufferedImage() {
		Dimension prefSize = editorPane.getPreferredSize();
		BufferedImage img = new BufferedImage(prefSize.width, editorPane.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = img.getGraphics();
		editorPane.setSize(prefSize);
		editorPane.paint(graphics);
		return img;
	}

	protected JEditorPane createJEditorPane() {
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setSize(getDefaultSize());
		editorPane.setEditable(false);
		final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
		editorPane.setEditorKitForContentType("text/html", kit);
		editorPane.setContentType("text/html");
		editorPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("page")) {
					onDocumentLoad();
				}
			}
		});
		return editorPane;
	}
}
