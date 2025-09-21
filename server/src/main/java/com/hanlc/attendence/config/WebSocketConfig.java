package com.hanlc.attendence.config;

import com.hanlc.attendence.netty.WebSocketServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {
    
    @Value("${websocket.port:8888}")
    private int port;

    private final ApplicationContext applicationContext;

    public WebSocketConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public WebSocketServer webSocketServer() {
        return new WebSocketServer(port,applicationContext);
    }
} 