package com.electric.controller.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.util.Base64Util;
import com.electric.util.DateUtil;
import com.electric.util.HttpClientUtils;
import com.electric.util.Md5Util;
import com.electric.util.SignUtil;

/**
 * @author sunk
 * @since 2026/1/12
 */
public class MyNetTest {
    // 一卡通查询订单
    public static void main(String[] args) {
        //			queryCardOrder();// 查询一卡通订单接口
        //			queryNetOrder();// 查询城市热点网费充值结果
        queryNetAccount();// 查询城市热点网费账号信息
        //queryNetAccountWL();// 查询城市热点网费账号信息万里
        //			queryElectric();// 查询电费房间
        //			cardCheck();// 一卡通检验接口
        //			querySerialno();// 查询一卡通明细接口
        //			cardcheck();
        //			qyCheck();
        //			packagesList();// 查询套餐列表
        //offline();// 万里强制离线

        /********* 慎用 ***********/
        //rechargeElectric();
        //			rechargeWLNet();// 万里网费充值
        //rechargeNet();
        /********* 慎用 ***********/

    }

    private static void offline() {
        String reqUrl = "http://61.153.150.19:9071/electric/cityHot.shtml";
        String signKey = "f64c495d0e53078162a5d6c862f12aae";
        String account = "2023013693";
        String reqContent = new StringBuilder("014").append(account).append("\t").append("6789").append("\t").append("xywx111").toString();
        JSONObject json = commonRequest(reqUrl, signKey, reqContent);
        System.out.println(json);
    }

