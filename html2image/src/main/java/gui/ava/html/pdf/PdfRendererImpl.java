package gui.ava.html.pdf;

import com.lowagie.text.DocumentException;
import gui.ava.html.exception.RenderException;
import gui.ava.html.parser.HtmlParser;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * @author Yoav Aharoni
 */
public class PdfRendererImpl implements PdfRenderer {
	private HtmlParser parser;

	public PdfRendererImpl(HtmlParser parser) {
		this.parser = parser;
	}

	@Override
	public void saveToPDF(OutputStream outputStream, boolean closeStream) {
		try {
			ITextRenderer renderer = new ITextRenderer();
			parser.removeDocumentType();
			final Document document = parser.getDocument();
			renderer.setDocument(document, document.getDocumentURI());
			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (DocumentException e) {
			throw new RenderException("DocumentException while rendering PDF", e);
		} finally {
			if (closeStream) {
				try {
					outputStream.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	@Override
	public void saveToPDF(File file) {
		try {
			saveToPDF(new FileOutputStream(file), true);
		} catch (FileNotFoundException e) {
			throw new RenderException(String.format("File not found %s", file.getAbsolutePath()), e);
		}
	}

	@Override
	public void saveToPDF(String file) {
		saveToPDF(new File(file));
	}
}
