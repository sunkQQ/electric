package com.electric.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;

import com.electric.exception.BizException;

/**
 * 建行悦生活sm4工具类
 * @author sunkang
 * 2025/9/18
 */
public class SM4ToolUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    public static final String  ALGORITHM_NAME             = "SM4";
    //密钥长度128位
    private static final int    DEFAULT_KEY_SIZE           = 128;
    private static final String ENCODING                   = "UTF-8";
    // 加密算法 分组加密模式 分组填充方式
    // PKCS5Padding 以8个字节为一组进行分组加密
    private static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

    private static final String ALGORITHM_NAME_GCM_PADDING = "SM4/GCM/NoPadding";

    public static String generateKey() throws Exception {
        return new String(Hex.encodeHex(generateKey(DEFAULT_KEY_SIZE), false));
    }

    private static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    // ECB模式（electronic code book）
    private static Cipher generateEcbCipher(String algorithm, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    private static Cipher generateGcmCipher(int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_GCM_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        //以各自的密钥作为初始化向量
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, key);
        cipher.init(mode, sm4Key, gcmParameterSpec);
        return cipher;
    }

    private static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    private static byte[] encrypt_Gcm_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateGcmCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt_Gcm_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateGcmCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    /**
     * sm4加密，加密模式：ECB
     * @param hexKey 16进制密钥
     * @param paramStr  待加密字符串
     * @return 加密后的字符串
     */
    public static String encryptEcb(String hexKey, String paramStr) {
        //16进制字符串
        try {
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            byte[] srcData = paramStr.getBytes(ENCODING);
            //加密后的数组
            byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
            String cipherText = ByteUtils.toHexString(cipherArray);
            return cipherText;
        } catch (Exception e) {
            throw new BizException("sm4加密异常", e);
        }
    }

    /**
     * sm4解密，解密模式：ECB
     * @param hexKey 16进制密钥
     * @param cipherText  16进制加密字符串
     * @return 解密后的字符串
     */
    public static String decryptEcb(String hexKey, String cipherText) {
        //16进制字符串
        try {
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            byte[] cipherData = ByteUtils.fromHexString(cipherText);
            //加密后的数组
            byte[] srcData = new byte[0];
            srcData = decrypt_Ecb_Padding(keyData, cipherData);
            // 用于接收解密后的字符串
            String decryptStr = new String(srcData, ENCODING);
            return decryptStr;
        } catch (Exception e) {
            throw new BizException("sm4解密异常", e);
        }
    }

    public static String encryptGcm(String hexKey, String paramStr) throws Exception {
        // 16进制字符串
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encrypt_Gcm_Padding(keyData, srcData);
        String cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    public static String decryptGcm(String hexKey, String cipherText) throws Exception {
        // 16进制字符串
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 加密后的数组
        byte[] srcData = new byte[0];
        srcData = decrypt_Gcm_Padding(keyData, cipherData);
        // 用于接收解密后的字符串
        String decryptStr = new String(srcData, ENCODING);
        return decryptStr;
    }

    //	public static void main(String[] args) throws Exception{

    //		if(null != (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME))){
    //			double version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).getVersion();
    //			System.out.println("运行环境BouncyCastleProvider: " + version);
    ////			Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
    ////			Security.addProvider(new BouncyCastleProvider());
    ////			version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).getVersion();
    ////			System.out.println("运行环境BouncyCastleProvider: " + version);
    //		}
    //		Security.addProvider(new BouncyCastleProvider());
    //		double version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).getVersion();
    //		System.out.println("运行环境BouncyCastleProvider: " + version);

    //密钥
    //		String keyValue = generateKey();
    //		System.out.println("密钥:"+keyValue);

    //		String key1 = "52D5CCAC81EE1D4593EA4E8A73ABCDEF";
    //		String key2 = "ED1A49ABFA4BCF92BED14DCC0BC69A01";
    //		long startTime1 = System.currentTimeMillis();
    //		//加密
    //		String enString = encryptEcb(key2,custName);
    //		System.out.println("密文："+enString);
    //		System.out.println("【encryptEcb完成耗时】" + (System.currentTimeMillis() - startTime1) + "毫秒");
    //		//解密
    //		long startTime2 = System.currentTimeMillis();
    //		String deString  = decryptEcb(key2,enString);
    //		System.out.println("解密:"+deString);
    //		System.out.println("【decryptEcb完成耗时】" + (System.currentTimeMillis() - startTime2) + "毫秒");

    //52D5CCAC81EE1D4593EA4E8A73BEBF41
    //ED1A49ABFA4BCF92BED14DCC0BC69A01
    //82ad715291f4461579bec9e7f6a1ceaf
    //		BigDecimal bigdecimalFailCount = new BigDecimal(1);
    //		BigDecimal bigdecimalTotalCount = new BigDecimal(3);
    //		BigDecimal ratio = bigdecimalFailCount.divide(bigdecimalTotalCount,MathContext.DECIMAL32);
    //		ratio = ratio.setScale(3, RoundingMode.FLOOR);
    //		System.out.println("商:"+ratio);
    //	}

}