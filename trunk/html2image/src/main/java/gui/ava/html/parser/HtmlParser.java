package gui.ava.html.parser;

import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * @author Yoav Aharoni
 */
public interface HtmlParser {

	Document getDocument();

	void setDocument(Document document);

	void load(URL url);

	void load(URI uri);

	void load(File file);

	void load(Reader reader);

	void load(InputStream inputStream);

	void loadHtml(String html);

	void loadURI(String uri);

	void removeDocumentType();
}
