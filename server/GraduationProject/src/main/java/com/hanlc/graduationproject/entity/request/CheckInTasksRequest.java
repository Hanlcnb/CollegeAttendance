package com.hanlc.graduationproject.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanlc.graduationproject.entity.enums.CheckInMethod;


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
public class CheckInTasksRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 签到标题
     */
    private String title;

    /**
     * 签到方式
     */
    private CheckInMethod method;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private String endTime;

    /**
     * 签到位置(经纬度)
     */
    private String location;

    /**
     * 允许签到距离(米)
     */
    @TableField(value = "allow_distance")
    private Integer allowDistance;


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


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public String getMethodCode() {
        return method != null ? method.getCode() : null;
    }

    public void setMethodCode(String methodCode) {
        this.method = CheckInMethod.getByCode(methodCode);
    }

    @Override
    public String toString() {
        return "CheckInTasks{" +
                ", title = " + title +
                ", method = " + method +
                ", startTime = " + startTime +
                ", endTime = " + endTime +
                ", location = " + location +
                ", allowDistance = " + allowDistance +
                "}";
    }
}
