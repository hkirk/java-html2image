package gui.ava.html.imagemap;

import gui.ava.html.BaseTest;
import gui.ava.html.Html2Image;
import org.junit.Test;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageMapImplTest extends BaseTest {
	@Test
	public void testSaveImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromURL(getTest1Url());
		html2Image.getImageRenderer().saveImage("test.png");
		html2Image.getHtmlImageMap().saveImageMapDocument("test.html", "test.png");
	}
}
