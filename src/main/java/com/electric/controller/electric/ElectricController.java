package com.electric.controller.electric;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.electric.constant.Numbers;
import com.electric.param.*;
import com.electric.response.CommResponse;
import com.electric.response.PageResult;
import com.electric.response.electric.AppElectricUsageRecordVO;
import com.electric.response.electric.AppRoomBuyRecordVO;
import com.electric.util.*;
import com.electric.vo.ElectricRechargeVO;
import com.electric.vo.ElectricRoomVO;

/**
 * 电费通用测试接口
 * 
 * @author Administrator
 * @date 2020-9-10
 *
 */
@RestController
@RequestMapping("/api/electric")
public class ElectricController {

    private static final Logger                   LOGGER        = LoggerFactory.getLogger(ElectricController.class);

    private static final List<ElectricRechargeVO> RECHARGE_LIST = new ArrayList<>();

    private static final String                   KEY           = "123456";

    /**
     * 查询剩余量
     * 
     * @param param 参数
     * @return 返回
     */
    @RequestMapping(value = "/querySurplus", method = RequestMethod.POST)
    public CommResponse<?> querySurplus(HttpServletRequest request, ElectricQuerySurplueParam param) {
        ValidationUtil.validateEntityThrows(param);
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        String sign = SignUtil.getSignByMd5(map, KEY);

        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }

        /* 检验房间是否存在 */
        if (!checkRoom(param.getRoomCode())) {
            return CommResponse.failure("房间信息不存在");
        }

        JSONObject json = new JSONObject();
        json.put("surplus", "5.6");
        json.put("recordTime", "2020-09-09 09:58:29");
        json.put("fieldName", "欠费金额");
        json.put("fieldValue", "0.0");

