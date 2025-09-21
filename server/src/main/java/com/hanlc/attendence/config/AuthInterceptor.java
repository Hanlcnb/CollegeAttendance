package com.hanlc.attendence.config;

import com.hanlc.attendence.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;

    public AuthInterceptor(JwtUtils jwtUtils, JwtProperties jwtProperties) {
        this.jwtUtils = jwtUtils;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String headerName = jwtProperties.getHeader();
        String prefix = jwtProperties.getPrefix();
        String value = request.getHeader(headerName);
        log.info("Header: {}",value);
        if (!StringUtils.hasText(value) && !value.startsWith(prefix)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = value.substring(prefix.length());
        try {
            Claims claims = jwtUtils.parse(token);
            request.setAttribute("jwtClaims", claims);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}


