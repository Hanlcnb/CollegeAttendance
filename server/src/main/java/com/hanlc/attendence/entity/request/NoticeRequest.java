package com.hanlc.attendence.entity.request;

import java.time.LocalDateTime;

public class NoticeRequest {

    /**
     * courseId
     */
    private String courseId;

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

    @Override
    public String toString() {
        return "NoticeRequest{" +
                "courseId='" + courseId + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    /**
     * 时间
     */
    private LocalDateTime createdAt;
}
