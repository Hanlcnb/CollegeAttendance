package com.hanlc.graduationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Messages;
import com.hanlc.graduationproject.entity.request.MessagesRequest;

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

    Result<List<MessagesRequest>> selectMessages(String userId, String courseId);

    Result<Integer> saveMessage(Messages messages);
}
