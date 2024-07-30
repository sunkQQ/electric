package com.electric.controller.electric;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.electric.param.send.CenterElectricRemindParam;
import com.electric.util.HttpClientUtils;
import com.electric.util.HttpServletRequestUtil;
import com.electric.util.SignUtil;

/**
 * 发送消息通知
 *
 * @author sunk
 * @date 2024/07/16
 */
@Controller
@RequestMapping("/electric/message")
public class ElectricSendMessageController {

    @RequestMapping(value = "/index")
    public ModelAndView sendMessage(HttpServletRequest request, CenterElectricRemindParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("electric_msg");
        return modelAndView;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public String test(HttpServletRequest request, CenterElectricRemindParam param, String key) {
        // 日志
        Map<String, String> paramMap = HttpServletRequestUtil.getRequestParmeter(request, true);
        paramMap.remove("key");
        /* 校验签名 */
        String signKey = SignUtil.getSignByMd5(paramMap, key);
        paramMap.put("sign", signKey);
        String url = "https://open.xiaofubao.com/routesc/api/route/ua/remind/send";
        String result = HttpClientUtils.post(url, paramMap);

        return result;
    }
}
