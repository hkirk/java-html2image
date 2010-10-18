package gui.ava.html.imagemap;

import org.w3c.dom.Element;

import java.util.Collection;

/**
 * @author Yoav Aharoni
 */
public class ElementBox {
	private Element element;
	private int left;
	private int top;
	private int width;
	private int height;

	public ElementBox(Element element, int left, int top, int width, int height) {
		this.element = element;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	public Element getElement() {
		return element;
	}

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRight() {
		return left + width;
	}

	public int getBottom() {
		return top + height;
	}

	public boolean isEmpty() {
		return width <= 0 || height <= 0;
	}

	public boolean containedIn(Collection<ElementBox> elementBoxes) {
		for (ElementBox box : elementBoxes) {
			if (containedIn(box)) {
				return true;
			}
		}
		return false;
	}

	public boolean containedIn(ElementBox box) {
		return getTop() >= box.getTop() && getLeft() >= box.getTop()
				&& getBottom() <= box.getBottom() && getRight() <= box.getRight();
	}
}
