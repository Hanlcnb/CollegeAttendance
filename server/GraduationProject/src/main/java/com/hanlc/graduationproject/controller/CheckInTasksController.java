package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.entity.domain.CheckInTasks;
import com.hanlc.graduationproject.service.CheckInTasksService;
import com.hanlc.graduationproject.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 签到任务表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/checkInTasks")
public class CheckInTasksController {

    @Autowired
    private CheckInTasksService checkInTasksService;

    /**
     * 教师发布签到任务
     * 往表中插入一条新任务记录
     * TODO 测试该方法
     */
    @PostMapping("/publish")
    public Result publishCheckInTask(@RequestBody CheckInTasks task) {
        return checkInTasksService.publishCheckInTask(task);
    }

    /**
     * 根据签到任务的ID 获取签到任务详情
     */
    @GetMapping("/getTask")
    public Result getTaskDetail(@RequestParam String taskId) {
        return checkInTasksService.getTaskDetail(taskId);
    }

    /**
     * 根据课程ID查询所有签到任务
     */
    @GetMapping("/getAllTasks")
    public Result getCourseTasks(@RequestParam String courseId) {
        return checkInTasksService.getCourseTasks(courseId);
    }
}
