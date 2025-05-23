package com.hanlc.graduationproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用配置类
 */
@Configuration
public class AppConfig {
    
    @Value("${app.env}")
    private String env;
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version}")
    private String version;

    public String getEnv() {
        return env;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }
} 