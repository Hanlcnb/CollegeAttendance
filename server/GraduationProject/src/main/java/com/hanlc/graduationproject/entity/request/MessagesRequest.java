package com.hanlc.graduationproject.entity.request;

import lombok.Data;

@Data
public class MessagesRequest {

    /**
     * 发送者ID
     */
    private String senderId;

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
     * 是否已读(1:是,0:否)
     */
    private Boolean isRead;

    /**
     * 时间
     */
    private String time;
}
