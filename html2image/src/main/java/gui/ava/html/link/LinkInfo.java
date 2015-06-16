package gui.ava.html.link;

import java.awt.*;
import java.util.List;

/**
 * @author Yoav Aharoni
 */
public class LinkInfo {
        private String href;
        private String title;
        private List<Rectangle> bounds;

        public LinkInfo(String href, String title, List<Rectangle> bounds) {
                this.href = href;
                this.title = title;
                this.bounds = bounds;
        }

        public String getHref() {
                return href;
        }

        public String getTitle() {
                return title;
        }

        public List<Rectangle> getBounds() {
                return bounds;
        }
}
