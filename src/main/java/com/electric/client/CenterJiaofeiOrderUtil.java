package com.electric.client;

import java.util.Arrays;
import java.util.List;

/**
 * 缴费请求测试
 *
 * @author sunk
 * @date 2024/08/15
 */
public class CenterJiaofeiOrderUtil {

    //private static final String queryOrderPage = "https://open.lsmart.wang/routesapp/api/route/paycenter/queryOrderPage";
    private static final String queryOrderPage = "https://opengray.xiaofubao.com/routesapp/api/route/dn/paycenter/list/query";

    //uri=/routesapp/api/route/dn/paycenter/list/user,params=
    // {ymAppId=2308503402345807873, proId=2308629495116636174, userNo=371327200606115126,
    // sign=fd6af8c17d40c56c8d2da1515f9cbaf5, userName=卢伊,
    // schoolCode=2023080901}

    public static void main(String[] args) {
        /*Map<String, String> map = new HashMap<>();
        map.put("schoolCode", "2023080901");
        map.put("ymAppId", "2308503402345807873");
        map.put("userNo", "371327200606115126");
        map.put("proId", "2308629495116636174");
        map.put("userName", "卢伊");
        //map.put("currentPage", "");
        map.put("createTimeStart", "2024-01-01");
        map.put("createTimeEnd", "2024-08-19");
        //map.put("orderNo", "2308631040476446734");
        //map.put("proId", "2407709904074014734");
        //map.put("userName", "赵颜三");
        //map.put("userIdCard", "420101199409120583");
        //map.put("userNo", "16635353553");
        //map.put("payTimeStart", "2024-07-31");
        //map.put("payTimeEnd", "2024-07-31");
        //map.put("limit", "100");
        
        String sign = SignUtil.getSignByMd5(map, "caa003a1ed834d7ea45e551863b6ed7d");
        System.out.println(sign);
        map.put("sign", sign);
        System.out.println(sign);
        String result = HttpClientUtils.post(queryOrderPage, map);
        System.out.println(result);*/

        List<String> strList = Arrays.asList("s", "k");
        System.out.println(String.join("|", strList));

    }
}
