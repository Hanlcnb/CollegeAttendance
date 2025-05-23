package com.hanlc.graduationproject.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 签到记录表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@TableName("check_in_records")
public class CheckInRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private int id;

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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "CheckInRecords{" +
            "id = " + id +
            ", taskId = " + taskId +
            ", studentId = " + studentId +
            ", checkInTime = " + checkInTime +
            ", status = " + status +
        "}";
    }
}
