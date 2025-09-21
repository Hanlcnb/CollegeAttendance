package com.hanlc.attendence.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

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
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 星期几(1-7)
     */
    private Byte weekDay;

    /**
     * 开始周
     */
    private Byte startWeek;

    /**
     * 结束周
     */
    private Byte endWeek;


    /**
    * 课程安排在第几节课
    */
    private Byte section;

    /**
     * 课程持续时间
     */
    private Byte sectionCount;


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

    public Byte getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Byte weekDay) {
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

    public Byte getSection() {
        return section;
    }

    public void setSection(Byte section) {
        this.section = section;
    }

    public Byte getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(Byte sectionCount) {
        this.sectionCount = sectionCount;
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
                ", section = " + section +
                ", sectionCount = " + sectionCount +
                ", location = " + location +
                "}";
    }
}
