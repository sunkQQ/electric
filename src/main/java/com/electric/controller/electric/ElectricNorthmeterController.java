package com.electric.controller.electric;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.util.StringUtil;

/**
 * 北电接口
 *
 * @author sunk
 * @date 2026/4/3
 */
@RestController
public class ElectricNorthmeterController {

    @PostMapping(value = "/app/xcx/login")
    public String login(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("code", "0");
        json.put("msg", "成功");
        json.put("expire", 2700);
        json.put("token", StringUtil.generateString(32));
        return json.toString();
    }

    @GetMapping(value = "/app/xcx/meterinfo/getMeterStatus1")
    public String getMeterStatus1() {
        JSONObject json = new JSONObject();
        json.put("code", "0");
        json.put("msg", "成功");
        json.put("data", "1");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("comAddress", "1234567890");
        jsonObject.put("piplineName", "一号生产线");
        jsonObject.put("type", "电表");
        jsonObject.put("meterData", "1250.5");
        jsonObject.put("leftMoney", "368.50");
        jsonObject.put("preUseDay", "15");
        jsonObject.put("meterStatus", "合闸");
        jsonObject.put("updateDateTime", "2026-04-03 14:30:00");
        jsonObject.put("onlineStatus", "在线");
        jsonObject.put("isRecharge", "0");
        jsonObject.put("price", "0.58");
        jsonObject.put("remaining", "635.5");
        array.add(jsonObject);
        json.put("data", array);
        return json.toString();
    }

    @GetMapping(value = "/app/xcx/meterinfo/isrecharge")
    public String isRecharge() {
        JSONObject json = new JSONObject();
        json.put("code", "0");
        json.put("msg", "成功");
        json.put("isRecharge", "0");

        return json.toString();
    }

    @PostMapping(value = "/app/xcx/meterinfo/recharge1")
    public String recharge() {
        JSONObject json = new JSONObject();
        json.put("code", "0");
        json.put("msg", "成功");

        return json.toString();
    }

    @GetMapping(value = "/app/xcx/meterinfo/rechargeinfo")
    public String queryOrder() {
        JSONObject json = new JSONObject();
        json.put("code", "1");
        json.put("msg", "订单不存在");

        return json.toString();
    }
}
