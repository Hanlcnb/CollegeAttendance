package com.hanlc.graduationproject.model.vo;

import lombok.Data;

@Data
public class WxLoginResponse {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;
} 