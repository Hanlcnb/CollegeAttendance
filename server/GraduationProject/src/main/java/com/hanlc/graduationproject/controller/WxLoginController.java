package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.model.vo.WxLoginResponse;
import com.hanlc.graduationproject.service.UsersService;
import com.hanlc.graduationproject.service.WxLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wechat")
@RequiredArgsConstructor
public class WxLoginController {

    private final WxLoginService wxLoginService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public Result login(@RequestParam String code) {

        /**
         * 获取用户在微信小程序中的唯一标识openId
         **/
        WxLoginResponse wxLoginResponse = wxLoginService.login(code);
        return usersService.getUserInfo(wxLoginResponse.getOpenid());
    }
}