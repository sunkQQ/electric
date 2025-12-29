package com.electric.controller.jiaofei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.param.jiaofei.JiaofeiUnifiedNoticeParam;
import com.electric.param.jiaofei.JiaofeiUnifiedQueryParam;
import com.electric.response.jiaofei.JiaofeiQueryDateResponse;
import com.electric.util.DateUtil;
import com.electric.util.HttpServletRequestUtil;
import com.electric.util.SignUtil;

/**
 * 缴费统一接口返回
 *
 * @Author sunk
 * @Date 2021/11/3
 */
@RestController
@RequestMapping("/api/jiaofei/unified")
public class JiaofeiUnifiedController {

    private static final Logger LOG = LoggerFactory.getLogger(JiaofeiUnifiedController.class);

    private static final String KEY = "123456";

    /**
     * 查询接口
     * @param request 请求
     * @param param 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(HttpServletRequest request, JiaofeiUnifiedQueryParam param) {

        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
        JSONObject jsonObject = new JSONObject();
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            jsonObject.put("statusCode", -1);
            jsonObject.put("message", "签名不正确");
            return jsonObject.toString();
        }
        List<JiaofeiQueryDateResponse> list = getJiaofeiQueryDateResponses(param);

        jsonObject.put("statusCode", 0);
        jsonObject.put("message", "操作成功");
        jsonObject.put("data", JSONArray.toJSONString(list));
        //jsonObject.put("data", list);
        return jsonObject.toJSONString();
    }

    /**
     * 缴费接口
     * @param request 请求
     * @param param 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public String notice(HttpServletRequest request, JiaofeiUnifiedNoticeParam param) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
        JSONObject jsonObject = new JSONObject();
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            jsonObject.put("statusCode", -1);
            jsonObject.put("message", "签名不正确");
            return jsonObject.toString();
        }
        jsonObject.put("statusCode", 0);
        jsonObject.put("message", "操作成功");
        return jsonObject.toJSONString();
    }

    /**
     * 返回查询数据
     * @param param
     * @return
     */
    private List<JiaofeiQueryDateResponse> getJiaofeiQueryDateResponses(JiaofeiUnifiedQueryParam param) {
        List<JiaofeiQueryDateResponse> list = new ArrayList<>();
        JiaofeiQueryDateResponse response1 = new JiaofeiQueryDateResponse();
        response1.setUserNo(param.getUserNo());
        response1.setUserName(param.getUserName());
        response1.setSeqNo("122138");
        response1.setProCode("101");
        response1.setProName("测试项目");
        response1.setThirdYear(DateUtil.getYear());
        response1.setDurationCode(DateUtil.getTimeNow2());
        response1.setDurationName("21年11月");
        response1.setPayAmount("1");
        list.add(response1);

        JiaofeiQueryDateResponse response2 = new JiaofeiQueryDateResponse();
        response2.setUserNo(param.getUserNo());
        response2.setUserName(param.getUserName());
        response2.setSeqNo("8625");
        response2.setProCode("101");
        response2.setProName("测试项目");
        response2.setThirdYear(DateUtil.getYear());
        response2.setDurationCode(DateUtil.getTimeNow2());
        response2.setDurationName("21年12月");
        response2.setPayAmount("2");
        list.add(response2);
        return list;
    }

}
