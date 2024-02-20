package com.electric.img;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.layout.BrowserCanvas;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import cz.vutbr.web.css.MediaSpec;
import freemarker.template.Template;

/**
 * @author sunk
 * @date 2024/02/20
 */
public class FreemarkerUtils {

    private static String    mediaType            = "screen";
    private static Dimension windowSize           = new Dimension(1200, 600);
    private static boolean   cropWindow           = false;
    private static boolean   loadImages           = true;
    private static boolean   loadBackgroundImages = true;

    private static String getTemplate(String template, Map<String, Object> model) throws Exception {
        if (template == null) {
            return null;
        }
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(template)).process(model, out);
        return out.toString();
    }

    public static void turnImage(String template, Map<String, Object> map) throws Exception {
        String html = getTemplate(template, map);
        Document document = Jsoup.parse(html);
        W3CDom w3CDom = new W3CDom();
        org.w3c.dom.Document w3cDoc = w3CDom.fromJsoup(document);
        DOMAnalyzer da = new DOMAnalyzer(w3cDoc, null);
        MediaSpec media = new MediaSpec(mediaType);
        media.setDimensions((float) windowSize.width, (float) windowSize.height);
        media.setDeviceDimensions((float) windowSize.width, (float) windowSize.height);
        da.setMediaSpec(media);
        da.attributesToStyles();
        da.addStyleSheet((URL) null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
        da.addStyleSheet((URL) null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
        da.addStyleSheet((URL) null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT);
        da.getStyleSheets();
        BrowserCanvas contentCanvas = new BrowserCanvas(w3cDoc.getDocumentElement(), da, null);
        contentCanvas.setAutoMediaUpdate(false);
        contentCanvas.getConfig().setClipViewport(cropWindow);
        contentCanvas.getConfig().setLoadImages(loadImages);
        contentCanvas.getConfig().setLoadBackgroundImages(loadBackgroundImages);
        contentCanvas.createLayout(new Dimension(297, 210));
        OutputStream out = new FileOutputStream("d:\\html111.png");
        ImageIO.write(contentCanvas.getImage(), "png", out);
    }
}
