package com.hanlc.graduationproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)  // 允许发送cookie
                .allowedOriginPatterns("http://localhost:8080")  // 明确指定允许的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")  // 允许所有请求头
                .exposedHeaders("Authorization")  // 允许暴露的响应头
                .maxAge(3600);  // 预检请求的有效期，单位秒
    }
}
