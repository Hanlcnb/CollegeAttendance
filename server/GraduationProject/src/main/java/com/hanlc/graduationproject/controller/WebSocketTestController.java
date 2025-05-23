package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.netty.WebSocketFrameHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wechat/ws")
public class WebSocketTestController {

    /**
     * 发送消息给指定用户
     */
    @PostMapping("/send")
    public Result sendMessage(@RequestParam String userId, @RequestParam String message) {
        boolean success = WebSocketFrameHandler.sendToUser(userId, message);
        if (success) {
            return Result.success("消息发送成功");
        } else {
            return Result.error("消息发送失败，用户可能不在线");
        }
    }

    /**
     * 广播消息给所有在线用户
     */
    @PostMapping("/broadcast")
    public Result broadcastMessage(@RequestParam String message) {
        WebSocketFrameHandler.broadcast(message);
        return Result.success("广播消息发送成功");
    }

    /**
     * 获取在线用户数
     */
    @GetMapping("/online/count")
    public Result getOnlineCount() {
        int count = WebSocketFrameHandler.getOnlineUserCount();
        return Result.success("获取在线用户数成功", count);
    }

    /**
     * 获取所有在线用户
     */
    @GetMapping("/online/users")
    public Result getOnlineUsers() {
        List<String> users = WebSocketFrameHandler.getOnlineUsers();
        return Result.success("获取在线用户列表成功", users);
    }
} 