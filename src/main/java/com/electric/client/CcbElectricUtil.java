package com.electric.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.electric.constant.Numbers;
import com.electric.util.HttpClientUtils;
import com.electric.util.Md5Util;

import lombok.extern.slf4j.Slf4j;

/**
 * 缴费请求测试
 *
 * @author sunk
 * @date 2024/08/15
 */
@Slf4j
public class CcbElectricUtil {

    private static final String SIGNTYPE       = "SIGNTYPE";
    private static final String SIGN           = "SIGN";

    private static final String QUERY_AREA_URL = "https://open.lsmart.wang/routesc/api/route/ua/ccb/electric/queryArea";

    private static final String SIGN_KEY       = "Fil5L78xOEYZ3KETkQ1w8RYpeS+/MThG";

    private static final String SCHOOL_ID      = "33020001";

    private static final String YM_APP_ID      = "2008051032578004";

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("school_id", SCHOOL_ID);
        map.put("ymAppId", YM_APP_ID);
        //map.put("routeUri", "/routesc/api/route/ua/ccb/electric/queryArea");

        String sign = md5(map, SIGN_KEY);
        map.put("SIGN", sign);
        System.out.println("请求参数：" + map);
        String result = HttpClientUtils.post(QUERY_AREA_URL, map);
        System.out.println(result);
    }

    /**
     * md5加密
     *
     * @param map
     * @param key
     * @return
     */
    private static String md5(Map<String, String> map, String key) {
        TreeMap<String, String> treemap = new TreeMap<String, String>(map);

        try {
            treemap.remove(SIGNTYPE);
            treemap.remove(SIGN);
            StringBuffer params = getParamer(treemap);
            if (key.length() > Numbers.INT_30) {
                key = key.substring(key.length() - Numbers.INT_30);
            }
            params.append("&PUB=").append(key);
            return Md5Util.md5(params.toString()).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            log.error("建行电费接口签名异常");
        }

        return "";
    }

    /**
     * 加密参数处理
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static StringBuffer getParamer(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                if (e.getValue() != null && !e.getValue().isEmpty()) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(URLEncoder.encode(e.getValue(), "utf-8"));
                } else {
                    continue;
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

}
