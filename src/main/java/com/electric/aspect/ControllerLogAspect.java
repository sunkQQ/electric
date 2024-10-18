package com.electric.aspect;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求日志切面类
 * 
 * @author sunk
 * @date 2020-9-16
 *
 */
@Aspect
@Order
@Component
@Slf4j
public class ControllerLogAspect {

    /**
     * 定义了一个切入点表达式，用于匹配 com.electric.controller 包及其子包下所有公共方法的执行
     */
    @Pointcut("execution(public * com.electric.controller..*.*(..))")
    public void controllerLog() {
    }

    /**
     * 在方法执行之前进行日志记录
     *
     * @param joinPoint 切点对象
     * @throws Throwable 抛出异常
     */
    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 获取请求属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }

        // 如果请求不为空且请求方法为POST
        if (request != null && "POST".equals(request.getMethod())) {
            // 记录请求URL
            log.info("请求URL : " + request.getRequestURL());
            // 记录请求IP
            log.info("请求IP : " + request.getRemoteAddr());
            // 记录请求方法
            log.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            // 初始化请求参数拼接字符串
            StringBuilder wholeParams = new StringBuilder();
            // 如果请求体包含数据
            if (isBodyContent(request)) {
                // 记录请求体参数
                bodyParameterLog(request, wholeParams);
            }
            // 如果请求为表单数据
            if (isFormData(request)) {
                // 记录表单参数
                formParameterLog(request);
            }
            // 遍历方法参数
            for (Object o : joinPoint.getArgs()) {
                // 如果参数为HttpServletRequest或HttpServletResponse，则跳过
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    continue;
                }
                // 记录请求参数
                log.info("请求参数 : " + JSON.toJSONString(o));
            }
        }
    }

    /**
     * 记录表单参数日志
     *
     * @param request HttpServletRequest对象
     */
    private static void formParameterLog(HttpServletRequest request) {
        // 获取请求参数映射表
        Map<String, String[]> paramMap = request.getParameterMap();
        // 初始化一个StringBuilder对象，用于拼接请求参数的JSON字符串
        StringBuilder sb = new StringBuilder("{");
        // 遍历参数映射表
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            // 获取参数名
            String key = entry.getKey();
            // 获取参数值数组
            String[] values = entry.getValue();
            // 将参数名和参数值以JSON格式拼接到StringBuilder对象中
            sb.append("\"").append(key).append("\"").append(":").append("\"").append(values[0]).append("\"").append(",");
        }
        // 如果拼接的字符串最后一个字符是逗号，则删除该逗号
        if (sb.lastIndexOf(",") != -1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        // 在字符串末尾添加闭合的大括号
        sb.append("}");
        // 打印请求参数的日志信息
        log.info("请求参数 form-data : " + sb);
    }

    /**
     * 将请求体中的参数打印到日志中
     *
     * @param request HttpServletRequest对象
     * @param wholeParams 存储请求体内容的StringBuilder对象
     * @throws IOException 如果读取请求体时发生IO异常
     */
    private static void bodyParameterLog(HttpServletRequest request, StringBuilder wholeParams) throws IOException {
        // 定义一个字符串变量str
        String str;
        // 获取请求的输入流
        BufferedReader br;
        try {
            br = request.getReader();
        } catch (Exception e) {
            log.error("获取请求体输入流失败");
            return;
        }
        // 逐行读取输入流中的数据，直到读取完毕
        while ((str = br.readLine()) != null) {
            // 将读取到的数据追加到wholeParams中
            wholeParams.append(str);
        }
        // 将wholeParams转换为JSON对象
        JSONObject params = JSON.parseObject(wholeParams.toString());
        // 打印请求参数的body部分
        log.info("请求参数 body : " + params);
    }

    /**
     * 判断请求是否为表单数据
     *
     * @param request HttpServletRequest对象
     * @return 如果请求体为表单数据，则返回true；否则返回false
     */
    private boolean isFormData(HttpServletRequest request) {
        // 获取请求的Content-Type头部信息
        String contentType = request.getContentType();

        // 判断Content-Type是否为"application/x-www-form-urlencoded"开头
        // 如果是，则返回true，表示请求体为表单数据
        // 如果不是，则返回false，表示请求体不是表单数据
        return contentType != null && (contentType.startsWith("application/x-www-form-urlencoded") || contentType.startsWith("multipart/form-data"));
    }

    /**
     * 判断请求体是否包含数据
     *
     * @param request HttpServletRequest对象
     * @return 如果请求体的Content-Type为application/json，则返回true，否则返回false
     */
    private boolean isBodyContent(HttpServletRequest request) {
        // 获取请求的Content-Type头部信息
        String contentType = request.getContentType();

        // 判断Content-Type是否为"application/json"开头
        // 如果是，则返回true，表示请求体包含数据
        // 如果不是，则返回false，表示请求体不包含数据
        return contentType != null && contentType.startsWith("application/json");
    }

    /**
     * 在方法执行完成后，对返回结果进行日志记录
     *
     * @param ret 方法执行后的返回值
     * @throws Throwable 如果日志记录过程中发生异常，则抛出该异常
     */
    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 如果返回值不为空
        // 处理完请求，返回内容
        if (ret != null) {
            // 将返回值转换为JSON字符串，并打印日志
            log.info("返回 : " + JSONObject.toJSONString(ret));
        }
    }

}
