package gui.ava.html.link;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public class LinkHarvester {
	private final JTextComponent textComponent;
	private final List<LinkInfo> links = new ArrayList<LinkInfo>();

	public LinkHarvester(JEditorPane textComponent) {
		this.textComponent = textComponent;
		harvestElement(textComponent.getDocument().getDefaultRootElement());
	}

	public List<LinkInfo> getLinks() {
		return links;
	}

	private void harvestElement(Element element) {
		if (element == null) {
			return;
		}

		final AttributeSet attributes = element.getAttributes();
		final Enumeration<?> attributeNames = attributes.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			final Object key = attributeNames.nextElement();
			if (HTML.Tag.A.equals(key)) {
				final Map<String, String> linkAttributes = harvestAttributes(element);
				final List<Rectangle> bounds = harvestBounds(element);
				if (!linkAttributes.isEmpty() && !bounds.isEmpty()) {
					links.add(new LinkInfo(linkAttributes, bounds));
				}
			}
		}

		for (int i = 0; i < element.getElementCount(); i++) {
			final Element child = element.getElement(i);
			harvestElement(child);
		}
	}

	private Map<String, String> harvestAttributes(Element element) {
		final Object value = element.getAttributes().getAttribute(HTML.Tag.A);
		if (value instanceof SimpleAttributeSet) {
			final SimpleAttributeSet attributeSet = (SimpleAttributeSet) value;
			final Map<String, String> result = new HashMap<String, String>();
			addAttribute(attributeSet, result, HTML.Attribute.HREF);
			addAttribute(attributeSet, result, HTML.Attribute.TARGET);
			addAttribute(attributeSet, result, HTML.Attribute.TITLE);
			addAttribute(attributeSet, result, HTML.Attribute.CLASS);
			addAttribute(attributeSet, result, "tabindex");
			addAttribute(attributeSet, result, "dir");
			addAttribute(attributeSet, result, "lang");
			addAttribute(attributeSet, result, "accesskey");

			addAttribute(attributeSet, result, "onblur");
			addAttribute(attributeSet, result, "onclick");
			addAttribute(attributeSet, result, "ondblclick");
			addAttribute(attributeSet, result, "onfocus");
			addAttribute(attributeSet, result, "onmousedown");
			addAttribute(attributeSet, result, "onmousemove");
			addAttribute(attributeSet, result, "onmouseout");
			addAttribute(attributeSet, result, "onmouseover");
			addAttribute(attributeSet, result, "onmouseup");
			addAttribute(attributeSet, result, "onkeydown");
			addAttribute(attributeSet, result, "onkeypress");
			addAttribute(attributeSet, result, "onkeyup");
			return result;
		}

		return Collections.emptyMap();
	}

	private void addAttribute(SimpleAttributeSet attributeSet, Map<String, String> result, Object attribute) {
		final String attName = attribute.toString();
		final String attValue = (String) attributeSet.getAttribute(attribute);
		if (attValue != null && !attValue.equals("")) {
			result.put(attName, attValue);
		}
	}

	private List<Rectangle> harvestBounds(Element element) {
		final List<Rectangle> boundsList = new ArrayList<Rectangle>();
		try {
			final int startOffset = element.getStartOffset();
			final int endOffset = element.getEndOffset();

			Rectangle lastBounds = null;
			for (int i = startOffset; i <= endOffset; i++) {
				final Rectangle bounds = textComponent.modelToView(i);
				// skip null rectangle
				if (bounds == null) {
					continue;
				}

				// set lastBounds
				if (lastBounds == null) {
					lastBounds = bounds;
					continue;
				}

				// union horizontal bounds
				if (bounds.getY() == lastBounds.getY()) {
					lastBounds = lastBounds.union(bounds);
					continue;
				}

				// add lastBounds to list
				if (lastBounds.getWidth() > 1 && lastBounds.getHeight() > 1) {
					boundsList.add(lastBounds);
				}
				lastBounds = null;
			}

			// add lastBounds to list
			if (lastBounds != null && lastBounds.getWidth() > 1 && lastBounds.getHeight() > 1) {
				boundsList.add(lastBounds);
			}
			return boundsList;

		} catch (BadLocationException e) {
			throw new RuntimeException("Got BadLocationException", e);
		}
	}
}
