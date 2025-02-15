package com.electric.controller.special;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.electric.param.special.KayRoadQuZhiRefundApplyParam;
import com.electric.param.special.KayRoadQuZhiRefundStatusUpdateParam;
import com.electric.param.special.SpecialUserParam;
import com.electric.response.special.kayroad.KayRoadQuZhiBaseResponse;
import com.electric.response.special.kayroad.KayRoadQuZhiRefundApplyResponse;
import com.electric.response.special.kayroad.KayRoadQuZhiUserResponse;

/**
 * 生活缴费
 *
 * @author sunk
 * @date 2021/11/9
 */
@RestController
@RequestMapping("/external")
public class SpecialController {

    private static final Logger LOG = LoggerFactory.getLogger(SpecialController.class);

    /**
     * 查询用户信息接口
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String user(HttpServletRequest request, SpecialUserParam param) {
        printParameters(request);

        KayRoadQuZhiUserResponse response = new KayRoadQuZhiUserResponse();
        response.setProjectId("1");
        response.setProjectName("测试项目");
        response.setAccountId("20001681");
        response.setTelPhone(param.getTelPhone());
        response.setStatusId("0");
        response.setAccountMoney("10");
        response.setAccountGivenMoney("0");
        response.setIsCard("0");
        response.setCardStatusId("-1");
        response.setIsUseCode("0");

        KayRoadQuZhiBaseResponse base = new KayRoadQuZhiBaseResponse();
        base.setData(JSONObject.toJSONString(response));
        base.setErrorCode("0");
        base.setMessage("成功");
        return JSONObject.toJSONString(base);
    }

    /**
     * 充值接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public String recharge(HttpServletRequest request) {
        printParameters(request);

        KayRoadQuZhiBaseResponse base = new KayRoadQuZhiBaseResponse();
        base.setMessage("成功");
        base.setErrorCode("0");

        return JSONObject.toJSONString(base);
    }

    /**
     * 查询个人充值记录接口
     *
     * @return
     */
    @RequestMapping(value = "/recharge/user/list", method = RequestMethod.POST)
    public String userList(HttpServletRequest request) {
        printParameters(request);

        KayRoadQuZhiBaseResponse base = new KayRoadQuZhiBaseResponse();
        base.setMessage("成功");
        base.setErrorCode("0");

        return JSONObject.toJSONString(base);
    }

    @RequestMapping(value = "/refund/apply", method = RequestMethod.POST)
    public String refundApply(HttpServletRequest request, KayRoadQuZhiRefundApplyParam param) {
        KayRoadQuZhiBaseResponse base = new KayRoadQuZhiBaseResponse();
        base.setMessage("成功");
        base.setErrorCode("0");

        KayRoadQuZhiRefundApplyResponse refundApply = new KayRoadQuZhiRefundApplyResponse();
        refundApply.setTelPhone(param.getTelPhone());
        refundApply.setRefundMoney("1000");
        refundApply.setRefundApplyNo("2000000000");
        base.setData(JSONObject.toJSONString(refundApply));
        return JSONObject.toJSONString(base);
    }

    @RequestMapping(value = "/refund/apply/status/update", method = RequestMethod.POST)
    public String refundStatus(HttpServletRequest request, KayRoadQuZhiRefundStatusUpdateParam param) {
        KayRoadQuZhiBaseResponse base = new KayRoadQuZhiBaseResponse();
        base.setMessage("成功");
        base.setErrorCode("0");
        return JSONObject.toJSONString(base);
    }

    /**
     * 打印参数
     *
     * @param request
     */
    private void printParameters(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length > 0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        Set<Map.Entry<String, Object>> set = map.entrySet();
        LOG.info("==============================================================");
        for (Map.Entry<String, Object> entry : set) {
            LOG.info(entry.getKey() + ":" + entry.getValue());
        }
    }
}
