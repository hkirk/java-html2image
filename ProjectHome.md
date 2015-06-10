# Html2Image #
This simple Java library converts plain HTML markup to image and provides client-side image-map using HTML `<map>` element.

## What Can I Do With It: Use Cases ##
  * **Programmatically compose images** - Use case: You need to compose images from other images and texts.
    * **Solution** - Create a web page using plain HTML, CSS and image and use Html2Image to convert it to an image.

  * **Improve your spam** - Use case: You need to send decorative HTML email.
    * You craft your HTML using CSS, images and links.
    * Alas, your clients open their Outlook, Gmail or any other mail client mailbox, and find your mail all scrambled up (merely resembles the presentation of the same HTML in a browser).
    * That's because Outlook and other clients only support limited functionality of HTML and have different implementations and bugs.
    * **Solution** - Use Html2Image to convert your original mail's HTML to a new HTML containing only image (`<img/>`) and client-side image-map (`<map/>`) for the links in the original HTML.
    * This also allow you to easily use unique fonts in your mail.

  * **Prevent spamming** - Use case: Your website has a list of contacts and their email addresses.
    * The email addresses are in plain text, allowing robots and spiders to harvest these addresses and spam your team with Viagra ads.
    * **Solution** - Use Html2Image to convert those email addresses to images.

## What Do I Give and What Do I Get ##
Html2Image allows you to transform this HTML markup:
```
<b>Hello World!</b> Please goto <a title="Goto Google" href="http://www.google.com">Google</a>.
```

To this visually equivalent HTML markup:
```
<map name="map">
<area href="http://www.google.com" coords="153,3,193,22" shape="rect" title="Goto Google">
</map>
<img border="0" usemap="#map" src="hello-world.png"/>
```

## Supported Images Formats ##
Html2Image allows you to save your HTML as GIF, PNG or JPEG image.

## API ##
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

## Download ##
You can download Html2Image from the [download](http://code.google.com/p/java-html2image/downloads/list) page or use it as Maven dependency:
```
<dependency>
   <groupId>gui.ava</groupId>
   <artifactId>html2image</artifactId>
   <version>0.9</version>
</dependency>

<repositories>
   <repository>
      <id>yoava</id>
      <name>AOL yoava</name>
      <url>http://yoava.artifactoryonline.com/yoava/repo</url>
   </repository>
</repositories>
```