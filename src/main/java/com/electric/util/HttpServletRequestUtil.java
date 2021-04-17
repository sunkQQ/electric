package com.electric.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * request请求参数转map
 * 
 * @author: admin
 * @history:
 */
public class HttpServletRequestUtil {

	private static final String UTF8 = "utf-8";

	/**
	 * 从HttpServletRequest中获取所有请求参数
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestParmeter(HttpServletRequest request) {
		return getRequestParmeter(request, true);
	}

	/**
	 * 从HttpServletRequest中获取所有请求参数
	 *
	 * @param request
	 * @param skipEmpty 是否忽略为空的参数 true:忽略 false:不忽略
	 * @return
	 */
	public static Map<String, String> getRequestParmeter(HttpServletRequest request, boolean skipEmpty) {
		Map<String, String[]> requestParameter = request.getParameterMap();
		Map<String, String> map = new HashMap<>();
		try {
			for (String key : requestParameter.keySet()) {
				String[] array = requestParameter.get(key);
				String value = array != null && array.length > 0 ? array[0] : null;
				if (!skipEmpty) {
					if (StringUtils.isNotEmpty(value)) {
						map.put(key, value);
					}
				} else {
					String decodeValue = URLDecoder.decode(value, UTF8);
					/*
					 * if (decodeValue.indexOf("?") > -1) { decodeValue = URLDecoder.decode(new
					 * String(value.getBytes("ISO-8859-1"), UTF8), UTF8); }
					 */
					map.put(key, decodeValue);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return map;
	}
}
