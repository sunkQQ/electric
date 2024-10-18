package com.electric.controller.scan;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 艾迪思特中控扫码
 *
 * @author sunk
 * @date 2023/08/15
 */
@RestController
@RequestMapping("/openapi")
public class IdsteScreenController {

    @RequestMapping(value = "/v1/device/ctrl-by-third-qrcode")
    public String qrCode() {
        //{"status":0,"message":"操作成功"}
        JSONObject json = new JSONObject();
        json.put("status", 0);
        json.put("message", "操作成功");
        return json.toString();
    }

    @RequestMapping(value = "/oauth/authorize")
    public String authorize() {
        return "token";
    }
}
