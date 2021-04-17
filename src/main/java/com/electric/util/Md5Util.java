package com.electric.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

/**
 * md5工具类
 *
 * @author: luochao
 * @since: 2020年7月28日 上午9:18:20
 * @history:
 */
public class Md5Util {

	private static final String UTF8 = "UTF-8";
	private static final Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

	/**
	 * utf-8编码后再签名
	 *
	 * @param str
	 * @return
	 * @create 2020年7月28日 上午9:19:45 luochao
	 * @history
	 */
	public static String md5(String str) {
		return md5(str, UTF8);

	}

	/**
	 * 签名
	 *
	 * @param str     要签名的字符串
	 * @param charSet 编码格式
	 * @return
	 * @create 2020年7月28日 上午9:19:03 luochao
	 * @history
	 */
	public static String md5(String str, String charSet) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			return DigestUtils.md5DigestAsHex(str.getBytes(charSet));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("md5加密失败", e);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(md5("12345678901234567890123456789012345678901234567890123456789012345678901234567890"));
		System.out.println(
				md5("12345678901234567890123456789012345678901234567890123456789012345678901234567890", "utf-8"));
	}

}