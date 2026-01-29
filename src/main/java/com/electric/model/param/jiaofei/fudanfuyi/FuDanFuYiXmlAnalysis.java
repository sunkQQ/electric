package com.electric.model.param.jiaofei.fudanfuyi;

import java.io.ByteArrayInputStream;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.OMXMLParserWrapper;

import com.electric.model.enums.jiaofei.fudanfuyi.FuDanFuYiStatusEnum;

/**
 * 复旦复翼接口返回
 * @author yzh
 * @since 2020/5/21 16:26
 */
public class FuDanFuYiXmlAnalysis {

    public static final String RESP_CODE = "respcode";

    public static final String RESP_MSG  = "respinfo";

    public static final String TRANSCODE = "transcode";

    private OMDocument         document;

    public FuDanFuYiXmlAnalysis(String xml) throws Exception {
        OMXMLParserWrapper omBuilder = OMXMLBuilderFactory.createOMBuilder(new ByteArrayInputStream(xml.getBytes("GBK")));
        document = omBuilder.getDocument();
    }

    public OMElement getItem(String tag) {
        System.out.println("-------------------");
        System.out.println(document.getOMDocumentElement().getText());
        System.out.println(document.getOMDocumentElement().getQName());
        System.out.println("-------------------");
        return document.getOMDocumentElement().getFirstChildWithName(new QName(tag));
    }

    public OMElement getItem(OMElement element, String tag) {
        return element.getFirstChildWithName(new QName(tag));
    }

    public String getItemText(String tag) {
        OMElement element = getItem(tag);
        if (element == null) {
            return null;
        }
        return element.getText().trim();
    }

    public String getItemText(OMElement element, String tag) {
        OMElement element2 = getItem(element, tag);
        if (element2 == null) {
            return null;
        }
        return element2.getText().trim();
    }

    public boolean isSuccess() {
        String code = getItemText(RESP_CODE);
        return FuDanFuYiStatusEnum.SUCCESS.getCode().equals(code);
    }

    public String getMsg() {
        return getItemText(RESP_MSG);
    }
}
