package com.hanlc.attendence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.CourseSchedules;
import com.hanlc.attendence.entity.domain.Courses;
import com.hanlc.attendence.entity.request.CourseSchedulesRequest;
import com.hanlc.attendence.mapper.CourseSchedulesMapper;
import com.hanlc.attendence.mapper.CoursesMapper;
import com.hanlc.attendence.service.CourseSchedulesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private CoursesMapper coursesMapper;

    /**
     * 根据学生ID查询课程安排
     */
    @Override
    public Result<List<CourseSchedulesRequest>> getCourseSchedulesByStudentID(String studentId) {
        List<CourseSchedulesRequest> courseSchedulesList;
        courseSchedulesList = courseSchedulesMapper.selectByStudentId(studentId);
        return Result.success("成功通过课程ID查询课程安排", courseSchedulesList);
    }

    /**
     * 根据单个课程ID查询课程安排
     */
    @Override
    public Result<List<CourseSchedulesRequest>> getCourseSchedulesBycourseId(String courseID) {
        List<CourseSchedulesRequest> courseSchedulesRequestList = courseSchedulesMapper.selectByCourseId(courseID);
        return Result.success("成功通过课程ID查询课程安排", courseSchedulesRequestList);
    }

    /**
     * 前端修改课程信息后传递给后端一个CourseSchedulesRequest，将该元素的属性分别更新course表和courseSchedule表
     * @param courseSchedulesRequest
     * @return
     */
    @Override
    public Result<Boolean> updateCourseSchedules(CourseSchedulesRequest courseSchedulesRequest) {
        String courseID = courseSchedulesRequest.getCourseId();
        QueryWrapper<CourseSchedules> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseID);
        if(courseSchedulesMapper.selectCount(queryWrapper) < 1){
            return Result.error("该课程不存在");
        }else if(courseSchedulesMapper.selectCount(queryWrapper) > 1){
            return Result.error("课程数据有误，无法更新");
        }
        //更新course表中name属性
        String courseName = courseSchedulesRequest.getName();
        Courses courses = new Courses();
        courses.setName(courseName);
        courses.setId(courseID);
        coursesMapper.updateCourseName(courses);

        //再更新courseSchedule表中的所有属性
        CourseSchedules courseSchedules = new CourseSchedules();
        BeanUtils.copyProperties(courseSchedulesRequest,courseSchedules,"name");
        courseSchedulesMapper.update(courseSchedules);
        return Result.success("课程数据更新成功");
    }
}