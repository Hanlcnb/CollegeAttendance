package com.hanlc.attendence.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime checkInTime;

    /**
     * 签到状态
     */
    private String status;

}
