package gui.ava.html.imagemap;

import gui.ava.html.exception.RenderException;
import gui.ava.html.renderer.LayoutHolder;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xhtmlrenderer.layout.Styleable;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.render.InlineLayoutBox;
import org.xhtmlrenderer.render.LineBox;

import java.io.*;
import java.util.*;

import static java.lang.String.format;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageMapImpl implements HtmlImageMap {
	private static Set<String> searchedAttributes = stringSet("href", "onclick", "ondblclick", "onmousedown", "onmouseup");
	private static Set<String> allowedAttributes = stringSet(
			"href", "target", "title", "class", "tabindex", "dir", "lang", "accesskey",
			"onblur", "onclick", "ondblclick", "onfocus",
			"onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup",
			"onkeydown", "onkeypress", "onkeyup");

	private LayoutHolder layoutHolder;

	public HtmlImageMapImpl(LayoutHolder layoutHolder) {
		this.layoutHolder = layoutHolder;
	}

	@Override
	public String getImageMap(String mapName, String imageURL) {
		final StringWriter writer = new StringWriter();
		saveImageMap(writer, mapName, imageURL);
		return writer.toString();
	}

	@Override
	public void saveImageMap(Writer writer, String mapName, String imageURL) {
		try {
			writer.append("<map name=\"").append(mapName).append("\">\n");
			for (ElementBox elementBox : getClickableBoxes()) {
				final int x1 = elementBox.getLeft();
				final int y1 = elementBox.getTop();
				final int x2 = x1 + elementBox.getWidth();
				final int y2 = y1 + elementBox.getHeight();
				writer.append(format("<area coords=\"%s,%s,%s,%s\" shape=\"rect\"", x1, y1, x2, y2));
				final NamedNodeMap attributes = elementBox.getElement().getAttributes();
				for (int i = 0, l = attributes.getLength(); i < l; i++) {
					final Node node = attributes.item(i);
					final String name = node.getNodeName();
					final String value = node.getNodeValue();
					if (name != null && value != null) {
						final String lowerName = name.toLowerCase();
						if (allowedAttributes.contains(lowerName)) {
							writer.append(" ").append(lowerName).append("=\"").append(value.replace("\"", "&quot;")).append("\"");
						}
					}
				}
				writer.append(">\n");
			}
			writer.append("</map>\n");
		} catch (IOException e) {
			throw new RenderException("IOException while writing client-side image map.", e);
		}
	}

	@Override
	public String getImageMapDocument(String imageURL) {
		final StringWriter writer = new StringWriter();
		saveImageMapDocument(writer, imageURL, true);
		return writer.toString();
	}

	@Override
	public void saveImageMapDocument(Writer writer, String imageURL, boolean closeWriter) {
		try {
			writer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
			writer.append("<html>\n<head></head>\n");
			writer.append("<body style=\"margin: 0; padding: 0; text-align: center;\">\n");
			saveImageMap(writer, "map", imageURL);
			writer.append("<img border=\"0\" usemap=\"#map\" src=\"");
			writer.append(imageURL);
			writer.append("\"/>\n");
			writer.append("</body>\n</html>");
		} catch (IOException e) {
			throw new RenderException("IOException while writing image map document.", e);
		} finally {
			if (closeWriter) {
				try {
					writer.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	@Override
	public void saveImageMapDocument(File file, String imageURL) {
		try {
			saveImageMapDocument(new FileWriter(file), imageURL, true);
		} catch (IOException e) {
			throw new RenderException(format("IOException while writing image map document '%s'.", file.getAbsolutePath()), e);
		}
	}

	@Override
	public void saveImageMapDocument(String filename, String imageURL) {
		saveImageMapDocument(new File(filename), imageURL);
	}

	@Override
	public Collection<ElementBox> getClickableBoxes() {
		final LinkedList<ElementBox> boxes = new LinkedList<ElementBox>();
		final Box rootBox = layoutHolder.getRootBox();
		recursiveAddLinks(rootBox, boxes, new HashSet<Styleable>());
		return boxes;
	}

	@SuppressWarnings({"unchecked"})
	private void recursiveAddLinks(Styleable styleable, LinkedList<ElementBox> boxes, Set<Styleable> visited) {
		if (styleable == null || visited.contains(styleable)) {
			return;
		}
		visited.add(styleable);

		if (isClickable(styleable)) {
			final ElementBox elementBox = createElementBox(styleable);
			if (elementBox != null) {
				boxes.add(elementBox);
			}
		}
		if (styleable instanceof Box) {
			for (Styleable child : (List<Styleable>) ((Box) styleable).getChildren()) {
				recursiveAddLinks(child, boxes, visited);
			}
		}
		if (styleable instanceof InlineLayoutBox) {
			for (Object child : (List<?>) ((InlineLayoutBox) styleable).getInlineChildren()) {
				if (child instanceof Styleable) {
					recursiveAddLinks((Styleable) child, boxes, visited);
				}
			}
		} else if (styleable instanceof BlockBox) {
			final List<Styleable> content = (List<Styleable>) ((BlockBox) styleable).getInlineContent();
			if (content != null) {
				for (Styleable child : content) {
					recursiveAddLinks(child, boxes, visited);
				}
			}
		} else if (styleable instanceof LineBox) {
			for (Styleable child : (List<Styleable>) ((LineBox) styleable).getNonFlowContent()) {
				recursiveAddLinks(child, boxes, visited);
			}
		}
	}

	private ElementBox createElementBox(Styleable styleable) {
		if (styleable instanceof InlineLayoutBox) {
			final InlineLayoutBox box = (InlineLayoutBox) styleable;
			final int width = Math.max(box.getInlineWidth(), box.getWidth());
			return new ElementBox(box.getElement(), box.getX(), box.getAbsY(), width, box.getHeight());
		}
		if (styleable instanceof Box) {
			final Box box = (Box) styleable;
			return new ElementBox(box.getElement(), box.getX(), box.getAbsY(), box.getWidth(), box.getHeight());
		}
		return null;
	}

	private boolean isClickable(Styleable box) {
		final Element element = box.getElement();
		if (element == null) {
			return false;
		}
		for (String attribute : searchedAttributes) {
			final String value = element.getAttribute(attribute);
			if (StringUtils.isNotBlank(value)) {
				return true;
			}
		}
		return false;
	}

	private static HashSet<String> stringSet(String... items) {
		return new HashSet<String>(Arrays.asList(items));
	}
}
