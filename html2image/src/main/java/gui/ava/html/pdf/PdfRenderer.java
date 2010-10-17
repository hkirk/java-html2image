package gui.ava.html.pdf;

import java.io.File;
import java.io.OutputStream;

/**
 * @author Yoav Aharoni
 */
public interface PdfRenderer {
	void saveToPDF(OutputStream outputStream, boolean closeStream);

	void saveToPDF(File file);

	void saveToPDF(String file);
}
