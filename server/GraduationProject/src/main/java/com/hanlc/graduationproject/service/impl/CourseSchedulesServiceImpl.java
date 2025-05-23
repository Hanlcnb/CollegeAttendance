package com.hanlc.graduationproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.CourseSchedules;
import com.hanlc.graduationproject.entity.request.CourseSchedulesRequest;
import com.hanlc.graduationproject.mapper.CourseSchedulesMapper;
import com.hanlc.graduationproject.service.CourseSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程安排表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class CourseSchedulesServiceImpl extends ServiceImpl<CourseSchedulesMapper, CourseSchedules> implements CourseSchedulesService {

    @Autowired
    private CourseSchedulesMapper courseSchedulesMapper;

    /**
     * 根据课程ID查询课程安排
     */
    @Override
    public Result<List<CourseSchedulesRequest>> getCourseSchedules(List<String> coursessID) {
        List<CourseSchedulesRequest> courseSchedulesRequestList = new ArrayList<>();
        for(String courseID : coursessID){
            List<CourseSchedulesRequest> schedules = courseSchedulesMapper.selectByCourseId(courseID);
            if (schedules != null && !schedules.isEmpty()) {
                courseSchedulesRequestList.addAll(schedules);
            }
        }
        return Result.success("成功通过课程ID查询课程安排", courseSchedulesRequestList);
    }

}