package com.hanlc.attendence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanlc.attendence.config.WxConfig;
import com.hanlc.attendence.model.vo.WxLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WxLoginService {
    private final WxConfig wxConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";

    public WxLoginResponse login(String code) {
        try {
            String response = restTemplate.getForObject(
                    WX_LOGIN_URL,
                    String.class,
                    wxConfig.getAppid(),
                    wxConfig.getSecret(),
                    code
            );
            log.info("微信登录响应: {}", response);
            return objectMapper.readValue(response, WxLoginResponse.class);
        } catch (Exception e) {
            log.error("微信登录失败", e);
            throw new RuntimeException("微信登录失败: " + e.getMessage());
        }
    }
}