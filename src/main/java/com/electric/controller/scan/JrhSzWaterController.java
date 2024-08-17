package com.electric.controller.scan;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author sunk
 * @date 2024/08/06
 */
@Controller
@RequestMapping("/jrh/water")
public class JrhSzWaterController {

    @RequestMapping(value = "/getParam")
    @ResponseBody
    public String getParamFromRequest(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder inputDate = new StringBuilder();
        inputDate.append("<jiJinBean>");
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            String value = null;
            if (values.length > 0) {
                value = values[0];
            }

            inputDate.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
        }
        inputDate.append("</jiJinBean>");
        System.out.println(inputDate);

        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "");
        json.put("type", "2");
        return json.toJSONString();
    }
}
