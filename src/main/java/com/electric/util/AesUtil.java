package com.electric.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.electric.exception.BizException;

import lombok.extern.slf4j.Slf4j;

/**
 * Aes加密
 *
 * @author sunk
 * @date 2023/04/14
 */
@Slf4j
public class AesUtil {

    private static final String ALGORITHM            = "AES";

    private static final String TRANSFORMATION       = "AES/CBC/NoPadding";
    private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

    /**
     * AES CBC算法模式 pkcs7填充模式 加密 返回16进制（hex）密文
     * @param data 明文内容
     * @param key 密钥
     * @param iv 偏移
     * @return 16进制数据
     */
    public static String encryptCbc(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
            // 手动实现 PKCS7Padding 填充
            byte[] result = cipher.doFinal(pkcs7padding(data, cipher.getBlockSize()));
            // 转16进制
            return byteToHexString(result);
        } catch (Exception e) {
            log.error("aes加密失败", e);
            return null;
        }
    }

    /**
     * AES算法解密  AES/ECB/PKCS5Paddin
     *
     * @param data 数据
     * @param key key
     * @return 解密结果
     */
    public static String decryptEcb(String data, String key) {
        try {
            // 创建AES解密算法实例
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

            // 初始化为解密模式，并将密钥注入到算法中
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // 将Base64编码的密文解码  解密
            byte[] decByte = cipher.doFinal(Base64.getDecoder().decode(data));

            return new String(decByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("aes解密失败", e);
            return null;
        }
    }

    /**
     * AES算法加密  AES/ECB/PKCS5Padding
     *
     * @param data 明文数据
     * @param key 密钥
     * @return Base64编码的加密结果
     */
    public static String encryptEcb(String data, String key) {
        try {
            // 创建AES加密算法实例
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

            // 初始化为加密模式，并将密钥注入到算法中
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 加密数据
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // 将加密结果转换为Base64编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("aes加密失败", e);
            return null;
        }
    }

    /**
     * pkcs7填充
     * @param content
     * @param blockSize
     * @return
     */
    private static byte[] pkcs7padding(String content, int blockSize) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        // 计算需要补位的长度
        int pad = blockSize - (contentBytes.length % blockSize);
        // 补位字符 (即用补位长度)
        byte padChr = (byte) pad;
        // 在原有的长度上加上补位长度
        byte[] paddedBytes = new byte[contentBytes.length + pad];
        // 原有的先复制过去
        System.arraycopy(contentBytes, 0, paddedBytes, 0, contentBytes.length);
        // 补位字符填充
        for (int i = contentBytes.length; i < paddedBytes.length; i++) {
            paddedBytes[i] = padChr;
        }
        return paddedBytes;
    }

    /**
     * 转16 进制
     * @param bytes
     * @return
     */
    private static String byteToHexString(byte[] bytes) {
        StringBuilder resultHexString = new StringBuilder();
        String tempStr;
        for (byte b : bytes) {
            //这里需要对b与0xff做位与运算，
            //若b为负数，强制转换将高位位扩展，导致错误，
            //故需要高位清零
            tempStr = Integer.toHexString(b & 0xff);
            //若转换后的十六进制数字只有一位，
            //则在前补"0"
            if (tempStr.length() == 1) {
                resultHexString.append(0).append(tempStr);
            } else {
                resultHexString.append(tempStr);
            }
        }
        return resultHexString.toString();
    }

    /**
     * aes加密 CBC NoPadding
     * @param plainText 要加密的内容
     * @param key 加密key
     * @param iv 初始化向量
     * @return 加密后的数据base64编码
     * @throws Exception 异常
     */
    public static String encryptCbcNopadding(String plainText, String key, String iv) throws Exception {
        if (plainText == null || key == null || iv == null) {
            throw new BizException("aes加密参数异常");
        }

        if (key.length() != 16 || iv.length() != 16) {
            throw new BizException("密钥和初始化向量必须为16个字符");
        }
        byte[] sSrcArray = plainText.getBytes(StandardCharsets.UTF_8);
        int length = plainText.getBytes(StandardCharsets.UTF_8).length;
        byte[] lastArr;
        if (length % 16 != 0) {
            Byte[] temp = new Byte[16 - length % 16];
            //用空格补位
            byte[] fArr = " ".getBytes(StandardCharsets.UTF_8);
            Arrays.fill(temp, fArr[0]);
            lastArr = new byte[length + temp.length];
            System.arraycopy(sSrcArray, 0, lastArr, 0, length);
            for (int i = 0; i < temp.length; i++) {
                lastArr[length + i] = temp[i];
            }
        } else {
            lastArr = sSrcArray;
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedBytes = cipher.doFinal(lastArr);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES解密 CBC NoPadding
     * @param encryptedData 需要解码的base64字符串
     * @param key 加密key
     * @param iv 初始化向量
     * @return 解密后的字符串
     * @throws Exception 异常
     */
    public static String decryptCbcNopadding(String encryptedData, String key, String iv) throws Exception {
        if (encryptedData == null || key == null || iv == null) {
            throw new IllegalArgumentException("aes加密参数异常");
        }

        if (key.length() != 16 || iv.length() != 16) {
            throw new IllegalArgumentException("密钥和初始化向量必须为16个字符");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
