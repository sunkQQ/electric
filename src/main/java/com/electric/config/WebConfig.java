package com.electric.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.electric.interceptor.MyInterceptor;

/**
 * @author sunk
 * @date 2024/08/13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**"); // 这里可以自定义拦截的路径
        //.excludePathPatterns("/login", "/error"); // 这里可以自定义排除的路径
    }
}
