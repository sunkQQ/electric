package com.electric.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.util.DateUtil;
import com.electric.util.HttpClientUtils;
import com.electric.util.StringUtil;

/**
 * @author sunk
 * @date 2024/10/15
 */
public class CenterAppOrderTest {
    private static final String SAVE_ORDER = "https://open.lsmart.wang/routesapp/api/route/third/order/save";
    private static final String ymAppId    = "2409577171911376897";

    private static final String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfwnHG8kTpx2EZFtNZZYqZd7nw"
                                             + "2Nq9+sAZz6NwzScttTOG2XiulYbA07Qn017dpXjniRqUvC6OsMhYIcJ6GYGgKZNg"
                                             + "L0Zv25Fabzlge1FG2De0eVhmGb5XEy1s+9IY83z7mVyxWV8pn00n/TemRUnYZ1xg" + "j04WPzynWGmfpze9xQIDAQAB";

    public static void main(String[] args) throws Exception {
        savaOrder();
        //batchsave();

        /*List<Integer> numbers = Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1);
        
        int sum = numbers.stream().reduce(0, Integer::sum);
        
        System.out.println("Sum: " + sum);*/

        /*String result = HttpClientUtils.get(
            "https://www.douyin.com/user/MS4wLjABAAAAvLWTkvLHuRN6pvvcV7obHewhkFZphikhux6TDqfJ3EU?from_tab_name=main&modal_id=7426743026397039887");
        System.out.println(result);*/
    }

    private static void batchsave() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("schoolCode", "sunk");
        map.put("firm", "1");

        JSONArray array = new JSONArray();
        for (int i = 0; i <= 5; i++) {
            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("thirdUserId", "123456789122233121");
            jsonObject.put("proName", "批量新增测试" + StringUtil.generateMixNum(1));
            //jsonObject.put("userId", "12345123124123122");

            jsonObject.put("payNo", StringUtil.generateMixNum(19));
            String money = StringUtil.generateMixNum(1);
            jsonObject.put("payMoney", money);
            //map.put("payType", "ALIPAY_FACE");
            jsonObject.put("payType", "WXPAY");
            jsonObject.put("createTime", DateUtil.getTimeNow());
            jsonObject.put("payTime", DateUtil.getTimeNow());
            jsonObject.put("refundTime", DateUtil.getTimeNow());
            jsonObject.put("totalMoney", money);
            array.add(jsonObject);

            Thread.sleep(1000);
        }
        map.put("rows", array.toJSONString());
        //map.put("ymAppId", ymAppId);
        /*map.put("thirdUserId", "123456789122233121");
        map.put("proName", "测试商品" + StringUtil.generateMixNum(1));
        map.put("userId", "12345123124123122");
        
        map.put("payNo", StringUtil.generateMixNum(19));
        map.put("payMoney", "2");
        //map.put("payType", "ALIPAY_FACE");
        map.put("payType", "WXPAY");
        map.put("createTime", DateUtil.getTimeNow());
        map.put("payTime", DateUtil.getTimeNow());
        map.put("refundTime", DateUtil.getTimeNow());
        map.put("totalMoney", StringUtil.generateMixNum(1));*/

        //String sourceStr1 = RsaUtil3.encryptByPublicKey(sign, public_key);

        String signParams = md5(getSignParams(map));
        String sign = encryptByPublicKey(signParams, public_key);

        //String sign = SignUtil.getSignByMd5(map, "3db06b91a4dc4168814dba2c7c1c7349");
        System.out.println(sign);
        map.put("sign", sign);
        System.out.println(sign);
        String result = HttpClientUtils.post("http://127.0.0.1:8087/app/third/order/batchSave", map);
        System.out.println(result);
    }

    private static void savaOrder() throws Exception {
        //for (int i = 0; i < 20; i++) {
        Map<String, String> map = new HashMap<>();
        map.put("schoolCode", "yq025");
        map.put("ymAppId", ymAppId);
        map.put("thirdUserId", "12345678");
        map.put("proName", "测试商品" + StringUtil.generateMixNum(1));
        map.put("userId", "1111111");
        map.put("firm", "5");
        map.put("payNo", StringUtil.generateMixNum(19));

        String money = StringUtil.generateMixNum(1);
        map.put("payMoney", money);
        //map.put("payType", "ALIPAY_FACE");
        map.put("payType", "WXPAY");
        map.put("createTime", "2024-10-20 13:20");
        map.put("payTime", "2024-10-20 13:22");
        map.put("refundTime", DateUtil.getTimeNow());
        map.put("totalMoney", money);

        //String sourceStr1 = RsaUtil3.encryptByPublicKey(sign, public_key);

        String signParams = md5(getSignParams(map));
        String sign = encryptByPublicKey(signParams, public_key);

        //String sign = SignUtil.getSignByMd5(map, "3db06b91a4dc4168814dba2c7c1c7349");
        System.out.println(sign);
        map.put("sign", sign);
        System.out.println(sign);
        String result = HttpClientUtils.post(SAVE_ORDER, map);
        System.out.println(result);
        //}
    }

    public static final String  PARAM_SIGN        = "sign";
    public static final String  PARAM_STARTWITH   = "t_";
    public static final String  serialVersionUID  = "serialVersionUID";
    public static final String  PARAM_SPLIT       = "|";
    private static final String ALGORITHM         = "RSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int    MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int    MAX_DECRYPT_BLOCK = 512;

    /**
     * 获取需要签名的数据,未进行MD5
     *
     * @param map 参数集合
     * @return
     * @create  2020年2月8日 下午2:00:35 luochao
     * @history
     */
    public static String getSignParams(Map<String, String> map) {
        // 根据KEY排序
        Map<String, String> sortedParamMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //略过sign、serialVersionUID、以t_开头的参数
            if (entry.getKey().equalsIgnoreCase(PARAM_SIGN) || entry.getKey().toLowerCase().startsWith(PARAM_STARTWITH)
                || entry.getKey().equalsIgnoreCase(serialVersionUID)) {
                continue;
            }
            sortedParamMap.put(entry.getKey(), entry.getValue().toString());
        }
        // 最终结果
        List<String> allList = new LinkedList<String>();
        for (String value : sortedParamMap.values()) {
            if (StringUtils.isNotEmpty(value)) {
                allList.add(value);
            }
        }
        String a = StringUtils.join(allList.iterator(), PARAM_SPLIT);
        System.out.println(a);
        return a;
    }

    /**
     * 分段加密
     *
     * @param key 加密key
     * @param bytes 被加密字节数组
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @create  2020年1月15日 上午11:28:15 luochao
     * @history
     */
    private static String encrypt(Key key, byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                                                         IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLength = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] encryptedData;
        try {
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (inputLength - offset > 0) {
                if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offset, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offset, inputLength - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
        } finally {
            out.close();
        }

        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedData));
    }

    /**
     *对字符串进行md5,各个语言均有md5
     *
     * @param str
     * @return
     * @create  2020年2月18日 下午4:11:01 luochao
     * @history
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //原因在于汉字编码，在加密时设置一下编码UTF-8，问题解决。
            md.update(str.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /***
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     * @create  2019年10月8日 下午7:21:38 luochao
     * @history
     */
    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] decodeBase64Data = data.getBytes();
        byte[] decodeBase64Key = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodeBase64Key);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        return encrypt(key, decodeBase64Data);
    }
}
