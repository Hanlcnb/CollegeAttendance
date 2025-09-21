package com.hanlc.attendence.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.StudentCourses;

/**
 * <p>
 * 学生选课表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface StudentCoursesService extends IService<StudentCourses> {

    List<String> getCoursesID(String studentId);

    Result getCourseStudents(String courseId);
}
