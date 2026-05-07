package com.electric.controller.invoice.nuonuo;

import java.util.HashMap;

import com.electric.util.HttpClient5Util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2020-9-17
 *
 */
@Getter
@Setter
@ToString
public class TestDO {
    public static void main(String[] args) {

        String fullRequestUrl = "https://ykt.lumei.edu.cn:8091/api/ztwyPower/checkRecharge";
        //String fullRequestUrl = "https://sunkang.lsmart.wang/index/getParam";
        com.alibaba.fastjson2.JSONObject requestBody1 = new com.alibaba.fastjson2.JSONObject();
        requestBody1.put("tokenvalue", "8d58c14c-8612-47bb-9914-9751776b97db");
        requestBody1.put("strTrace", "2604191243309711371");
        String result1 = HttpClient5Util.sendPostJson(fullRequestUrl, requestBody1, new HashMap<>());
        System.out.println(result1);
    }
}
