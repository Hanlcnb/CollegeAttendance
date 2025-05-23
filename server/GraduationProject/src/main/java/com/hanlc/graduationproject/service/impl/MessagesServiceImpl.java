package com.hanlc.graduationproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Messages;
import com.hanlc.graduationproject.entity.request.MessagesRequest;
import com.hanlc.graduationproject.mapper.MessagesMapper;
import com.hanlc.graduationproject.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {

    @Autowired
    private MessagesMapper messagesMapper;

    //根据userID查询数据库中的消息
    @Override
    public Result<List<MessagesRequest>> selectMessages(String userId , String courseId){
        if(userId.isEmpty() ){
            return Result.error("查询不到用户ID或课程ID");
        }
        List<MessagesRequest> messagesRequestList = messagesMapper.selectByUserIdAndCourseId(userId,courseId);
        return Result.success("成功获取用户" + userId + "在课程ID为"+courseId +"的消息",messagesRequestList);
    }

    @Override
    public Result<Integer> saveMessage(Messages messages){
        String senderId = messages.getSenderId();
        if(senderId.isEmpty()){
            return Result.error("发送者ID不能为空");
        }
        return Result.success("成功保存用户ID"+senderId+"发送的信息",messagesMapper.insert(messages));
    }

}
