package com.hanlc.graduationproject.entity.request;

import lombok.Data;

/**
 * 教师信息请求类
 */
@Data
public class TeacherInfoRequest {
    /**
     * 教师工号
     */
    private String teacherNumber;
    
    /**
     * 院系
     */
    private String department;
    
    /**
     * 职称
     */
    private String title;
    
    /**
     * 姓名
     */
    private String name;
} 