package com.hanlc.attendence.entity.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessagesRequest {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     *
     */
    private String senderName;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 未读消息的数量
     */
    private int unreadCount;

    /**
     * 时间
     */
    private LocalDateTime createdAt;
}
