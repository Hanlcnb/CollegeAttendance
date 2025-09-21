package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.request.CheckInRecordsRequest;
import com.hanlc.attendence.service.CheckInRecordsService;
import com.hanlc.attendence.service.CheckInTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 签到记录表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/checkInRecords")
public class CheckInRecordsController {

    @Autowired
    private CheckInRecordsService checkInRecordsService;

    @Autowired
    private CheckInTasksService checkInTasksService;

    /**
     * 学生进行签到
     * 向表中插入一条新记录
     */
    @PostMapping("/checkIn")
    public Result studentCheckIn(@RequestBody CheckInRecordsRequest checkInRecordsRequest){
        return checkInRecordsService.insertRecords(checkInRecordsRequest);
    }

    /**
     * 根据学生ID查询签到记录
     *
     * @param studentId 学生ID
     * @return 签到记录列表
     */
    @PostMapping("/student")
    public Result getStudentRecords(@RequestParam String studentId,
                                    @RequestParam(defaultValue = "1") int page ,
                                    @RequestParam(required = false, defaultValue = "") String statusRange ,
                                    @RequestParam(required = false, defaultValue = "") String dateRange) {
        if (studentId == null || studentId.isEmpty()) {
            return Result.error("学生ID不能为空");
        }
        if (page < 1) {
            page = 1;
        }
        int size = 10;
        return checkInRecordsService.getStudentRecords(studentId , page , size, statusRange , dateRange);
    }

    /**
     * 根据签到任务ID查询签到记录
     *
     * @param taskId 签到任务ID
     * @return 签到记录列表
     */
    @GetMapping("/task/{taskId}")
    public Result getTaskRecords(@PathVariable String taskId) {
        if (taskId == null || taskId.isEmpty()) {
            return Result.error("签到任务ID不能为空");
        }
        return checkInRecordsService.getTaskRecords(taskId);
    }
}
