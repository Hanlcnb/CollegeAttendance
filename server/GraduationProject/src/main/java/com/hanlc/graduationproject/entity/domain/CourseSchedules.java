package com.hanlc.graduationproject.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 * 课程安排表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@TableName("course_schedules")
public class CourseSchedules implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 安排ID
     */
    private String id;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 星期几(1-7)
     */
    private Boolean weekDay;

    /**
     * 开始周
     */
    private Byte startWeek;

    /**
     * 结束周
     */
    private Byte endWeek;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 上课地点
     */
    private String location;

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

    public Boolean getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Boolean weekDay) {
        this.weekDay = weekDay;
    }

    public Byte getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Byte startWeek) {
        this.startWeek = startWeek;
    }

    public Byte getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Byte endWeek) {
        this.endWeek = endWeek;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CourseSchedules{" +
            "id = " + id +
            ", courseId = " + courseId +
            ", weekDay = " + weekDay +
            ", startWeek = " + startWeek +
            ", endWeek = " + endWeek +
            ", startTime = " + startTime +
            ", endTime = " + endTime +
            ", location = " + location +
        "}";
    }
}
