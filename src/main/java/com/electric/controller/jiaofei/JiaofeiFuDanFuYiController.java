package com.electric.controller.jiaofei;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.electric.exception.BizException;
import com.electric.param.jiaofei.fudanfuyi.FuDanFuYiQueryFeeParam;
import com.electric.param.jiaofei.fudanfuyi.JiaofeiFudanfuyiParam;
import com.electric.util.DateUtil;
import com.electric.util.ReflectionUtil;
import com.electric.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 复旦复翼接口
 *
 * @author sunk
 * @date 2024/06/04
 */

@Slf4j
@RestController
@RequestMapping("/queryfee")
public class JiaofeiFuDanFuYiController {
    private static final String                    HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

    private static final List<Map<String, String>> LIST = new ArrayList<>();
    static {
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("feeitemdeford", "1");
        map1.put("feeitemname", "java从入门到精通");
        map1.put("feeord", StringUtil.generateMixNum(6));
        map1.put("paidamt", "1.47");
        LIST.add(map1);

        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("feeitemdeford", "2");
        map2.put("feeitemname", "java从入门到放弃");
        map2.put("feeord", StringUtil.generateMixNum(6));
        map2.put("paidamt", "2.58");
        LIST.add(map2);

        Map<String, String> map3 = new LinkedHashMap<>();
        map3.put("feeitemdeford", "2");
        map3.put("feeitemname", "java从入门到121312放弃");
        map3.put("feeord", StringUtil.generateMixNum(6));
        map3.put("paidamt", "3.69");
        LIST.add(map3);
    }

    @RequestMapping(value = "/queryfee.action", method = RequestMethod.POST)
    public String queryfee(HttpServletRequest request, JiaofeiFudanfuyiParam param) throws Exception {
        /* 请求参数 */
        Document doc = DocumentHelper.parseText(param.getData());
        Element rootElt = doc.getRootElement();
        Element transcode = rootElt.element("transcode");
        Element userid = rootElt.element("userid");
        FuDanFuYiQueryFeeParam queryFeeParam = new FuDanFuYiQueryFeeParam();

        /* 返回 */
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("", "");
        OMElement abcsh = fac.createOMElement("abcsh", omNs);

        OMElement transcodeElement = fac.createOMElement("transcode", omNs);
        transcodeElement.setText(transcode.getText());
        abcsh.addChild(transcodeElement);

        OMElement respcode = fac.createOMElement("respcode", omNs);
        respcode.setText("0000");
        abcsh.addChild(respcode);

        OMElement respinfo = fac.createOMElement("respinfo", omNs);
        respinfo.setText("1");
        abcsh.addChild(respinfo);

        OMElement respdate = fac.createOMElement("respdate", omNs);
        respdate.setText(DateUtil.getTimeNowFormat(DateUtil.DAY_FORMAT2));
        abcsh.addChild(respdate);

        OMElement resptime = fac.createOMElement("resptime", omNs);
        resptime.setText(DateUtil.getTimeNowFormat(DateUtil.HOUR_MINUTE_SECOND_FORMAT));
        abcsh.addChild(resptime);

        OMElement useridElement = fac.createOMElement("userid", omNs);
        useridElement.setText(userid.getText());
        abcsh.addChild(useridElement);
        if ("SH02".equals(transcode.getText())) {
            for (Element element : rootElt.elements()) {
                ReflectionUtil.setValueByFieldName(queryFeeParam, StringUtil.getFirstCharLower(element.getName()), element.getText());
            }

            OMElement feecnt = fac.createOMElement("feecnt", omNs);
            feecnt.setText("2");
            abcsh.addChild(feecnt);

            OMElement username = fac.createOMElement("username", omNs);
            username.setText("测试姓名");
            abcsh.addChild(username);

            OMElement fees = fac.createOMElement("fees", omNs);
            if (userid.getText().equals("24052302") || userid.getText().equals("234040020")) {
                for (Map<String, String> map : LIST) {
                    OMElement fee = fac.createOMElement("fee", omNs);
                    for (String key : map.keySet()) {
                        OMElement keyElement = fac.createOMElement(key, omNs);
                        keyElement.setText(map.get(key));
                        fee.addChild(keyElement);
                    }
                    fees.addChild(fee);
                }
            }
            abcsh.addChild(fees);

            return HEAD + abcsh;
        } else if ("SH03".equals(transcode.getText())) {
            OMElement respseqno = fac.createOMElement("respseqno", omNs);
            respseqno.setText(StringUtil.newGuid20());
            abcsh.addChild(respseqno);

            Element fee = rootElt.element("fee");
            Element feeord = fee.element("feeord");
            for (Map<String, String> map : LIST) {
                String oldFeeord = map.get("feeord");
                System.out.println(oldFeeord);

                if (oldFeeord.equals(feeord.getText())) {
                    LIST.remove(map);
                }
            }
            return HEAD + abcsh;
        } else {
            throw new BizException("错误");
        }
    }

}
