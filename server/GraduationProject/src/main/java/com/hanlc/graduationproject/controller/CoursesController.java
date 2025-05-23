package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Courses;
import com.hanlc.graduationproject.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/courses")
public class CoursesController {

    @Autowired
    private CoursesService coursesService;

    /**
     * 根据学生ID查询课程列表
     *
     * @param studentId 学生ID
     * @return 课程列表
     */
    @GetMapping("/student")
    public Result<List<Courses>> getCoursesByStudentId(@RequestParam String studentId) {
        return coursesService.getCoursesByStudentId(studentId);
    }

    /**
     * 根据教师ID查询课程列表
     *
     * @param teacherId 教师ID
     * @return 课程列表
     */
    @GetMapping("/teacher")
    public Result<List<Courses>> getCoursesByTeacherId(@RequestParam String teacherId) {
        return coursesService.getCoursesByStudentId(teacherId);
    }
}
