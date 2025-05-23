package com.hanlc.graduationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.CheckInRecords;
import com.hanlc.graduationproject.entity.request.CheckInRecordsRequest;


public interface CheckInRecordsService extends IService<CheckInRecords> {

    Result getStudentRecords(String studentId);

    Result getTaskRecords(String taskId);

    Result insertRecords(CheckInRecordsRequest checkInRecordsRequest);

}
