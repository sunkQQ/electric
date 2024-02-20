package com.electric.img;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

/**
 * HTML转PDF
 */
public class HtmlToPdfTest {
    /**
     * 将HTML内容转换为PDF文件
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String htmlContent = "<html><body><h1>Hello, World!</h1></body></html>";
        OutputStream os = null;
        try {
            os = new FileOutputStream("output.pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);

            // 解决中文支持不良问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
