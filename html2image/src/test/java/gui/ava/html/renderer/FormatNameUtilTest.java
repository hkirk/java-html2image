package gui.ava.html.renderer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yoav Aharoni
 */
public class FormatNameUtilTest {
	@Test
	public void testGif() {
		final String format = FormatNameUtil.formatForFilename("test.file.gif");
		Assert.assertEquals("gif", format);
	}

	@Test
	public void testPng() {
		final String format = FormatNameUtil.formatForFilename("test.file.png");
		Assert.assertEquals("png", format);
	}

	@Test
	public void testJpg() {
		final String format = FormatNameUtil.formatForFilename("test.file.jpg");
		Assert.assertEquals("jpg", format);
	}

	@Test
	public void testNoName() {
		final String format = FormatNameUtil.formatForFilename(".gif");
		Assert.assertEquals("gif", format);
	}

	@Test
	public void testNoExtension() {
		final String format = FormatNameUtil.formatForFilename("name.");
		Assert.assertEquals("png", format);
	}

	@Test
	public void testEmptyFilename() {
		final String format = FormatNameUtil.formatForFilename("");
		Assert.assertEquals("png", format);
	}
}
