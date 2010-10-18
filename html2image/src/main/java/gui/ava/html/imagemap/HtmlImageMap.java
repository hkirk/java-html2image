package gui.ava.html.imagemap;

import java.io.File;
import java.io.Writer;
import java.util.Collection;

/**
 * @author Yoav Aharoni
 */
public interface HtmlImageMap {
	Collection<ElementBox> getClickableBoxes();

	String getImageMap(String mapName, String imageURL);

	void saveImageMap(Writer writer, String mapName, String imageURL);

	String getImageMapDocument(String imageURL);

	void saveImageMapDocument(String filename, String imageURL);

	void saveImageMapDocument(File file, String imageURL);

	void saveImageMapDocument(Writer writer, String imageURL, boolean closeWriter);
}