        return CommResponse.success(json);
    }

    /**
     * 充值
     * @param request request
     * @param param 参数
     * @return 结果
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public CommResponse<?> recharge(HttpServletRequest request, ElectricRechargeParam param) {
        ValidationUtil.validateEntityThrows(param);
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        /* 检验签名 */
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }

        /* 检验房间数据 */
        if (!checkRoom(param.getRoomCode())) {
            return CommResponse.failure("房间信息不存在");
        }
        List<ElectricRechargeVO> rechargeVOList = RECHARGE_LIST.stream().filter(rechargeVO -> param.getOrderCode().equals(rechargeVO.getOrderCode()))
            .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(rechargeVOList)) {
            JSONObject json = new JSONObject();
            ElectricRechargeVO rechargeVO = rechargeVOList.get(Numbers.INT_0);
            LOGGER.info("请求{}接口，订单已经存在， 订单号{}", "recharge", rechargeVO.getOrderCode());
            json.put("rechargeTime", rechargeVO.getRechargeTime());
            json.put("bussiOrder", rechargeVO.getBussiOrder());
            return CommResponse.success(json);
        }

        JSONObject json = new JSONObject();
        ElectricRechargeVO rechargeVO = BeanConvertor.copy(param, ElectricRechargeVO.class);
        String rechargeTime = DateUtil.getTimeNow();
        String bussiOrder = getRandom(20);
        rechargeVO.setRechargeTime(rechargeTime);
        rechargeVO.setBussiOrder(bussiOrder);
        RECHARGE_LIST.add(rechargeVO);
        json.put("rechargeTime", rechargeTime);
        json.put("bussiOrder", bussiOrder);
        return CommResponse.success(json);
    }

    /**
     * 查询充值结果
     * @param request request
     * @param param 参数
     * @return 结果
     */
    @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
    public CommResponse<?> queryOrder(HttpServletRequest request, ElectricQueryOrderParam param) {
        ValidationUtil.validateEntityThrows(param);
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        /* 检验签名 */
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }

        List<ElectricRechargeVO> rechargeVOList = RECHARGE_LIST.stream().filter(rechargeVO -> param.getOrderCode().equals(rechargeVO.getOrderCode()))
            .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(rechargeVOList)) {
            JSONObject json = new JSONObject();
            ElectricRechargeVO rechargeVO = rechargeVOList.get(Numbers.INT_0);
            LOGGER.info("请求{}接口，订单已经存在， 订单号{}", "queryOrder", rechargeVO.getOrderCode());
            json.put("rechargeStatus", "2");
            json.put("rechargeTime", rechargeVO.getRechargeTime());
            json.put("bussiOrder", rechargeVO.getBussiOrder());
            json.put("orderCode", rechargeVO.getOrderCode());
            json.put("roomCode", rechargeVO.getRoomCode());
            json.put("rechargeMoney", rechargeVO.getRechargeMoney());
            return CommResponse.success(json);
        }
        return CommResponse.success();
    }

    /**
     * 查询所有房间数据
     * 
     * @param request request
     * @param param 参数
     * @return 结果
     */
    @RequestMapping(value = "/queryRoomList", method = RequestMethod.POST)
    public CommResponse<?> queryRoomList(HttpServletRequest request, ElectricQueryRoomListParam param) {
        ValidationUtil.validateEntityThrows(param);
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
        /* 检验签名 */
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }

        List<ElectricRoomVO> roomList = roomList();
        return PageResult.handle(roomList, roomList.size());
    }

    @RequestMapping(value = "/queryUsageRecord", method = RequestMethod.POST)
    public CommResponse<?> queryUsageRecord(HttpServletRequest request, ElectricQueryRecordParam param) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        /* 检验签名 */
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }
        List<AppElectricUsageRecordVO> usageList = new ArrayList<>();
        int pageNo = param.getPageNo();
        int pageSize = param.getPageSize();

        int size = Math.min(pageSize * pageNo + 1, 101);
        for (int i = (pageNo - 1) * pageSize + 1; i < size; i++) {
            String dateTime = DateUtil.getNextDayString(new Date(), -i, DateUtil.DAY_FORMAT);
            AppElectricUsageRecordVO usageRecord = new AppElectricUsageRecordVO(dateTime, i + "度");
            usageList.add(usageRecord);
        }

        return PageResult.handle(usageList, 100);
    }

    @RequestMapping(value = "/queryRoomRecord", method = RequestMethod.POST)
    public CommResponse<?> queryRoomRecord(HttpServletRequest request, ElectricQueryRecordParam param) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        /* 检验签名 */
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            return CommResponse.failure("签名不正确");
        }
        List<AppRoomBuyRecordVO> usageList = new ArrayList<>();
        int pageNo = param.getPageNo();
        int pageSize = param.getPageSize();

        int size = Math.min(pageSize * pageNo + 1, 101);
        for (int i = (pageNo - 1) * pageSize + 1; i < size; i++) {
            String dateTime = DateUtil.getNextDayString(new Date(), -i, DateUtil.DAY_FORMAT) + " "
                              + DateUtil.getTimeNowFormat(DateUtil.HOUR_MINUTE_SECOND_FORMAT);
            if (i % 2 == 0) {
                AppRoomBuyRecordVO usageRecord = new AppRoomBuyRecordVO(dateTime, "电费充值", i + "元");
                usageList.add(usageRecord);
            } else if (i % 3 == 0) {
                AppRoomBuyRecordVO usageRecord = new AppRoomBuyRecordVO(dateTime, "赠送金额", i + "元");
                usageList.add(usageRecord);
            } else {
                AppRoomBuyRecordVO usageRecord = new AppRoomBuyRecordVO(dateTime, i + "元");
                usageList.add(usageRecord);
            }
        }

        return PageResult.handle(usageList, 100);
    }

    /**
     * 检验房间是否存在
     * 
     * @param roomCode 房间编码
     * @return 是否存在
     */
    private boolean checkRoom(String roomCode) {
        List<ElectricRoomVO> roomList = roomList();
        return roomList.stream().anyMatch(room -> roomCode.equals(room.getRoomCode()));
    }

    /**
     * 所有房间数据
     * 
     * @return 房间列表
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
        roomVO2.setRoomName("102房间");
        roomList.add(roomVO2);
        return roomList;
    }

    /**
     * 生成指定位数的随机数
     * 
     * @param length 长度
     * @return 随机数
     */
    public static String getRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }
}
