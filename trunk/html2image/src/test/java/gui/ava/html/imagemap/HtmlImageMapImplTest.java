package gui.ava.html.imagemap;

import gui.ava.html.BaseTest;
import gui.ava.html.Html2Image;
import org.junit.Test;

import java.net.URL;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageMapImplTest extends BaseTest {
	@Test
	public void test1ImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromURL(getTest1Url());
		html2Image.getImageRenderer().saveImage("test1.png");
		html2Image.getHtmlImageMap().saveImageMapDocument("test1.html", "test1.png");
	}


	@Test
	public void googleImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromURL(new URL("http://www.google.com"));
		html2Image.getImageRenderer().saveImage("google.png");
		html2Image.getHtmlImageMap().saveImageMapDocument("google.html", "google.png");
	}

	@Test
	public void hebImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromHtml("<div>text<div style='text-align: right'><a onclick='alert(1)'>שלום!</a></div></div>");
		html2Image.getImageRenderer().saveImage("heb.png");
		html2Image.getHtmlImageMap().saveImageMapDocument("heb.html", "heb.png");
	}

	@Test
	public void imageImageMapDocument() throws Exception {
		final Html2Image html2Image = Html2Image.fromHtml("<div>HELLO!<a href='javascript: alert(1);'><img src='http://www.google.co.il/intl/en_com/images/srpr/logo1w.png'/></a></div>");
		html2Image.getImageRenderer().saveImage("image.png");
		html2Image.getHtmlImageMap().saveImageMapDocument("image.html", "heb.png");
	}
}
