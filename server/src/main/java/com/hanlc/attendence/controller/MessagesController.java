package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.request.MessagesRequest;
import com.hanlc.attendence.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //用户读取自己所有的消息列表
    @GetMapping(value = "/getMessage")
    public Result<List<MessagesRequest>> getMessage(@RequestParam String userId ,
                                                    @RequestParam String courseId){
        return messagesService.getMessages(userId,courseId);
    }

    //用户读取
    @GetMapping(value = "/getMessageByUserId")
    public Result<List<MessagesRequest>> getMessageByUserId(@RequestParam String userId ){
        return messagesService.getMessagesByUserId(userId);
    }

    //查询某一个课程的所有消息
    @GetMapping(value = "/getMessageByCourseId")
    public Result<List<MessagesRequest>> getMessageByCourseId(@RequestParam String courseId){
        return messagesService.getMessagesByCourseId(courseId);
    }

    //查询列表courseID中每个课程的最新消息
    @GetMapping(value = "/studentGetNotice")
    public Result<List<MessagesRequest>> studentGetNotice(@RequestParam String userId){
        return messagesService.studentGetLastMessage(userId);
    }

    //查询列表courseID中每个课程的最新消息
    @GetMapping(value = "/teacherGetNotice")
    public Result<List<MessagesRequest>> teacherGetNotice(@RequestParam String userId){
        return messagesService.teacherGetLastMessage(userId);
    }

    //用户查看某个课程的消息后需要更新数据库中用户在该课程的消息的未读状态
    @PutMapping("/updateIsRead")
    public Result updateIsRead(@RequestParam String userId ,@RequestParam String courseId){
        return messagesService.updateIsRead(userId , courseId);
    }
}
