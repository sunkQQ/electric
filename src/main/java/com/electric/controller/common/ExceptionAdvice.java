package com.electric.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson2.JSONObject;
import com.electric.exception.ValidationException;

/**
 * 异常处理切面
 * 
 * @Author Administrator
 * @Date 2020-9-16
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    //	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 校验异常
     *
     * @param e
     * @return
     * @history
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    public String handleValidationException(ValidationException e, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode", "203");
        jsonObject.put("message", e.getMessage());
        return jsonObject.toString();
    }
}
