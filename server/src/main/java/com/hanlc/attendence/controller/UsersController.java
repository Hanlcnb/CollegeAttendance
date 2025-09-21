package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Users;
import com.hanlc.attendence.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/getUserByopenId")
    public Result getUserByopenId(@RequestParam("openId") String openId){
        if(openId == null || openId.isEmpty()){
            return Result.notFound();
        }
        return usersService.getUserInfoByOpenId(openId);
    }

    //根据userId查找用户信息
    @GetMapping(value="/getUserById")
    public Result getUserByName(@RequestParam("userId") String userId){
        if(userId == null || userId.isEmpty()){
            return Result.notFound();
        }
        return usersService.getUserInfoByUserId(userId);
    }

    //用户登录时验证用户名和密码是否匹配
    @GetMapping(value = "/loginVerity")
    public Result loginVerity(@RequestParam("username") String username,
                              @RequestParam("password") String password){
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            return Result.error("用户名或密码为空");
        }
        return usersService.loginVerity(username,password);
    }


    //新建用户
    @PostMapping(value = "/saveUser")
    public Result saveUser(@RequestBody Users user){
        if(user == null){
            return Result.error("用户信息不能为空");
        }
        return usersService.addUser(user);
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
    public Result deleteUser(@RequestParam("id") String userId) {
        if(userId == null || userId.isEmpty()) {
            return Result.error("openId不能为空");
        }
        return usersService.deleteUser(userId);
    }

    //根据用户的邮箱，找回密码
    @GetMapping(value="/getPassword")
    public Result getPassword(@RequestParam("email") String email){
        if(email == null || email.isEmpty()) {
            return Result.error("邮箱不能为空");
        }
        return usersService.getPassword(email);
    }
}
