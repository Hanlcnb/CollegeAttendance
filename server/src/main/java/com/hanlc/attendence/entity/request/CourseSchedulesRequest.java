package com.hanlc.attendence.entity.request;

import lombok.Data;

@Data
public class CourseSchedulesRequest {

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 课程名称
     */
    private String name;

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
     * 开始时间
     */
    private Byte section;

    /**
     * 结束时间
     */
    private Byte sectionCount;

    /**
     * 上课地点
     */
    private String location;
    
}
