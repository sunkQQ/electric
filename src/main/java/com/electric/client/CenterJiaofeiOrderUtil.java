package com.electric.client;

import com.electric.util.HttpClientUtils;
import com.electric.util.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 缴费请求测试
 *
 * @author sunk
 * @date 2024/08/15
 */
public class CenterJiaofeiOrderUtil {

    private static final String queryOrderPage = "https://open.lsmart.wang/routesapp/api/route/paycenter/queryOrderPage";

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("schoolCode", "yq025");
        map.put("ymAppId", "2009141021518024");
        //map.put("orderNo", "2308631040476446734");
        //map.put("proId", "2407709904074014734");
        //map.put("userName", "赵颜三");
        //map.put("userIdCard", "420101199409120583");
        //map.put("userNo", "16635353553");
        //map.put("payTimeStart", "2024-07-31");
        //map.put("payTimeEnd", "2024-07-31");
        map.put("limit", "100");

        String sign = SignUtil.getSignByMd5(map, "f1adcf54c2b14c6bb0a0d40e19662f85");
        map.put("sign", sign);
        System.out.println(sign);
        String result = HttpClientUtils.post(queryOrderPage, map);
        System.out.println(result);
    }
}
