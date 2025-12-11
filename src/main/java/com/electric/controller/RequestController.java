package com.electric.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取参数
 * @author sunkang
 * 2025/11/20
 */
@RestController
public class RequestController {

    @PostMapping("/api/getParam")
    public Map<String, Object> collectAllParams(HttpServletRequest request, @RequestParam Map<String, String> formParams,
                                                @RequestBody(required = false) String rawBody) {

        Map<String, Object> allParams = new HashMap<>();

        // 1. 获取查询参数
        //allParams.put("queryParams", getQueryParams(request));

        // 2. 获取请求头
        //allParams.put("headers", getHeaders(request));

        // 3. 获取表单参数 (form-data, x-www-form-urlencoded)
        allParams.put("formParams", formParams);

        // 4. 获取原始请求体 (JSON, XML, RAW等)
        allParams.put("rawBody", rawBody);

        // 5. 获取路径参数
        allParams.put("pathParams", getPathParams(request));

        return allParams;
    }

    private Map<String, String> getQueryParams(HttpServletRequest request) {
        Map<String, String> queryParams = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            queryParams.put(paramName, paramValue);
        }

        return queryParams;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        return headers;
    }

    private Map<String, String> getPathParams(HttpServletRequest request) {
        // 路径参数通常在方法参数中直接获取
        // 这里返回空Map，实际使用时可以在方法参数中添加 @PathVariable
        return new HashMap<>();
    }
}