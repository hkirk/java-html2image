package gui.ava.html.imagemap;

import java.io.File;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public interface HtmlImageMap {
	List<LinkInfo> getLinks();

	String getImageMapHtml(String mapName, String imageURL);

	String getImageMapDocumentHtml(String imageURL);

	void saveImageMapDocument(String filename, String imageURL);

	void saveImageMapDocument(File file, String imageURL);
}
