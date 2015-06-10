Html2Image as only one useful Java class, **HtmlImageGenerator**.

Common usage is this:
```
HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
imageGenerator.loadHtml("<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.");
imageGenerator.saveAsImage("hello-world.png");
imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
```

Which will generate _hello-world.png_ image of the HTML and _hello-world.html_ file containing client-side image-map `<map>` (as in the example above).

### HtmlImageGenerator Methods ###
  * **loadUrl(_url_)** - Loads HTML from URL object or URL string.
  * **loadHtml(_html_)** - Loads HTML source.

  * **saveAsImage(_file_)** - Save loaded HTML as image.
  * **saveAsHtmlWithMap(_file_, _imageUrl_)** - Creates an HTML file containing client-side image-map `<map>` generated from HTML's links.

  * **getLinks()** - List all links in the HTML document and their corresponding href, target, title, position and dimension.
  * **getBufferedImage()** - Get AWT buffered image of the HTML.
  * **getLinksMapMarkup(_mapName_)** - Get HTML snippet of the client-side image-map `<map>` generated from the links.

  * **get/setOrientation(_orientation_)** - Get/Set document orientation (left-to-right or right-to-left).
  * **get/setSize(_dimension_)** - Get/Set size of the generated image.