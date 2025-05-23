package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Messages;
import com.hanlc.graduationproject.entity.request.MessagesRequest;
import com.hanlc.graduationproject.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping(value = "/getMessage")
    public Result<List<MessagesRequest>> getMessage(@RequestParam String userId ,
                                                    @RequestParam String courseId){
        return messagesService.selectMessages(userId,courseId);
    }

    @GetMapping(value = "/getNotice")
    public Result<List<MessagesRequest>> getNotice(@RequestParam String userId,
                                                    @RequestParam String courseId){
        return messagesService.selectMessages(userId,courseId);
    }
}
