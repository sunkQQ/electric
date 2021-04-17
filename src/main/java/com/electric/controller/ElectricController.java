package com.electric.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.constant.Numbers;
import com.electric.param.ElectricQueryOrderParam;
import com.electric.param.ElectricQueryRoomListParam;
import com.electric.param.ElectricQuerySurplueParam;
import com.electric.param.ElectricRechargeParam;
import com.electric.util.BeanConvertor;
import com.electric.util.DateUtil;
import com.electric.util.HttpServletRequestUtil;
import com.electric.util.SignUtil;
import com.electric.util.ValidationUtil;
import com.electric.vo.ElectricRechargeVO;
import com.electric.vo.ElectricRoomVO;

/**
 * 电费通用测试接口
 * 
 * @Author Administrator
 * @Date 2020-9-10
 *
 */
@RestController
@RequestMapping("/api/electric")
public class ElectricController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElectricController.class);

	private static List<ElectricRechargeVO> rechargeList = new ArrayList<>();

	private static final String KEY = "123456";

	/**
	 * 查询剩余量
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/querySurplus", method = RequestMethod.POST)
	public String querySurplus(HttpServletRequest request, ElectricQuerySurplueParam param) {
		ValidationUtil.validateEntityThrows(param);
		Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

		JSONObject jsonObject = new JSONObject();

		String sign = SignUtil.getSignByMd5(map, KEY);

        /*if (!sign.equalsIgnoreCase(param.getSign())) {
            jsonObject.put("statusCode", -1);
            jsonObject.put("message", "签名不正确");
            return returnStr(jsonObject, "querySurplus");
        }*/

		/* 检验房间是否存在 */
		if (!checkRoom(param.getRoomCode())) {
			jsonObject.put("statusCode", -1);
			jsonObject.put("message", "房间信息不存在");
			return returnStr(jsonObject, "querySurplus");
		}

		jsonObject.put("statusCode", 0);
		jsonObject.put("message", "操作成功");
		JSONObject json = new JSONObject();
		json.put("surplus", "5.6");
		json.put("recordTime", "2020-09-09 09:58:29");
		json.put("fieldName", "欠费金额");
		json.put("fieldValue", "0.0");
		json.put("surplus", "10");
		jsonObject.put("data", json);
		return returnStr(jsonObject, "querySurplus");
	}

	/**
	 * 充值
	 * 
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public String recharge(HttpServletRequest request, ElectricRechargeParam param) {
		ValidationUtil.validateEntityThrows(param);
		Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
		JSONObject jsonObject = new JSONObject();

		/* 检验签名 */
		/*
		 * String sign = SignUtil.getSignByMd5(map, KEY); if
		 * (!sign.equalsIgnoreCase(param.getSign())) { jsonObject.put("statusCode", -1);
		 * jsonObject.put("message", "签名不正确"); return returnStr(jsonObject,
		 * "querySurplus"); }
		 */

		/* 检验房间数据 */
		if (!checkRoom(param.getRoomCode())) {
			jsonObject.put("statusCode", -1);
			jsonObject.put("message", "房间信息不存在");
			return returnStr(jsonObject, "recharge");
		}
		if (rechargeList != null) {
			List<ElectricRechargeVO> rechargeVOList = rechargeList.stream()
					.filter(rechargeVO -> param.getOrderCode().equals(rechargeVO.getOrderCode()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(rechargeVOList)) {
				jsonObject.put("statusCode", 0);
				jsonObject.put("message", "操作成功");
				JSONObject json = new JSONObject();
				ElectricRechargeVO rechargeVO = rechargeVOList.get(Numbers.INT_0);
				LOGGER.info("请求{}接口，订单已经存在， 订单号{}", "recharge", rechargeVO.getOrderCode());
				json.put("rechargeTime", rechargeVO.getRechargeTime());
				json.put("bussiOrder", rechargeVO.getBussiOrder());
				jsonObject.put("data", json);
				return returnStr(jsonObject, "recharge");
			}
		}

		jsonObject.put("statusCode", 0);
		jsonObject.put("message", "操作成功");
		JSONObject json = new JSONObject();
		ElectricRechargeVO rechargeVO = BeanConvertor.copy(param, ElectricRechargeVO.class);
		String rechargeTime = DateUtil.getTimeNow();
		String bussiOrder = getRandom(20);
		rechargeVO.setRechargeTime(rechargeTime);
		rechargeVO.setBussiOrder(bussiOrder);
		rechargeList.add(rechargeVO);
		json.put("rechargeTime", rechargeTime);
		json.put("bussiOrder", bussiOrder);
		jsonObject.put("data", json);

		return returnStr(jsonObject, "recharge");
	}

	/**
	 * 充值结果查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
	public String queryOrder(HttpServletRequest request, ElectricQueryOrderParam param) {
		ValidationUtil.validateEntityThrows(param);
		Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
		JSONObject jsonObject = new JSONObject();

		/* 检验签名 */
		/*
		 * String sign = SignUtil.getSignByMd5(map, KEY); if
		 * (!sign.equalsIgnoreCase(param.getSign())) { jsonObject.put("statusCode", -1);
		 * jsonObject.put("message", "签名不正确"); return returnStr(jsonObject,
		 * "querySurplus"); }
		 */

		if (rechargeList != null) {
			List<ElectricRechargeVO> rechargeVOList = rechargeList.stream()
					.filter(rechargeVO -> param.getOrderCode().equals(rechargeVO.getOrderCode()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(rechargeVOList)) {
				jsonObject.put("statusCode", 0);
				jsonObject.put("message", "操作成功");
				JSONObject json = new JSONObject();
				ElectricRechargeVO rechargeVO = rechargeVOList.get(Numbers.INT_0);
				LOGGER.info("请求{}接口，订单已经存在， 订单号{}", "queryOrder", rechargeVO.getOrderCode());
				json.put("rechargeStatus", "2");
				json.put("rechargeTime", rechargeVO.getRechargeTime());
				json.put("bussiOrder", rechargeVO.getBussiOrder());
				json.put("orderCode", rechargeVO.getOrderCode());
				json.put("roomCode", rechargeVO.getRoomCode());
				json.put("rechargeMoney", rechargeVO.getRechargeMoney());
				jsonObject.put("data", json);
				return returnStr(jsonObject, "queryOrder");
			}
		}
		jsonObject.put("statusCode", "0");
		jsonObject.put("message", "操作成功");
		return returnStr(jsonObject, "queryOrder");
	}

	/**
	 * 查询所有房间数据
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryRoomList", method = RequestMethod.POST)
	public String queryRoomList(HttpServletRequest request, ElectricQueryRoomListParam param) {
		ValidationUtil.validateEntityThrows(param);
		Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
		/* 检验签名 */
		/*
		 * String sign = SignUtil.getSignByMd5(map, KEY); if
		 * (!sign.equalsIgnoreCase(param.getSign())) { jsonObject.put("statusCode", -1);
		 * jsonObject.put("message", "签名不正确"); return returnStr(jsonObject,
		 * "querySurplus"); }
		 */

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("statusCode", 0);
		jsonObject.put("total", 2);
		List<ElectricRoomVO> roomList = roomList();
		JSONArray array = new JSONArray();
		for (ElectricRoomVO electricRoomVO : roomList) {
			array.add(JSONObject.toJSON(electricRoomVO));
		}
		jsonObject.put("rows", array);
        jsonObject.put("message", "操作成功");
		return returnStr(jsonObject, "queryRoomList");
	}

	/**
	 * 检验房间是否存在
	 * 
	 * @param roomCode
	 * @return
	 */
	private boolean checkRoom(String roomCode) {
		List<ElectricRoomVO> roomList = roomList();
		boolean flag = roomList.stream().anyMatch(room -> roomCode.equals(room.getRoomCode()));
		return flag;
	}

	/**
	 * 所有房间数据
	 * 
	 * @return
	 */
	private List<ElectricRoomVO> roomList() {
		List<ElectricRoomVO> roomList = new ArrayList<>();
		ElectricRoomVO roomVO1 = new ElectricRoomVO();
		roomVO1.setAreaCode("1");
		roomVO1.setAreaName("测试校区");
		roomVO1.setBuildingCode("01");
		roomVO1.setBuildingName("1号楼");
		roomVO1.setFloorCode("1001");
		roomVO1.setFloorName("1层");
		roomVO1.setRoomCode("101");
		roomVO1.setRoomName("101房间");
		roomList.add(roomVO1);
		ElectricRoomVO roomVO2 = new ElectricRoomVO();
		roomVO2.setAreaCode("1");
		roomVO2.setAreaName("测试校区");
		roomVO2.setBuildingCode("01");
		roomVO2.setBuildingName("1号楼");
		roomVO2.setFloorCode("1001");
		roomVO2.setFloorName("1层");
		roomVO2.setRoomCode("101");
		roomVO2.setRoomName("101房间");
		roomList.add(roomVO2);
		return roomList;
	}

	/**
	 * 生成指定位数的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}

	// 生成20位随机数
	public static String newGuid20() {
		String str1 = "";
		String[] strArr36 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		Calendar cal = Calendar.getInstance();
		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);
		yy = (yy % 100) % 36;
		int hh = 100 + cal.get(Calendar.HOUR);
		;
		int mins = 100 + cal.get(Calendar.MINUTE);
		int sec = 100 + cal.get(Calendar.SECOND);
		String hhstr = ("" + hh).substring(1);
		String minsStr = ("" + mins).substring(1);
		String secStr = ("" + sec).substring(1);

		str1 = strArr36[yy] + strArr36[mm] + strArr36[dd] + hhstr + minsStr + secStr;
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < 11; i++) {
			int k = (int) (Math.random() * 35);
			sb.append(strArr36[k]);
		}
		str1 = str1 + sb.toString();
		return str1;
	}

	/**
	 * 所有请求参数日志
	 * 
	 * @param request
	 * @return
	 */
	private void printLog(Map<String, String> map, String methodName) {
//		LOGGER.info("请求{}接口，请求参数:{}", methodName, JSONObject.toJSONString(map));
	}

	/**
	 * 返回日志
	 * 
	 * @param jsonObject
	 * @param methodName
	 * @return
	 */
	private String returnStr(JSONObject jsonObject, String methodName) {
//		LOGGER.info("请求{}接口，返回结果:{}", methodName, jsonObject.toString());
		return jsonObject.toString();
	}
}
