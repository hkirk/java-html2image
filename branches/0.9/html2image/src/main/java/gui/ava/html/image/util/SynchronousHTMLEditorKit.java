package gui.ava.html.image.util;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

/**
 * @author Yoav Aharoni
 */
public class SynchronousHTMLEditorKit extends HTMLEditorKit {

	public Document createDefaultDocument() {
		HTMLDocument doc = (HTMLDocument) super.createDefaultDocument();
		doc.setAsynchronousLoadPriority(-1);
		return doc;
	}

	public ViewFactory getViewFactory() {
		return new HTMLFactory() {
			public View create(Element elem) {
				View view = super.create(elem);
				if (view instanceof ImageView) {
					((ImageView) view).setLoadsSynchronously(true);
				}
				return view;
			}
		};
	}
}
