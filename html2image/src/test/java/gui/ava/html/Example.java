package gui.ava.html;

import gui.ava.html.image.*;

/**
 * Created by hki on 07-01-2016.
 */
public class Example {

    public static void main(String[] args) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml("<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.");
        imageGenerator.saveAsImage("hello-world.png");
        imageGenerator.saveAsImage("hello-world.jpeg");
        //imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    }
}
