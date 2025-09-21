package com.hanlc.attendence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Messages;
import com.hanlc.attendence.entity.request.MessagesRequest;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface MessagesService extends IService<Messages> {

    Result<List<MessagesRequest>> getMessagesByUserId(String userId);

    Result<List<MessagesRequest>> getMessagesByCourseId(String courseId);

    Result<List<MessagesRequest>> getMessages(String userId, String courseId);

    Result<List<MessagesRequest>> studentGetLastMessage(String studentId);

    Result<List<MessagesRequest>> teacherGetLastMessage(String studentId);

    Result<Integer> saveMessage(Messages messages);

    Result updateIsRead(String studentId, String courseId);
}
