package com.electric.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket
 *
 * @Author sunk
 * @Date 2021/12/10
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注入一个serverEndpointExporter，该Bean会自动注册使用@serverEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
