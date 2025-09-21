package com.hanlc.attendence.service.impl;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.CheckInRecords;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.entity.request.CheckInRecordsRequest;
import com.hanlc.attendence.mapper.CheckInRecordsMapper;
import com.hanlc.attendence.mapper.CheckInTasksMapper;
import com.hanlc.attendence.service.CheckInRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private CheckInTasksMapper checkInTasksMapper;

    /**
     * 根据学生ID查询签到记录
     */
    @Override
    public Result getStudentRecords(String studentId , int page , int size , String statusRange , String dateRange) {
        try {
            int offset = (page - 1) * size;

            String status = null;
            if (statusRange != null && !statusRange.trim().isEmpty()) {
                status = statusRange.trim();
            }

            String startDate = null;
            String endDate = null;
            if (dateRange != null && !dateRange.trim().isEmpty()) {
                String normalized = dateRange.trim().toLowerCase();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                switch (normalized) {
                    case "week":
                        startDate = now.minusDays(7).format(formatter);
                        endDate = now.format(formatter);
                        break;
                    case "month":
                        startDate = now.minusDays(30).format(formatter);
                        endDate = now.format(formatter);
                        break;
                    case "quarter":
                        startDate = now.minusDays(90).format(formatter);
                        endDate = now.format(formatter);
                        break;
                    case "all":
                        startDate = null;
                        endDate = null;
                        break;
                    default:
                        // 若传入非预期值，则不加时间过滤
                        startDate = null;
                        endDate = null;
                }
            }

            long total = checkInRecordsMapper.countByStudentId(studentId, status, startDate, endDate);
            List<CheckInRecords> records = checkInRecordsMapper.selectByStudentIdPaged(studentId, status, startDate, endDate, size, offset);

            java.util.Map<String, Object> pageData = new java.util.HashMap<>();
            pageData.put("total", total);
            pageData.put("page", page);
            pageData.put("size", size);
            pageData.put("records", records);

            return Result.success("成功获取学生签到记录", pageData);
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
        
        // 获取签到任务的开始和结束时间
        List<Object> timeResults = checkInTasksMapper.getTime(checkInRecordsRequest.getTaskId());
        System.out.println("查询结果: " + timeResults);
        
        // 安全检查：确保返回的时间列表包含开始和结束时间
        if(timeResults == null || timeResults.size() < 2){
            return Result.error("获取签到任务时间信息失败，请检查任务ID是否正确");
        }
        
        // 获取并转换时间对象
        Object startTimeObj = timeResults.get(0);
        Object endTimeObj = timeResults.get(1);
        
        // 检查时间是否为 NULL
        if(startTimeObj == null || endTimeObj == null){
            return Result.error("签到任务的开始时间或结束时间为空，请检查任务配置");
        }
        
        // 转换为 LocalDateTime
        LocalDateTime startTime;
        LocalDateTime endTime;
        
        try {
            startTime = convertToLocalDateTime(startTimeObj);
            endTime = convertToLocalDateTime(endTimeObj);
        } catch (Exception e) {
            return Result.error("时间格式转换失败：" + e.getMessage());
        }
        
        // 检查是否在签到时间内
        if(checkInRecordsRequest.getCheckInTime().compareTo(startTime) < 0 ||
                checkInRecordsRequest.getCheckInTime().compareTo(endTime) > 0){
            return Result.error("未在签到时间内，签到时间：" + startTime + " 至 " + endTime);
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
    
    /**
     * 将各种时间类型转换为 LocalDateTime
     */
    private LocalDateTime convertToLocalDateTime(Object timeObj) {
        if (timeObj == null) {
            throw new IllegalArgumentException("时间对象不能为空");
        }
        
        if (timeObj instanceof LocalDateTime) {
            return (LocalDateTime) timeObj;
        } else if (timeObj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) timeObj).toLocalDateTime();
        } else if (timeObj instanceof java.sql.Date) {
            return ((java.sql.Date) timeObj).toLocalDate().atStartOfDay();
        } else if (timeObj instanceof java.util.Date) {
            return new java.sql.Timestamp(((java.util.Date) timeObj).getTime()).toLocalDateTime();
        } else if (timeObj instanceof String) {
            return LocalDateTime.parse((String) timeObj);
        } else {
            throw new IllegalArgumentException("不支持的时间类型: " + timeObj.getClass().getName());
        }
    }
}
