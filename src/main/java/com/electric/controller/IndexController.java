package com.electric.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.electric.param.ElectricQuerySurplueParam;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2021年2月24日
 *
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
}
