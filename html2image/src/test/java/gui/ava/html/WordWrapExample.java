package gui.ava.html;

import gui.ava.html.image.HtmlImageGenerator;

import java.awt.*;

/**
 * Created by hki on 12-01-2016.
 */
public class WordWrapExample {
    public static void main(String[] args) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml("<html>\n" +
                "<body>\n" +
                "<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.\n" +
                "<div style=\"width:200px\">" +
                    "word wrap all " +
                "<span style=\"word-wrap:break-all; display:block;\">" +
                    "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "</span>" +
                "</div>" +

                "<div style=\"width:200px\">" +
                "word wrap word " +
                "<span style=\"word-wrap:break-word; display:block;\">" +
                "wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwww w wwwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwwwww w w w wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwww wwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwww w wwwwwwwwwwwwwwww w wwwwwwwwwwwwwwwwwwwwww w w w wwwwwwwwwwwwwwwwwwwwwww" +
                "</span>" +
                "</div>" +

                "\n" +
                "</body>\n" +
                "</html>");
        //imageGenerator.saveAsImage("hello-form.png");
        //imageGenerator.setSize(new Dimension(150, 600));
        //imageGenerator.saveAsImage("word-wrap.jpeg");
        imageGenerator.show();
    }
}
