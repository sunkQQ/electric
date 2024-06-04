package com.electric.controller.jiaofei.shuangqi;

import com.alibaba.fastjson.JSONObject;
import com.electric.constant.Numbers;
import com.electric.response.shuangqi.ShuangqiUserPersonInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.electric.param.shuangqi.ShuangqiPersonInfoParam;
import com.electric.response.shuangqi.ShuangqiBaseResponse;
import com.electric.util.DateUtil;
import com.electric.util.Md5Util;

/**
 * 双旗公寓接口
 *
 * @author sunk
 * @date 2023/08/04
 */
@RestController
@RequestMapping("/appdata/person/restapi")
public class ShuangqiPersonController {

    @RequestMapping(value = "/personInfo", method = RequestMethod.POST)
    public String personInfo(ShuangqiPersonInfoParam param) {
        assert StringUtils.isBlank(param.getKey());
        assert StringUtils.isBlank(param.getPersonsn());
        String sign = Md5Util.md5("SQ" + DateUtil.getDayNow() + "SELECT");
        ShuangqiBaseResponse baseResponse = new ShuangqiBaseResponse();
        // 校验签名
        if (!sign.equals(param.getKey())) {
            baseResponse.setCode("500");
            baseResponse.setMsg("签名不正确");
            return JSONObject.toJSONString(baseResponse);
        }
        ShuangqiUserPersonInfo userInfo = new ShuangqiUserPersonInfo();
        userInfo.setPersonId(param.getPersonsn());
        userInfo.setBuildingCode("1");
        userInfo.setRoomCode("101");
        userInfo.setDormitoryInfo("01号宿舍楼-1层-3-208");
        baseResponse.setCode(Numbers.STRING_0);

        baseResponse.setData(JSONObject.toJSONString(userInfo));

        return JSONObject.toJSONString(baseResponse);
    }
}
