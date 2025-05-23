package com.hanlc.graduationproject.entity.request;

import lombok.Data;

@Data
public class CourseSchedulesRequest {

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
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 上课地点
     */
    private String location;
    
}
