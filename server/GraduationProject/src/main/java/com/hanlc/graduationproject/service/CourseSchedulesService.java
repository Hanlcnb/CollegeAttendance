package com.hanlc.graduationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.CourseSchedules;
import com.hanlc.graduationproject.entity.request.CourseSchedulesRequest;

import java.util.List;

/**
 * <p>
 * 课程安排表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface CourseSchedulesService extends IService<CourseSchedules> {

    Result<List<CourseSchedulesRequest>> getCourseSchedules(List<String> coursessID);

}
