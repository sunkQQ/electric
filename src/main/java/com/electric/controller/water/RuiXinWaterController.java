package com.electric.controller.water;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.model.param.water.ruixin.RuiXinWaterRecordParam;
import com.electric.model.param.water.ruixin.RuixinWaterAddCmdParam;
import com.electric.model.response.water.ruixin.RuiXinWaterDeviceInfoResponse;
import com.electric.model.response.water.ruixin.RuiXinWaterRecordResponse;
import com.electric.model.response.water.ruixin.RuixinWaterBaserResponse;
import com.electric.util.DateUtil;
import com.electric.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 瑞信水控
 *
 * @author sunk
 * @since 2026/1/29
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RuiXinWaterController {

    @GetMapping(value = "/deviceInfo.do")
    public String deviceInfo(String deviceNum) {
        RuixinWaterBaserResponse baserResponse = new RuixinWaterBaserResponse();
        baserResponse.setCode("200");
        baserResponse.setMessage("success");
        RuiXinWaterDeviceInfoResponse deviceInfo = new RuiXinWaterDeviceInfoResponse();
        deviceInfo.setDeviceNum(Long.parseLong(deviceNum));
        deviceInfo.setDeviceName("测试设备");

        baserResponse.setData(JSONObject.toJSONString(deviceInfo));
        return JSON.toJSONString(baserResponse);
    }

    @PostMapping(value = "/device/xb/control/water/addCmd.do")
    public String addCmd(String cmd) {
        RuixinWaterAddCmdParam addCmdParam = JSONObject.parseObject(cmd, RuixinWaterAddCmdParam.class);
        log.info("addCmdParam:{}", addCmdParam);
        RuixinWaterBaserResponse baserResponse = new RuixinWaterBaserResponse();
        baserResponse.setCode("200");
        baserResponse.setMessage("success");
        return JSON.toJSONString(baserResponse);
    }

    @GetMapping(value = "/xb/consume/crd/list.do")
    public String queryRecordList(String consumeCrdRq) {
        RuiXinWaterRecordParam recordParam = JSONObject.parseObject(consumeCrdRq, RuiXinWaterRecordParam.class);
        List<RuiXinWaterRecordResponse> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RuiXinWaterRecordResponse recordResponse = new RuiXinWaterRecordResponse();
            recordResponse.setId((long) i);
            recordResponse.setCompanyId(Long.valueOf(recordParam.getCompanyId()));
            recordResponse.setUserName("测试用户");
            recordResponse.setUserId(Long.valueOf(recordParam.getCompanyId()));
            recordResponse.setUserNo("test");
            recordResponse.setDeviceName("测试设备");
            recordResponse.setDeviceNum(1111L);
            recordResponse.setDeviceId(1111L);
            recordResponse.setOpFare(100L);
            recordResponse.setOpTime(DateUtil.getTimeNow());
            recordResponse.setCollectTime(DateUtil.getTimeNow());
            recordResponse.setOpCount((long) i);
            recordResponse.setWaterControl((long) i);
            recordResponse.setTimeTakeCount((long) i);
            recordResponse.setTradeNo(StringUtil.generateMixNum(4));
            recordResponse.setOutTradeNo(StringUtil.generateMixNum(6));
            recordResponse.setHandleFlag(0L);
            list.add(recordResponse);
        }
        return JSONArray.toJSONString(list);
    }
}
