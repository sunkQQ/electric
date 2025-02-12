package com.electric.controller.electric;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.util.HttpServletRequestUtil;

/**
 * 亿码电控
 *
 * @author sunk
 * @date 2023/06/14
 */
@RestController
@RequestMapping("/ymcb/inter")
public class ElectricImasmoldController {

    @RequestMapping(value = "/GetWarning", method = RequestMethod.POST)
    public String getWarning(HttpServletRequest request) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        JSONObject json = new JSONObject();
        json.put("Code", "1");
        json.put("Msg", "成功");

        JSONArray array = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("RmId", "103");
        jsonObject1.put("WarMoney", "5");
        jsonObject1.put("SyMoney", "2.01");
        array.add(jsonObject1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("RmId", "001001001001001");
        jsonObject2.put("WarMoney", "5");
        jsonObject2.put("SyMoney", "5.20");
        array.add(jsonObject2);

        json.put("Date", array);
        return json.toString();
    }
}
