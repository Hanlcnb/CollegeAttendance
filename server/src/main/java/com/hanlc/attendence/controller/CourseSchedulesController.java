package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.request.CourseSchedulesRequest;
import com.hanlc.attendence.service.CourseSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程安排表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/courseSchedules")
public class CourseSchedulesController {

    @Autowired
    private CourseSchedulesService courseSchedulesService;

    @GetMapping("/getCourseSchedules")
    public Result getCourseSchedules(@RequestParam String courseId) {
        if(courseId.length() == 0 || courseId.equals("null")){
            return Result.error("courseId不能为0");
        }
        return courseSchedulesService.getCourseSchedulesBycourseId(courseId);
    }

    @PostMapping("/updateCourseSchedules")
    public Result updateCourseSchedules(@RequestBody CourseSchedulesRequest courseSchedulesRequest) {
        if(courseSchedulesRequest.getCourseId().length() == 0 || courseSchedulesRequest.getCourseId().equals("null")){
            return Result.error("课程数据不能为空");
        }
        return courseSchedulesService.updateCourseSchedules(courseSchedulesRequest);
    }
}
