package com.electric.controller.electric;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 亿码电控
 *
 * @author sunk
 * @date 2023/06/14
 */
@RestController
@RequestMapping("/ymcb/inter")
public class ElectricImasmoldControll {

    @RequestMapping(value = "/GetWarning", method = RequestMethod.POST)
    public String getWarning(HttpServletRequest request) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        JSONObject json = new JSONObject();
        json.put("Code", "1");
        json.put("Msg", "成功");

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RmId", "103");
        jsonObject.put("WarMoney", "5");
        jsonObject.put("SyMoney", "2.01");
        array.add(jsonObject);

        json.put("Date", array);
        return json.toString();
    }
}
