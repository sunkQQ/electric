package com.electric.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author sunk
 * @date 2023/05/27
 */
public class RsaDemo {

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

        return new String(Base64.encodeBase64(encryptedData));
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
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
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

    public static void main(String[] args) throws Exception {
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJJg3nrXRJ7zCdanoJCcyQycK8CR04K2JXF9RZKDS3uF1jeNuuT6Fl75VVPUxXt/4QTgeBJdm9Y1khaFnOhTqAcCAwEAAQ==";

        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", "checkCard");
        map.put("incomeAccount", "YM201");
        map.put("incomeType", "2");
        map.put("schoolCode", "yq025");
        map.put("time", "20090202080403");
        map.put("realName", "云马201");
        map.put("ymAppId", "2009041114217022");
        String signParams = md5(getSignParams(map));
        System.out.println("signParams:" + signParams);
        String sign = encryptByPublicKey(signParams, publicKey);
        System.out.println("sign:" + sign);
        //        结果
        //        YM201|2|checkCard|云马201|yq025|20090202080403|2009041114217022
        //        signParams:9bbacdd2283a65ede5e843440f09b820
        //        sign:J83u4+YwURVeyNiR00//BsdQoTqCP+rQT4UOOZrYv+c3GXplFWwGzpZW/JnFF3FEORmdQ1ObD+coOtZDkdp/Uw==
        //-----------------------------------------------------------

        String identityPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDbicqzabDYMeXzTdHMaRqMAM6e"
                                   + "2hqfzJrF1AkNyNW7G0sAlkypppqqYf68FINedcN3W4GNbjxXi83rzeqO6HOwpp5a"
                                   + "JfoxXGR2FWvyLt2au+j6/HS85VJEkGxvAP003rUMuJZgD+4iZTUUqQDq939ZzIMJ" + "GSr2/3OBgiQERt9rkQIDAQAB";
        Map<String, String> identityMap = new HashMap<String, String>();
        identityMap.put("callBackUrl", "https://webapp.lsmart.wang/card/card_home.shtml");
        identityMap.put("jobNo", "YM201");
        identityMap.put("openid", "1111111111");
        identityMap.put("userName", "云马201");
        //开发者应用id,由云马开放平台提供,纯粹测试接口不用修改此参数, 实际开发中请修改为实际值
        identityMap.put("ymAppId", "2008311104517010");
        String identitySignParams = md5(getSignParams(identityMap));
        System.out.println("identitySignParams:" + identitySignParams);
        String identitySign = encryptByPublicKey(identitySignParams, identityPublicKey);
        System.out.println("identitySign:" + identitySign);
        //结果
        //https://webapp.lsmart.wang/card/card_home.shtml|YM201|1111111111|云马201|2008311104517010
        //identitySignParams:e4ee0b6a34e031430328d749609134cc
        //identitySign:cBfJnBh1OARkf5w6aMRp9o/SPan52LyrESJKoq+Cj7OfmJPZrwSg2r12c/a2nylTn3We7qC5oHUGsFWcTJRqp8iuhw4CeriqgKQm7ET4Otu2HbGU3wsZV38ARCJ7c8PqkRAKPBBmvHmmU/9b1QXYXFRRtpllemofCwxCwDNyRQ0=
    }

}
