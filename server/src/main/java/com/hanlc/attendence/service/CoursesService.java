package com.hanlc.attendence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Courses;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface CoursesService extends IService<Courses> {

    /**
     * 根据学生ID查询课程列表
     *
     * @param studentId 学生ID
     * @return 课程列表
     */
    Result<List<Courses>> getCoursesByStudentId(String studentId);

    /**
     * 根据教师ID查询课程列表
     *
     * @param teacherId 教师ID
     * @return 课程列表
     */
    Result<List<Courses>> getCoursesByTeacherId(String teacherId);
} 