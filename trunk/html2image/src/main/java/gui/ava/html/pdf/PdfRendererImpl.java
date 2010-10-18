package gui.ava.html.pdf;

import com.lowagie.text.DocumentException;
import gui.ava.html.exception.RenderException;
import gui.ava.html.parser.DocumentHolder;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * @author Yoav Aharoni
 */
public class PdfRendererImpl implements PdfRenderer {
	private DocumentHolder documentHolder;

	public PdfRendererImpl(DocumentHolder documentHolder) {
		this.documentHolder = documentHolder;
	}

	@Override
	public void saveToPDF(OutputStream outputStream, boolean closeStream) {
		try {
			ITextRenderer renderer = new ITextRenderer();
			final Document document = documentHolder.getDocument();
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
