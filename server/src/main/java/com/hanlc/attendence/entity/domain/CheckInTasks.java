package com.hanlc.attendence.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanlc.attendence.entity.enums.CheckInMethod;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 签到任务表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@TableName("check_in_tasks")
public class CheckInTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 签到任务ID
     */
    private String id;

    /**
     * 课程ID
     */
    @TableField(value = "course_id")
    private String courseId;

    /**
     * 发布教师ID
     */
    @TableField(value = "teacher_id")
    private String teacherId;

    /**
     * 签到标题
     */
    private String title;

    /**
     * 签到方式
     */
    private CheckInMethod method;

    /**
     * 签到密码/手势(人脸识别为空)
     */
    private String secret;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**
     * 签到位置(经纬度)
     */
    private String location;

    /**
     * 允许签到距离(米)
     */
    @TableField(value = "allow_distance")
    private Integer allowDistance;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 状态
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CheckInMethod getMethod() {
        return method;
    }

    public void setMethod(CheckInMethod method) {
        this.method = method;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAllowDistance() {
        return allowDistance;
    }

    public void setAllowDistance(Integer allowDistance) {
        this.allowDistance = allowDistance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethodCode() {
        return method != null ? method.getCode() : null;
    }

    public void setMethodCode(String methodCode) {
        this.method = CheckInMethod.getByCode(methodCode);
    }

    @Override
    public String toString() {
        return "CheckInTasks{" +
            "id = " + id +
            ", courseId = " + courseId +
            ", teacherId = " + teacherId +
            ", title = " + title +
            ", method = " + method +
            ", secret = " + secret +
            ", startTime = " + startTime +
            ", endTime = " + endTime +
            ", location = " + location +
            ", allowDistance = " + allowDistance +
            ", createdAt = " + createdAt +
            ", status = " + status +
        "}";
    }
}
