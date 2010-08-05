package gui.ava.html.link;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author Yoav Aharoni
 */
public class LinkInfo {
	private Map<String, String> attributes;
	private List<Rectangle> bounds;

	public LinkInfo(Map<String, String> attributes, List<Rectangle> bounds) {
		this.attributes = attributes;
		this.bounds = bounds;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public List<Rectangle> getBounds() {
		return bounds;
	}
}
