package com.hanlc.graduationproject.service.impl;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.CheckInRecords;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.entity.request.CheckInRecordsRequest;
import com.hanlc.graduationproject.mapper.CheckInRecordsMapper;
import com.hanlc.graduationproject.service.CheckInRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 签到记录表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class CheckInRecordsServiceImpl extends ServiceImpl<CheckInRecordsMapper, CheckInRecords> implements CheckInRecordsService {

    @Autowired
    private CheckInRecordsMapper checkInRecordsMapper;

    /**
     * 根据学生ID查询签到记录
     */
    @Override
    public Result getStudentRecords(String studentId) {
        try {
            List<CheckInRecords> records = checkInRecordsMapper.selectByStudentId(studentId);
            if (records == null || records.isEmpty()) {
                return Result.success("该学生暂无签到记录", null);
            }
            return Result.success("成功获取学生签到记录", records);
        } catch (Exception e) {
            return Result.error("获取学生签到记录失败：" + e.getMessage());
        }
    }

    /**
     * 根据签到任务ID查询签到记录
     */
    @Override
    public Result getTaskRecords(String taskId) {
        try {
            List<CheckInRecords> records = checkInRecordsMapper.selectByTaskId(taskId);
            if (records == null || records.isEmpty()) {
                return Result.success("该签到任务暂无记录", null);
            }
            return Result.success("成功获取签到任务记录", records);
        } catch (Exception e) {
            return Result.error("获取签到任务记录失败：" + e.getMessage());
        }
    }

    @Override
    public Result insertRecords(CheckInRecordsRequest checkInRecordsRequest){
        if(checkInRecordsRequest == null){
            return Result.error("签到参数不能为空");
        }
        CheckInRecords checkInRecords = new CheckInRecords();
        checkInRecords.setTaskId(checkInRecordsRequest.getTaskId());
        checkInRecords.setStudentId(checkInRecordsRequest.getStudentId());
        checkInRecords.setCheckInTime(checkInRecordsRequest.getCheckInTime());
        checkInRecords.setStatus(checkInRecordsRequest.getStatus());

        if(checkInRecordsMapper.insert(checkInRecords) == 1){
            return Result.success("成功创建一条新的学生签到记录");
        }
        return Result.error("创建一条新的学生签到记录失败");
    }
}
