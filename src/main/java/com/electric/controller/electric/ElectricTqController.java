package com.electric.controller.electric;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.electric.param.tuoqiang.ElectricTuoQiangParam;
import com.electric.util.HttpServletRequestUtil;
import com.electric.util.Md5Util;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2021年4月16日
 *
 */
@RestController
@RequestMapping("/api/tuoqiang")
public class ElectricTqController {

	/** 随机字符串 */
	private final static String NONCE = "fWw2Hfn3mwKVtUyhEPYgVjJIvxCwDo9H";

	@RequestMapping(value = "/surplus", method = RequestMethod.POST)
	public String querySurplus(HttpServletRequest request, ElectricTuoQiangParam param) {
//		ValidationUtil.validateEntityThrows(param);
		Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
//		System.out.println(JSONObject.toJSON(map));

		System.out.println(param);
		System.out.println(getSign(map));
		return map.toString();
	}

	// 生成签名字符串
	private static String getSign(Map<String, String> data) {
		// 获取关键字列表
		List<String> keys = new ArrayList<>(data.keySet());
		// 关键字列表排序
		keys.sort(Comparator.naturalOrder());
		StringBuilder sb = new StringBuilder();
		for (String key : keys) { // 取各个字段内容拼接字符串
			if (key.equals("sign") || key.equals("sunk")) {
				continue;
			}
			sb.append(data.get(key));
		}

		// 加上双方约定随机字符串
		String txt = sb.toString() + NONCE;
		// 计算哈希值
		return Md5Util.md5(txt);
	}
}
