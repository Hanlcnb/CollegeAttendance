package com.hanlc.attendence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JwtProperties {

    @Value("${jwt.secret:CHANGE_ME_SECRET}")
    private String secret;

    @Value("${jwt.expireSeconds:2592000}") // 默认30天
    private long expireSeconds;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer }")
    private String prefix;
}