    /**
     * 查询一卡通订单接口
     */
    private static void queryCardOrder() {
        String partner = "ydorder";
        String date = DateUtil.getTimeNow2();
        String tranNo = "20180827112906507946";
        String schoolCode = "180716";
        //			String url = "http://interface.xiaofubao.com/interfaceCard/rechargeCard.shtml";
        //			String key = "gd#@!4dGF#@s42$#@sja";
        //				String partner = "ydcheck";
        String url = "http://interface.lsmart.wang/interfaceCard/rechargeCard.shtml";
        String key = "ea@iead@dfaef@";
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", partner);
        map.put("time", date);
        map.put("tranNo", tranNo);
        map.put("schoolCode", schoolCode);
        map.put("sign", getSign(map, key));
        String response = null;
        try {
            System.out.println("校验一卡通账号请求参数：" + map.toString());

            response = HttpClientUtils.post(url, map);
            System.out.println("校验一卡通账号,接口返回：" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cardcheck() {
        String incomeAccount = "983";
        String realName = "盛丽樱";
        String schoolCode = "yq012";
        String incomeType = "2";
        String partner = "cardcheck";
        //			String url = "http://interface.xiaofubao.com/interfaceCard/rechargeCard.shtml";
        String url = "https://auth.xiaofubao.com/card/interface/operCard";
        String key = "dfsakl$#@#23$#sdgFD@#!";
        //			String partner = "ydcheck";
        //			String url = "http://interface.lsmart.wang/interfaceCard/rechargeCard.shtml";
        //			String key = "ea@iead@dfaef@";
        //			String key = "123456";
        String date = DateUtil.getTimeNow2();
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", partner);
        map.put("time", date);
        map.put("incomeAccount", incomeAccount);
        map.put("realName", realName);
        map.put("incomeType", incomeType);
        map.put("schoolCode", schoolCode);
        map.put("sign", getSign(map, key));
        String response = null;
        try {
            map.put("realName", java.net.URLEncoder.encode(realName, "utf-8"));
            map.put("incomeAccount", java.net.URLEncoder.encode(incomeAccount, "utf-8"));
            System.out.println("校验一卡通账号请求参数：" + map.toString());

            response = HttpClientUtils.post(url, map, "utf-8");
            System.out.println("校验一卡通账号,接口返回：" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询城市热点网费是否到账
     */
    private static void queryNetOrder() {
        String account = "B20171101332";
        String orderCode = "2018090ss6170731000";
        String reqUrl = "http://card.ccsu.cn:8082/electric/cityHot.shtml";
        String signKey = "09444B4942BEF329";
        String reqContent = new StringBuilder("S92").append(account).append("\t").append(orderCode).toString();
        JSONObject json = commonRequest(reqUrl, signKey, reqContent);
        System.out.println(json);
    }

    /**
     * 查询城市热点网费账号信息
     *
     * @param args
     */
    private static void queryNetAccount() {
        //String reqUrl = "http://card.ccsu.cn:8082/electric/cityHot.shtml";
        String reqUrl = "http://122.224.174.90:8082/electric/cityHot.shtml";
        //			String reqUrl = "http://localhost:8081/LIANLIAN_ELECTRIC_10355/cityHot.shtml";
        String signKey = "a97f6e2fedcabc887911dc9b5fd3ccc3";
        String account = "2241301142";
        String reqContent = "091" + account;
        JSONObject json = commonRequest(reqUrl, signKey, reqContent);
        System.out.println(json);

        System.out.println(json.getString("content").split("\t")[10]);
    }

    private static void packagesList() {
        String reqUrl = "http://61.153.150.19:9071/electric/cityHot.shtml";
        String signKey = "f64c495d0e53078162a5d6c862f12aae";
        String REQ_CODE_PACKGES_LIST = "093";//套餐列表
        JSONArray array = new JSONArray();
        String reqContent = new StringBuilder(REQ_CODE_PACKGES_LIST).toString();
        JSONObject respJson = commonRequest(reqUrl, signKey, reqContent);
        System.out.println(respJson);
        if (respJson != null && respJson.containsKey("code") && respJson.containsKey("content")) {
            String code = respJson.getString("code");
            String content = respJson.getString("content");
            if (StringUtils.equals("000000", code) && StringUtils.isNotBlank(content)) {
                String[] mark = content.split("\t");
                try {
                    JSONObject json = new JSONObject();
                    json.put("code", mark[0]);
                    json.put("packagesId", mark[1]);
                    json.put("PackagesName", mark[2]);
                    //						json.put("describe", mark[3]);
                    array.add(json);
                    for (int i = 1; i < mark.length - 3; i = i + 3) {
                        json.put("packagesId", mark[i + 3]);
                        json.put("PackagesName", mark[i + 4]);
                        array.add(json);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
            System.out.println(array);
        }
    }

    /**
     * 查询城市热点网费账号信息（万里）
     *
     * @param args
     */
    private static void queryNetAccountWL() {
        String reqUrl = "http://122.224.174.90:8082/electric/cityHot.shtml";
        String signKey = "a97f6e2fedcabc887911dc9b5fd3ccc3";
        String account = "2231351411";
        String reqContent = new StringBuilder("091").append(account).toString();
        JSONObject json = commonRequest(reqUrl, signKey, reqContent);
        System.out.println(json);
    }

    /**
     * 查询电费房间
     */
    private static void queryElectric() {
        String areaNo = "1";
        String buildingNo = "4";
        String roomNo = "3534";

        String reqUrl = "http://card.ccsu.cn:8082/electric/electric_detail.shtml";
        String signKey = "644c87da09444b4942bef3299de94372";

        Map<String, String> map = new HashMap<String, String>();
        map.put("roomNo", roomNo);
        map.put("areaNo", areaNo);
        map.put("buildingNo", buildingNo);
        String sign = SignUtil.getSignByMd5(map, signKey);
        map.put("sign", sign);
        String result = HttpClientUtils.post(reqUrl, map);
        System.out.println(result);
    }

    // 一卡通校验
    private static void cardCheck() {
        String incomeAccount = "20172102025";
        String realName = "ILUNGA STEPHANE LAVE";
        String schoolCode = "10430";
        String partner = "cardcheck";
        String incomeType = "2";
        String url = "https://auth.xiaofubao.com/card/interface/operCard";
        //			String url = "http://interface.xiaofubao.com/interfaceCard/rechargeCard.shtml";
        String key = "dfsakl$#@#23$#sdgFD@#!";
        //			String incomeAccount = "127";
        //			String realName = "汪宝刚";
        //			String partner = "ydcheck";
        //			String schoolCode = "180716";
        //			String url = "http://interface.lsmart.wang/interfaceCard/rechargeCard.shtml";
        //			String key = "ea@iead@dfaef@";
        String date = DateUtil.getTimeNow2();
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", partner);
        map.put("time", date);
        map.put("incomeAccount", incomeAccount);
        map.put("realName", realName);
        map.put("incomeType", incomeType);
        map.put("schoolCode", schoolCode);
        map.put("sign", getSign(map, key));
        String response = null;
        try {
            map.put("realName", java.net.URLEncoder.encode(realName, "utf-8"));
            map.put("incomeAccount", java.net.URLEncoder.encode(incomeAccount, "utf-8"));
            System.out.println("校验一卡通账号请求参数：" + map.toString());

            response = HttpClientUtils.post(url, map);
            System.out.println("校验一卡通账号,接口返回：" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询一卡通明细
     */
    private static void querySerialno() {
        String partner = "ydserialno";
        String schoolCode = "180716";
        String account = "127";
        String accountType = "2";
        String queryTime = "20180827";

        String postUrl = "http://192.168.100.5/interfaceCard/querySerialno.shtml";
        String md5Key = "ea@iead@dfaef@";

        String time = DateUtil.getTimeNow2();
        Map<String, String> params = new HashMap<String, String>();

        params.put("partner", partner);
        params.put("schoolCode", schoolCode);
        params.put("account", account);
        params.put("accountType", accountType);
        params.put("queryTime", queryTime);
        params.put("time", time);

        System.out.println("查询消费记录----请求参数----" + params.toString());
        String sign1 = SignUtil.getSignByMd5(params, md5Key);
        System.out.println("查询消费记录----第一次签名----" + sign1);
        StringBuilder sb = new StringBuilder(sign1).append("|").append(md5Key);
        String sign = Md5Util.md5(sb.toString());
        System.out.println("查询消费记录----第二次签名----" + sign);

        params.put("sign", sign);
        String result = HttpClientUtils.post(postUrl, params, "utf-8");
        System.out.println(result);
    }

    /**
     * 万里网费充值
     *
     * @return
     */
    private static JSONObject rechargeWLNet() {
        String reqUrl = "http://61.153.150.44:8089/electric/cityHot.shtml";
        String signKey = "f64c495d0e53078162a5d6c862f12aae";
        String account = "2017010893";
        String money = "50.00";
        String MCH_NO = "6789";
        String tranNo = "xywx165795926";
        String id = "1";
        StringBuilder reqContent = new StringBuilder("010");
        reqContent.append(account).append("\t");
        reqContent.append(money).append("\t");
        reqContent/* .append(id) */.append("\t");
        reqContent.append(MCH_NO).append("\t");
        reqContent.append(tranNo);
        JSONObject json = commonRequest(reqUrl, signKey, reqContent.toString());
        /* {"code":"000000","content":"E00"} */
        if (json != null && json.containsKey("code") && json.containsKey("content")) {
            String code = json.getString("code");
            String content = json.getString("content");
            System.out.println(("城市热点，充值返回编码含义：" + codeMsg(content)));
            JSONObject backJson = new JSONObject();
            if (StringUtils.equals("000000", code) && StringUtils.equals("E00", content)) {
                backJson.put("code", "000000");
            } else {
                backJson.put("code", content);

            }
            backJson.put("content", codeMsg(content));
            return backJson;
        }
        return null;
    }

    private static void rechargeNet() {
        String reqUrl = "http://122.224.174.90:8082/electric/cityHot.shtml";
        String signKey = "a97f6e2fedcabc887911dc9b5fd3ccc3";
        String account = "2231351411";
        String money = "40.00";
        String fee_fav_id = "0";
        String mchNo = "6789";
        String tranNo = "xywx176413704";
        StringBuilder reqContent = new StringBuilder("010");
        reqContent.append(account).append("\t");
        reqContent.append(money).append("\t");
        reqContent.append(fee_fav_id).append("\t");
        reqContent.append(mchNo).append("\t");
        reqContent.append(tranNo);
        JSONObject json = commonRequest(reqUrl, signKey, reqContent.toString());
        /* {"code":"000000","content":"E00"} */
        if (json != null && json.containsKey("code") && json.containsKey("content")) {
            String code = json.getString("code");
            String content = json.getString("content");
            System.out.println("城市热点，充值返回编码含义：" + codeMsg(content));
            JSONObject backJson = new JSONObject();
            if (StringUtils.equals("000000", code) && StringUtils.equals("E00", content)) {
                backJson.put("code", "000000");
            } else {
                backJson.put("code", content);

            }
            backJson.put("content", codeMsg(content));
            System.out.println(backJson);
        }

    }

    /**
     * 充值电费
     */
    private static void rechargeElectric() {
        String areaNo = "001";
        String buildingNo = "001003";
        String roomNo = "3236";
        String tranNo = "190908998144125125";
        String money = "60.00";
        String unifiedMethod = "";
        String orderCode = "";

        String reqUrl = "http://122.224.174.90:8082/electric/cityHot.shtml";
        String signKey = "a97f6e2fedcabc887911dc9b5fd3ccc3";
        Map<String, String> map = new HashMap<String, String>();
        map.put("roomNo", roomNo);
        if (StringUtils.isNotBlank(areaNo)) {
            map.put("areaNo", areaNo);
        }
        if (StringUtils.isNotBlank(buildingNo)) {
            map.put("buildingNo", buildingNo);
        }

        map.put("tranNo", tranNo);
        map.put("money", money);
        String sign = Md5Util.md5(SignUtil.getSignByMd5(map, signKey));
        map.put("sign", sign);
        map.put("payMethod", unifiedMethod);
        map.put("orderCode", orderCode);
        String result = HttpClientUtils.post(reqUrl, map);
        System.out.println(result);
    }

    private static void qyCheck() {
        String partner = "qycheck";
        String cardNo = "049223";
        String realName = "杨杰";
        String incomeType = "2";
        String schoolCode = "yq015";
        String key = "@sdfj#@!s$#Dfs#@1s@";
        String url = "http://interface.xiaofubao.com/interfaceCard/rechargeCard.shtml";

        String date = DateUtil.getTimeNow2();
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", partner);
        map.put("time", date);
        map.put("incomeAccount", cardNo);
        map.put("realName", realName);
        map.put("incomeType", incomeType);
        map.put("schoolCode", schoolCode);
        map.put("sign", getSign(map, key));

        String response = null;
        try {
            map.put("realName", java.net.URLEncoder.encode(realName, "utf-8"));
            map.put("incomeAccount", java.net.URLEncoder.encode(cardNo, "utf-8"));
            response = HttpClientUtils.post(url, map, "utf-8");
            System.out.println(response);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 签名
     *
     * @param map
     * @param md5_key
     * @return
     */
    private static String getSign(Map<String, String> map, String md5_key) {
        Map<String, String> sortedParamMap = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sortedParamMap.put(entry.getKey(), entry.getValue());
        }
        // 最终结果
        List<String> allList = new LinkedList<String>();
        for (String value : sortedParamMap.values()) {
            allList.add(value);
        }
        // 添加签名key
        allList.add(md5_key);
        String param = StringUtils.join(allList.iterator(), "|");

        return Md5Util.md5(Md5Util.md5(param, "utf-8") + "|" + md5_key, "utf-8");
    }

    /**
     * 城市热点接口请求，通用方法
     *
     * @param reqContent
     * @return
     */
    private static JSONObject commonRequest(String reqUrl, String signKey, String reqContent) {
        System.out.println("城市热点，请求参数：" + reqContent);
        JSONObject json = null;
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("time", DateUtil.getTimeNow2());
        paramsMap.put("reqContent", Base64Util.encodeString(reqContent));
        String sign = SignUtil.getSignMd5(paramsMap, signKey);
        paramsMap.put("sign", sign);
        System.out.println("城市热点，请求参数：" + paramsMap);
        String result = HttpClientUtils.post(reqUrl, paramsMap);
        System.out.println("城市热点，请求返回：" + result);
        if (StringUtils.isNotBlank(result)) {
            try {
                json = JSONObject.parseObject(result);
            } catch (Exception e) {
                return null;
            }
        }
        return json;
    }

    /**
     * code的含义
     *
     * @param code
     * @return
     */
    private static String codeMsg(String code) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("E00", "成功");
        map.put("E14", "账号不存在");
        map.put("E30", "套餐ID非法");
        map.put("E31", "套餐变更无效");
        map.put("E32", "套餐优惠ID非法");
        map.put("E33", "套餐优惠金额不符");
        map.put("E34", "存在预约套餐记录");
        map.put("E43", "金额非法");
        map.put("E44", "终端代码非法");
        map.put("E45", "流水号非法");
        map.put("E46", "日期格式非法");
        map.put("E81", "销账金额不符");
        map.put("E82", "账号在线");
        map.put("E84", "账号状态不符");
        map.put("E99", "未预期错误、取数据出错");
        return map.containsKey(code) ? map.get(code) : code;
    }

}
