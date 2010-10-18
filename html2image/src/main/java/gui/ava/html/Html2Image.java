package gui.ava.html;

import gui.ava.html.imagemap.HtmlImageMap;
import gui.ava.html.imagemap.HtmlImageMapImpl;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.pdf.PdfRenderer;
import gui.ava.html.pdf.PdfRendererImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * @author Yoav Aharoni
 */
public class Html2Image {
	private HtmlParser parser = new HtmlParserImpl();
	private HtmlImageMap htmlImageMap;
	private ImageRenderer imageRenderer;
	private PdfRenderer pdfRenderer;

	public HtmlParser getParser() {
		return parser;
	}

	public HtmlImageMap getHtmlImageMap() {
		if (htmlImageMap == null) {
			htmlImageMap = new HtmlImageMapImpl(getImageRenderer());
		}
		return htmlImageMap;
	}

	public PdfRenderer getPdfRenderer() {
		if (pdfRenderer == null) {
			pdfRenderer = new PdfRendererImpl(parser);
		}
		return pdfRenderer;
	}

	public ImageRenderer getImageRenderer() {
		if (imageRenderer == null) {
			imageRenderer = new ImageRendererImpl(parser);
		}
		return imageRenderer;
	}

	public static Html2Image fromDocument(Document document) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().setDocument(document);
		return html2Image;
	}

	public static Html2Image fromHtml(String html) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().loadHtml(html);
		return html2Image;
	}

	public static Html2Image fromURL(URL url) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(url);
		return html2Image;
	}

	public static Html2Image fromURI(URI uri) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(uri);
		return html2Image;
	}

	public static Html2Image fromFile(File file) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(file);
		return html2Image;
	}

	public static Html2Image fromReader(Reader reader) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(reader);
		return html2Image;
	}

	public static Html2Image fromInputStream(InputStream inputStream) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(inputStream);
		return html2Image;
	}
}
