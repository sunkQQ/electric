package com.electric.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2020-9-16
 *
 */
@Aspect
@Order
@Component
public class ControllerLogAspect {

	private Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

	@Pointcut("execution(public * com.electric.controller..*.*(..))")
	public void controllerLog() {
	}

	@Before("controllerLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 只记录post方法
		if ("POST".equals(request.getMethod())) {
			// 记录下请求内容
			logger.info("请求URL : " + request.getRequestURL());
			logger.info("请求IP : " + request.getRemoteAddr());
			logger.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "."
					+ joinPoint.getSignature().getName());

			// 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
			if (joinPoint.getArgs().length > 0) {
				for (Object o : joinPoint.getArgs()) {
					if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
						continue;
					}
					logger.info("请求参数 : " + JSON.toJSONString(o));
				}
			}
		}
	}

	@AfterReturning(returning = "ret", pointcut = "controllerLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		if (ret != null) {
			logger.info("返回 : " + JSONObject.toJSON(ret).toString());
		}
	}

}
