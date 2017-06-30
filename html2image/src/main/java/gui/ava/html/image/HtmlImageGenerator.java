package gui.ava.html.image;

import gui.ava.html.image.util.FormatNameUtil;
import gui.ava.html.image.util.SynchronousHTMLEditorKit;
import gui.ava.html.link.LinkInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public class HtmlImageGenerator {
    private JEditorPane editorPane;
    static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

    public HtmlImageGenerator() {
        editorPane = createJEditorPane();
    }

    public ComponentOrientation getOrientation() {
        return editorPane.getComponentOrientation();
    }

    public void setOrientation(ComponentOrientation orientation) {
        editorPane.setComponentOrientation(orientation);
    }

    public Dimension getSize() {
        return editorPane.getSize();
    }

    public void setSize(Dimension dimension) {
        editorPane.setPreferredSize(dimension);
    }

    public void loadUrl(URL url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadUrl(String url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadHtml(String html) {
        editorPane.setEditable(false);
        editorPane.setText(html);
        editorPane.setContentType("text/html");
        onDocumentLoad();
    }

    public String getLinksMapMarkup(String mapName) {
        final StringBuilder markup = new StringBuilder();
        markup.append("<map name=\"").append(mapName).append("\">\n");
        for (LinkInfo link : getLinks()) {
            final List<Rectangle> bounds = link.getBounds();
            for (Rectangle bound : bounds) {
                final int x1 = (int) bound.getX();
                final int y1 = (int) bound.getY();
                final int x2 = (int) (x1 + bound.getWidth());
                final int y2 = (int) (y1 + bound.getHeight());
                markup.append(String.format("<area href=\"%s\" coords=\"%s,%s,%s,%s\" shape=\"rect\"", link.getHref(), x1, y1, x2, y2));
                final String title = link.getTitle();
                if (title != null && !title.equals("")) {
                    markup.append(" title=\"").append(title.replace("\"", "&quot;")).append("\"");
                }
                markup.append(">\n");
            }
        }
        markup.append("</map>\n");
        return markup.toString();
    }

    public List<LinkInfo> getLinks() {
//        final LinkHarvester harvester = new LinkHarvester(editorPane);
//        return harvester.getLinks();
        return Collections.emptyList();
    }

    public void saveAsHtmlWithMap(String file, String imageUrl) {
        saveAsHtmlWithMap(new File(file), imageUrl);
    }

    public void saveAsHtmlWithMap(File file, String imageUrl) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
            writer.append("<html>\n<head></head>\n");
            writer.append("<body style=\"margin: 0; padding: 0; text-align: center;\">\n");
            final String htmlMap = getLinksMapMarkup("map");
            writer.write(htmlMap);
            writer.append("<img border=\"0\" usemap=\"#map\" src=\"");
            writer.append(imageUrl);
            writer.append("\"/>\n");
            writer.append("</body>\n</html>");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' html file", file), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {
                }
            }
        }

    }

    public void saveAsImage(String file) {
        saveAsImage(new File(file));
    }

    public void saveAsImage(File file) {
        BufferedImage image = getBufferedImage();

        BufferedImage bufferedImageToWrite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImageToWrite.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

        final String formatName = FormatNameUtil.formatForFilename(file.getName());

        try {
            if (!ImageIO.write(bufferedImageToWrite, formatName, file))
                throw new IOException("No formatter for specified file type [" + formatName + "] available");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
        }
    }

    protected void onDocumentLoad() {
    }

    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public BufferedImage getBufferedImage() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(editorPane.getPreferredSize());
        frame.setUndecorated(true);
        frame.add(editorPane);
        frame.pack();

        Dimension prefSize = frame.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, prefSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img.getGraphics();

        frame.setVisible(true);
        frame.paint(graphics);
        frame.setVisible(false);
        frame.dispose();

        return img;
    }

    protected JEditorPane createJEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setSize(getDefaultSize());
        editorPane.setEditable(false);
        final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html");
        editorPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("page")) {
                    onDocumentLoad();
                }
            }
        });
        return editorPane;
    }

    public void show() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setTitle("My First Swing Application");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Welcome");
        frame.add(label);
        frame.add(editorPane);
        frame.pack();
        frame.setVisible(true);
    }
}
