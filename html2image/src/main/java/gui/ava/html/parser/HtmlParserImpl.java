package gui.ava.html.parser;

import org.apache.xerces.parsers.DOMParser;
import org.cyberneko.html.HTMLConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import java.io.*;
import java.net.URI;
import java.net.URL;

import static java.lang.String.format;

/**
 * @author Yoav Aharoni
 */
public class HtmlParserImpl implements HtmlParser {
	private DOMParser domParser;
	private Document document;

	public HtmlParserImpl() {
		domParser = new DOMParser(new HTMLConfiguration());
		try {
			domParser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
		} catch (SAXNotRecognizedException e) {
			throw new ParseException("Can't create HtmlParserImpl", e);
		} catch (SAXNotSupportedException e) {
			throw new ParseException("Can't create HtmlParserImpl", e);
		}
	}

	@Override
	public DOMParser getDomParser() {
		return domParser;
	}

	@Override
	public void setDomParser(DOMParser domParser) {
		this.domParser = domParser;
	}

	@Override
	public Document getDocument() {
		return document;
	}

	@Override
	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public void load(Reader reader) {
		try {
			domParser.parse(new InputSource(reader));
			document = domParser.getDocument();
		} catch (SAXException e) {
			throw new ParseException("SAXException while parsing HTML.", e);
		} catch (IOException e) {
			throw new ParseException("IOException while parsing HTML.", e);
		} finally {
			try {
				reader.close();
			} catch (IOException ignore) {
			}
		}
	}

	@Override
	public void load(InputStream inputStream) {
		try {
			domParser.parse(new InputSource(inputStream));
			document = domParser.getDocument();
		} catch (SAXException e) {
			throw new ParseException("SAXException while parsing HTML.", e);
		} catch (IOException e) {
			throw new ParseException("IOException while parsing HTML.", e);
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException ignore) {
			}
		}
	}

	@Override
	public void loadURI(String uri) {
		try {
			domParser.parse(new InputSource(uri));
			document = domParser.getDocument();
		} catch (SAXException e) {
			throw new ParseException(format("SAXException while parsing HTML from \"%s\".", uri), e);
		} catch (IOException e) {
			throw new ParseException(format("SAXException while parsing HTML from \"%s\".", uri), e);
		}
	}

	@Override
	public void load(File file) {
		load(file.toURI());
	}

	@Override
	public void load(URL url) {
		loadURI(url.toExternalForm());
	}

	@Override
	public void load(URI uri) {
		loadURI(uri.toString());
	}

	@Override
	public void loadHtml(String html) {
		load(new StringReader(html));
	}
}
