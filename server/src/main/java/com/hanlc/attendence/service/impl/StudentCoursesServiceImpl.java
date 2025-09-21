package com.hanlc.attendence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.StudentCourses;
import com.hanlc.attendence.mapper.StudentCoursesMapper;
import com.hanlc.attendence.service.StudentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生选课表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class StudentCoursesServiceImpl extends ServiceImpl<StudentCoursesMapper, StudentCourses> implements  StudentCoursesService {

    @Autowired
    private StudentCoursesMapper studentCoursesMapper;

    /**
     * 根据学生ID查询选课信息
     */
    @Override
    public List<String> getCoursesID(String studentId) {
        List<String> coursesID = studentCoursesMapper.selectByStudentId(studentId);
        if (coursesID == null || coursesID.isEmpty()) {
            return null;
        }
        return coursesID;
    }

    /**
     * 根据课程ID查询选课学生
     */
    @Override
    public Result getCourseStudents(String courseId) {
        try {
            List<StudentCourses> students = studentCoursesMapper.selectByCourseId(courseId);
            if (students == null || students.isEmpty()) {
                return Result.success("该课程暂无学生选课", null);
            }
            return Result.success("成功获取选课学生信息", students);
        } catch (Exception e) {
            return Result.error("获取选课学生信息失败：" + e.getMessage());
        }
    }
}
