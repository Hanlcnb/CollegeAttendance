package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Users;
import com.hanlc.graduationproject.service.UsersService;
import com.hanlc.graduationproject.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户基础表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:21:56
 */
@RestController
@RequestMapping("/api/wechat/users")
public class UsersController{

    @Autowired
    private UsersService usersService;

    //根据openID获取用户信息
    @GetMapping(value = "/userInfo")
    public Result getUserInfo(@RequestParam("openId") String openId){
        if(openId == null || openId.isEmpty()){
            return Result.notFound();
        }
        return usersService.getUserInfo(openId);
    }

    //新建用户
    @PostMapping(value = "/saveUser")
    public Result saveUser(@RequestBody Users users){
        //TODO 根据微信服务器发送的openId,创建一个新用户
        return Result.success("成功创建新用户");
    }

    //更新用户信息
    @PutMapping(value = "/updateUser")
    public Result updateUser(@RequestBody Users user) {
        if(user == null || user.getOpenId() == null || user.getOpenId().isEmpty()) {
            return Result.error("用户信息不完整");
        }
        return usersService.updateUser(user);
    }

    //删除用户
    @DeleteMapping(value = "/deleteUser")
    public Result deleteUser(@RequestParam("openId") String openId) {
        if(openId == null || openId.isEmpty()) {
            return Result.error("openId不能为空");
        }
        return usersService.deleteUser(openId);
    }
}
