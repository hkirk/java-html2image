package gui.ava.html.imagemap;

import org.w3c.dom.Element;

import java.io.File;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

/**
 * @author Yoav Aharoni
 */
public interface HtmlImageMap {
	Map<Element, Collection<ElementBox>> getClickableBoxes();

	String getImageMap(String mapName, String imageURL);

	void saveImageMap(Writer writer, String mapName, String imageURL);

	String getImageMapDocument(String imageURL);

	void saveImageMapDocument(String filename, String imageURL);

	void saveImageMapDocument(File file, String imageURL);

	void saveImageMapDocument(Writer writer, String imageURL, boolean closeWriter);
}
