package com.hanlc.attendence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.CourseSchedules;
import com.hanlc.attendence.entity.request.CourseSchedulesRequest;

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

    Result<List<CourseSchedulesRequest>> getCourseSchedulesByStudentID(String studentId);

    Result<List<CourseSchedulesRequest>> getCourseSchedulesBycourseId(String courseID);

    Result<Boolean> updateCourseSchedules(CourseSchedulesRequest courseSchedulesRequest);

}
