package com.hanlc.graduationproject.service.impl;

import com.hanlc.graduationproject.entity.domain.CheckInTasks;
import com.hanlc.graduationproject.entity.domain.CheckInRecords;
import com.hanlc.graduationproject.entity.enums.CheckInMethod;
import com.hanlc.graduationproject.mapper.CheckInTasksMapper;
import com.hanlc.graduationproject.mapper.CheckInRecordsMapper;
import com.hanlc.graduationproject.service.CheckInTasksService;
import com.hanlc.graduationproject.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 签到任务表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class CheckInTasksServiceImpl implements CheckInTasksService {

    @Autowired
    private CheckInTasksMapper checkInTasksMapper;

    @Autowired
    private CheckInRecordsMapper checkInRecordsMapper;

    /**
     * 在数据库中新建一个签到任务
     * @param task
     * @return
     */
    @Override
    @Transactional
    public Result publishCheckInTask(CheckInTasks task) {
        try {
            // 设置任务ID和创建时间
            task.setId(UUID.randomUUID().toString());
            task.setCreatedAt(LocalDateTime.now().toString());
            task.setStatus("active");
            
            // 保存签到任务
            checkInTasksMapper.insert(task);
            return Result.success("发布签到任务成功");
        } catch (Exception e) {
            return Result.error("发布签到任务失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result studentCheckIn(String taskId, String studentId, String secret, String location) {
        try {
            // 获取签到任务
            CheckInTasks task = checkInTasksMapper.selectById(taskId);
            if (task == null) {
                return Result.error("签到任务不存在");
            }

            // 检查签到时间
            LocalDateTime now = LocalDateTime.now();

            // 根据签到方式验证
            boolean isValid = false;
            switch (task.getMethod()) {
                case PASSWORD:
                    isValid = task.getSecret().equals(secret);
                    break;
                case FACE:
                    // 人脸识别逻辑需要单独实现
                    isValid = true; // 临时返回true
                    break;
                case GESTURE:
                    //手势签到

                case QR:
                    //二维码签到
            }

            if (!isValid) {
                return Result.error("签到验证失败");
            }

            // 创建签到记录
            CheckInRecords record = new CheckInRecords();
            record.setTaskId(taskId);
            record.setStudentId(studentId);
            record.setStatus("success");
            
            checkInRecordsMapper.insert(record);
            return Result.success("签到成功");
        } catch (Exception e) {
            return Result.error("签到失败：" + e.getMessage());
        }
    }

    @Override
    public Result getTaskDetail(String taskId) {
        try {
            CheckInTasks task = checkInTasksMapper.selectById(taskId);
            if (task == null) {
                return Result.error("签到任务不存在");
            }
            return Result.success(task);
        } catch (Exception e) {
            return Result.error("获取签到任务详情失败：" + e.getMessage());
        }
    }

    @Override
    public Result<List<CheckInTasks>> getCourseTasks(String courseId) {
        try {
            System.out.println("正在查询课程ID: " + courseId + " 的签到任务");
            List<CheckInTasks> tasks = checkInTasksMapper.selectByCourseId(courseId);
            System.out.println("查询结果: " + (tasks != null ? tasks.size() : 0) + " 条记录");
            if (tasks == null || tasks.isEmpty()) {
                return Result.success("该课程暂无签到任务", null);
            }
            return Result.success("获取课程签到任务成功", tasks);
        } catch (Exception e) {
            System.out.println("查询签到任务时发生异常: " + e.getMessage());
            e.printStackTrace();
            return Result.error("获取课程签到任务失败：" + e.getMessage());
        }
    }
}
