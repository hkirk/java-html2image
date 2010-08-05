package gui.ava.html.image.generator;

import java.awt.*;


public class HtmlImageGeneratorTest {
	//	@Test
	public void testLoadUrl() {
		final HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		imageGenerator.setOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		imageGenerator.loadUrl("file:///D:/Temp/template.html");
		imageGenerator.saveAsImage("d:/temp/test1.png");
		imageGenerator.saveAsHtmlWithMap("/temp/test1.html", "test1.png");
	}

	//	@Test
	public void testLoadHtml() {
		final HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		imageGenerator.loadHtml("<p>test test <b>test</b> xxx <a title='test\" link' href='http://www.google.com'>link<br/>link</a>xxx xxx</p><img width=\"820\" height=\"347\" alt=\"ביטוח ישיר\" style=\"width: 820px; height: 347px;\" src=\"http://www.555.co.il/resources/images/mail/astro_email_template/555_1.jpg\"><br/><img width=\"820\" height=\"132\" alt=\"ביטוח ישיר\" style=\"width: 820px; height: 132px;\" src=\"http://www.555.co.il/resources/images/mail/astro_email_template/555_2.jpg\">");
		imageGenerator.saveAsImage("d:/temp/test2.png");
		imageGenerator.saveAsHtmlWithMap("/temp/test2.html", "test2.png");
	}
}
