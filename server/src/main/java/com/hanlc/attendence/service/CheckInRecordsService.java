package com.hanlc.attendence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.CheckInRecords;
import com.hanlc.attendence.entity.request.CheckInRecordsRequest;


public interface CheckInRecordsService extends IService<CheckInRecords> {

    Result getStudentRecords(String studentId , int page , int size , String statusRange , String dateRange);

    Result getTaskRecords(String taskId);

    Result insertRecords(CheckInRecordsRequest checkInRecordsRequest);

}
