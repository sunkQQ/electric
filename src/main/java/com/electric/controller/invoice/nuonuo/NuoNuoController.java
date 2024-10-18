package com.electric.controller.invoice.nuonuo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.electric.util.HttpServletRequestUtil;

/**
 * 诺诺发票结果返回
 * 
 * @Author Administrator
 * @Date 2021年6月2日
 *
 */
@RestController
@RequestMapping("/api/nuonuo/invoice")
public class NuoNuoController {

    @RequestMapping(value = "/callback")
    public String querySurplus(HttpServletRequest request) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
        String jsonStr = JSONObject.toJSONString(map);
        System.out.println(JSONObject.parseObject(jsonStr));
        //        System.out.println(JSONObject.toJSON(map));
        return map.toString();
    }
}
