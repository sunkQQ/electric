package com.electric.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base64工具类
 * 
 * @Author Administrator
 * @Date 2021年10月18日
 *
 */

public class Base64Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Util.class);
    private static final String UTF_8  = "UTF-8";
    private static final String ERROR  = "Base64操作失败";

    /**
     * 对给定的字符串进行base64加密操作
     *
     * @param inputData
     * @return 
     * @history
     */
    public static String encodeString(String inputData) {
        if (null == inputData) {
            return null;
        }
        try {
            byte[] bytes = inputData.getBytes(UTF_8);
            return encodeBytes(bytes);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ERROR, e);
            throw new RuntimeException(ERROR);
        }
    }

    /**
     * 对给定的字节数组进行base64加密操作
     *
     * @param inputData
     * @return 
     * @history
     */
    public static String encodeBytes(byte[] inputData) {
        if (null == inputData) {
            return null;
        }
        try {
            return new String(Base64.encodeBase64(inputData), UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ERROR, e);
            throw new RuntimeException(ERROR);
        }
    }

    /**
     * 对给定的字符串进行base64解码操作，返回String
     *
     * @param inputData
     * @return 
     * @history
     */
    public static String decodeString(String inputData) {
        if (null == inputData) {
            return null;
        }
        try {
            return new String(decodeStringRetBytes(inputData), UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(inputData, e);
            throw new RuntimeException(ERROR);
        }
    }

    /**
     * 对给定的字符串进行base64解码操作，返回Bytes
     *
     * @param inputData
     * @return 
     * @history
     */
    public static byte[] decodeStringRetBytes(String inputData) {
        if (null == inputData) {
            return null;
        }
        try {
            return Base64.decodeBase64(inputData.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(inputData, e);
            throw new RuntimeException(ERROR);
        }
    }

    public static void main(String[] args) {
        System.out.println("对字符串加密:" + Base64Util.encodeString("abcd"));
        System.out.println("对字节加密:" + Base64Util.encodeBytes("abcd".getBytes()));

        System.out.println("对字符串解密:" + Base64Util.decodeString("YWJjZA=="));
        //System.out.println("对字符串解密返回字节:" + Base64Util.decodeStringRetBytes("YWJjZA==").toString());
    }

}
