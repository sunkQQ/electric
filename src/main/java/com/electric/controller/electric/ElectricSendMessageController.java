package com.electric.controller.electric;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.electric.model.param.send.CenterElectricRemindParam;
import com.electric.util.BeanConvertor;
import com.electric.util.HttpClient5Util;
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
        String result = HttpClient5Util.sendPost(url, paramMap);

        return result;
    }

    public static void main(String[] args) {
        String key = "jvpJoqnnOAiHApdWBpXb";
        String ymAppId = "2007281049435003";
        //String ymAppId = "2002081421372003";
        //String pushId = "2512128741946687489"; // 测试
        String pushId = "2512128534224076801";

        String areaCode = "1";
        String buildingCode = "01";
        String floorCode = "0101";
        String roomCode = "010105";
        CenterElectricRemindParam param = new CenterElectricRemindParam();
        param.setYmAppId(ymAppId);
        param.setPushId(pushId);
        param.setAreaCode(areaCode);
        param.setBuildingCode(buildingCode);
        param.setFloorCode(floorCode);
        param.setRoomCode(roomCode);
        param.setType(1);
        param.setSurplus("36.45");
        param.setAmount("20.78");
        // 日志
        Map<String, String> paramMap = BeanConvertor.objectToMap(param);
        paramMap.remove("key");
        /* 校验签名 */
        //paramMap.put("routeToken", "yunma8ac899ee712b47b5a79e25aa4b953049");
        String signKey = SignUtil.getSignByMd5(paramMap, key);
        paramMap.put("sign", signKey);
        String url = "https://open.xiaofubao.com/routesc/api/route/ua/remind/send";
        //String url = "https://open.lsmart.wang/routesc/api/route/ua/remind/send";
        //String url = "http://localhost:8087/api/route/ua/remind/send";
        String result = HttpClient5Util.sendPost(url, paramMap);
        System.out.println(result);
    }
}
