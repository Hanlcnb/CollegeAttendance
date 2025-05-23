package com.hanlc.graduationproject.entity.request;

import lombok.Data;


@Data
public class CheckInRecordsRequest {

    /**
     * 签到任务ID
     */
    private String taskId;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 签到时间
     */
    private String checkInTime;

    /**
     * 签到状态
     */
    private String status;

}
