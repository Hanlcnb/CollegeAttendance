package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.request.CourseSchedulesRequest;
import com.hanlc.graduationproject.service.CourseSchedulesService;
import com.hanlc.graduationproject.service.StudentCoursesService;
import com.hanlc.graduationproject.service.impl.CourseSchedulesServiceImpl;
import com.hanlc.graduationproject.service.impl.StudentCoursesServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生选课表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/studentCourses")
public class StudentCoursesController {

    @Autowired
    private StudentCoursesService studentCoursesService;

    @Autowired
    private CourseSchedulesService courseSchedulesService;

    /**
     * 根据学生ID查询选课信息
     *
     * @param studentId 学生ID
     * @return 选课信息列表
     */
    @GetMapping("/getCourses")
    public Result getStudentCourses(@RequestParam String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return Result.error("学生ID不能为空");
        }
        List<String> coursessID = studentCoursesService.getStudentCoursesID(studentId);
        return courseSchedulesService.getCourseSchedules(coursessID);
    }

    /**
     * 根据课程ID查询选课学生
     *
     * @param courseId 课程ID
     * @return 选课学生列表
     */
    @GetMapping("/course")
    public Result getCourseStudents(@RequestParam String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return Result.error("课程ID不能为空");
        }
        return studentCoursesService.getCourseStudents(courseId);
    }
}
