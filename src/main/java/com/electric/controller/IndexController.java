package com.electric.controller;

import com.electric.param.ElectricQuerySurplueParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Administrator
 * @date 2021年2月24日
 */
@Controller
@RequestMapping("/index")
//@RestController
public class IndexController {

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest request, ElectricQuerySurplueParam param) {
        System.out.println("teste");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("key", 12345);
        // System.out.println("test");
        return modelAndView;
        // return "index.html";
    }

    // 获取所有参数和参数值
    @RequestMapping(value = "/getParam")
    @ResponseBody
    public String getParamFromRequest(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder inputDate = new StringBuilder();
        inputDate.append("<jiJinBean>");
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            String value = null;
            if (values.length > 0) {
                value = values[0];
            }

            inputDate.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
        }
        inputDate.append("</jiJinBean>");
        System.out.println(inputDate);
        return "success";
    }
}
