package com.hanlc.attendence.service;

import com.hanlc.attendence.entity.domain.CheckInTasks;
import com.hanlc.attendence.common.Result;

import java.util.List;

/**
 * <p>
 * 签到任务表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface CheckInTasksService {
    
    /**
     * 教师发布签到任务
     */
    Result publishCheckInTask(CheckInTasks task);

    /**
     * 学生进行签到
     */
    Result studentCheckIn(String taskId, String studentId, String secret, String location);

    /**
     * 获取签到任务详情
     */
    Result getTaskDetail(String taskId);

    /**
     * 获取课程的所有签到任务
     */
    Result<List<CheckInTasks>> getCourseTasks(String courseId);
} 