package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description 开启WebSocket支持
 * @date 2021/09/23 16:22
 **/
@Configuration
public class WebSocketConfig {


    /**
     * 扫描并注册带有@ServerEndpoint注解的所有服务端
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
