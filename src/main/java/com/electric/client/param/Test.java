package com.electric.client.param;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.electric.exception.BizException;
import com.electric.util.Base64Util;
import com.electric.util.HttpClientUtils;
import com.electric.util.Md5Util;

/**
 * @author sunk
 * @date 2023/11/28
 */
public class Test {

    private static final String accNum               = "21";
    private static final String seqNo                = "E8A445FDAA23";
    private static final String signKey              = "684523174589651002354157";
    private static final String ivText               = "00000000";

    private static final String merchantKey          = "00000000";

    private static final String URL                  = "https://3q12c36168.oicp.vip/uwc_web_app";

    /** 获取挂账记录 */
    private final static String QUERY_CREDIT_URL     = "/withhold/query";
    /** 回调通知水控订单支付成功 */
    private final static String PAY_URL              = "/withhold/pay";
    /** 使用记录的订单列表 */
    private final static String TRANSACTION_LIST_URL = "/withhold/transaction/list";

    /** 开阀前信息 */
    private final static String OPEN_BEFORE_URL      = "/bluetoothApp/openBefore";

    /** 开阀校验 */
    private final static String OPEN_URL             = "/bluetoothApp/open";

    public static void main(String[] args) {
        transactionList();
    }

    private static void transactionList() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("accNum", accNum);
        paramMap.put("startTime", "2024-08-01");
        paramMap.put("endTime", "2024-08-30");
        paramMap.put("current", "1");
        paramMap.put("pageSize", "10");
        Map<String, String> signMap = new HashMap<>(paramMap);
        signMap.put("merchantKey", "hzsun.com.uwc的sign验签加密key");
        String sign = Base64Util.encodeString(Md5Util.md5(toGetStringSort(signMap)));
        paramMap.put("sign", sign);
        String str = JSONObject.toJSONString(paramMap);
        String resultParam = encode(str, signKey, ivText);
        Map<String, String> map = new HashMap<>();
        map.put("paramStr", resultParam);
        String result;
        try {
            System.out.println("[测试], 正元水控, 使用记录的订单列表接口, 账号: " + accNum + ", 请求参数：" + map);
            result = HttpClientUtils.post(URL + TRANSACTION_LIST_URL, map);
            System.out.println("[测试], 正元水控, 使用记录的订单列表接口, 账号: " + accNum + ", 返回结果：" + result);

            //result = DesUtil.decode(result, signKey, ivText);
            //System.out.println("[测试], 正元水控, 使用记录的订单列表接口, 账号: " + accNum + ", 返回结果解码后：" + result);
        } catch (Exception e) {
            System.out.println("[测试], 正元api水控, 使用记录的订单列表接口, 账号: " + accNum + ", 请求接口异常: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (StringUtils.isBlank(result)) {
            System.out.println("[测试], 正元水控, 使用记录的订单列表接口, 账号: " + accNum + ", 返回结果非json或返回结果为空, 返回结果：" + result);
            throw new BizException("接口返回异常");
        }
        JSONObject json = JSONObject.parseObject(result);
        if (!json.containsKey("resultMap")) {
            System.out.println("[测试], 正元水控, 使用记录的订单列表接口, 账号: " + accNum + ", 返回结果非json或返回结果为空, 返回结果：" + result);
            throw new BizException("接口返回异常");
        }
        String resultMap = json.getString("resultMap");
        result = decode(resultMap, signKey, ivText);

        System.out.println(result);
    }

    private static final String DES_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding"; //加密-解密算法 / 工作模式 / 填充方式

    public static String encode(String content, String key, String ivText) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivText.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new BizException("DES3加密失败:" + e);
        }
    }

    public static String decode(String content, String key, String ivText) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivText.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(Base64Util.decodeStringRetBytes(content));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {

            throw new BizException("DES3解密失败:" + e);
        }
    }

    public static String toGetStringSort(Map<String, String> map) {
        // 根据KEY排序
        Map<String, String> sortedParamMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sortedParamMap.put(entry.getKey(), entry.getValue());
        }
        // 最终结果
        List<String> allList = new LinkedList<String>();
        for (String key : sortedParamMap.keySet()) {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(key)) {
                allList.add(key + "=" + sortedParamMap.get(key));
            }
        }
        return org.apache.commons.lang.StringUtils.join(allList.iterator(), "&");
    }
}
