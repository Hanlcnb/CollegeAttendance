package com.hanlc.attendence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Messages;
import com.hanlc.attendence.entity.request.MessagesRequest;
import com.hanlc.attendence.mapper.*;
import com.hanlc.attendence.service.CoursesService;
import com.hanlc.attendence.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private StudentCoursesMapper studentCoursesMapper;

    @Autowired
    private TeacherCoursesMapper teacherCoursesMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private CoursesMapper coursesMapper;

    //根据userId查询数据库中的消息
    @Override
    public Result<List<MessagesRequest>> getMessagesByUserId(String userId){
        if(userId.isEmpty() ){
            return Result.error("查询不到用户ID");
        }
        List<MessagesRequest> messagesRequestList = messagesMapper.selectByUserId(userId);
        return Result.success("成功获取用户" + userId + "的消息",messagesRequestList);
    }

    //根据CourseID查询消息
    @Override
    public Result<List<MessagesRequest>> getMessagesByCourseId(String courseId){
        if(courseId.isEmpty() ){
            return Result.error("查询不到课程ID");
        }
        List<MessagesRequest> messagesRequestList = messagesMapper.selectByCourseId(courseId);
        return Result.success("成功获取课程ID为"+courseId +"的消息",messagesRequestList);
    }

    //根据CourseID和userID查询消息
    @Override
    public Result<List<MessagesRequest>> getMessages(String userId , String courseId){
        if(userId.isEmpty() || courseId.isEmpty()){
            return Result.error("查询不到用户ID或课程ID");
        }
        List<MessagesRequest> messagesRequestList = messagesMapper.selectByUserIdAndCourseId(userId,courseId);
        return Result.success("成功获取用户" + userId + "在课程ID为"+courseId +"的消息",messagesRequestList);
    }

    @Override
    public Result<List<MessagesRequest>> studentGetLastMessage(String userId){
        if(userId.isEmpty() ){
            return Result.error("用户信息为空，无法查询");
        }
        List<String> courseIdList;
        List<MessagesRequest> lastMessageList = new ArrayList<>();
        courseIdList = studentCoursesMapper.selectByStudentId(userId);
        for(String courseId : courseIdList){
            MessagesRequest lastMessage = messagesMapper.selectLastMessageByCourseId(courseId);
            String senderId = lastMessage.getSenderId();
            String courseName = coursesMapper.getCourseNameById(courseId);
            lastMessage.setCourseName(courseName);
            lastMessage.setCourseId(courseId);
            lastMessage.setSenderName(usersMapper.selectUsernameById(senderId));
            lastMessage.setUnreadCount(messagesMapper.getUnreadCount(courseId,userId));
            lastMessageList.add(lastMessage);
        }


        return Result.success("成功获取最新消息",lastMessageList);
    }

    @Override
    public Result<List<MessagesRequest>> teacherGetLastMessage(String userId){
        if(userId.isEmpty() ){
            return Result.error("用户信息为空，无法查询");
        }
        List<String> courseIdList;
        List<MessagesRequest> lastMessageList = new ArrayList<>();
        courseIdList = teacherCoursesMapper.selectByTeacherId(userId);
        for(String courseId : courseIdList){
            MessagesRequest lastMessage = messagesMapper.selectLastMessageByCourseId(courseId);
            String senderId = lastMessage.getSenderId();
            lastMessage.setSenderName(usersMapper.selectUsernameById(senderId));
            lastMessageList.add(lastMessage);
        }
        return Result.success("成功获取最新消息",lastMessageList);
    }

    @Override
    public Result<Integer> saveMessage(Messages messages){
        String senderId = messages.getSenderId();
        if(senderId.isEmpty()){
            return Result.error("发送者ID不能为空");
        }
        return Result.success("成功保存用户ID"+senderId+"发送的信息",messagesMapper.insert(messages));
    }

    @Override
    public Result updateIsRead(String userId , String courseId){
        if(userId.isEmpty() || courseId.isEmpty()){
            return Result.error("用户ID或课程ID为空，无法更新消息状态");
        }
        if(messagesMapper.updateIsRead(userId ,courseId) > 0){
            return  Result.success("成功更新用户" + userId + "在课程为：" + courseId + "的消息为已读");
        }
        return Result.error("服务器错误，无法更新未读消息的状态");
    }
}
