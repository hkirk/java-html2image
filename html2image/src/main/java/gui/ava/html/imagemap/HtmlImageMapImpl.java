package gui.ava.html.imagemap;

import java.io.File;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageMapImpl implements HtmlImageMap {
	@Override
	public List<LinkInfo> getLinks() {
		return null;
	}

	@Override
	public String getImageMapHtml(String mapName, String imageURL) {
		return null;
	}

	@Override
	public String getImageMapDocumentHtml(String imageURL) {
		return null;
	}

	@Override
	public void saveImageMapDocument(String filename, String imageURL) {
	}

	@Override
	public void saveImageMapDocument(File file, String imageURL) {
	}
}
