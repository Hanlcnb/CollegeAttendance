package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.model.vo.WxLoginResponse;
import com.hanlc.attendence.service.UsersService;
import com.hanlc.attendence.service.WxLoginService;
import com.hanlc.attendence.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wechat")
@RequiredArgsConstructor
public class LoginController {

    private final WxLoginService wxLoginService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/login")
    public Result login(@RequestParam String code) {

        /**
         * 获取用户在微信小程序中的唯一标识openId
         **/
        WxLoginResponse wxLoginResponse = wxLoginService.login(code);
        Result userInfoResult = usersService.getUserInfoByOpenId(wxLoginResponse.getOpenid());
        if (userInfoResult.getCode() != 200 || userInfoResult.getData() == null) {
            return userInfoResult;
        }

        Object data = userInfoResult.getData();
        // 期望数据对象含有用户ID与角色等，使用反射/Map 方式提取
        String userId = null;
        String role = null;
        try {
            java.lang.reflect.Method getId = data.getClass().getMethod("getId");
            Object idVal = getId.invoke(data);
            if (idVal != null) userId = String.valueOf(idVal);
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Method getRole = data.getClass().getMethod("getRole");
            Object roleVal = getRole.invoke(data);
            if (roleVal != null) role = String.valueOf(roleVal);
        } catch (Exception ignored) {}

        Map<String, Object> claims = new HashMap<>();
        if (role != null) claims.put("role", role);
        if (wxLoginResponse.getOpenid() != null) claims.put("openId", wxLoginResponse.getOpenid());
        String token = jwtUtils.generateToken(userId != null ? userId : wxLoginResponse.getOpenid(), claims);

        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("user", data);
        return Result.success("登录成功", resp);
    }
}