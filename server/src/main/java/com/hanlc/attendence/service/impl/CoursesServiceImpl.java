package com.hanlc.attendence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Courses;
import com.hanlc.attendence.mapper.CoursesMapper;
import com.hanlc.attendence.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    //查询学号为
    @Override
    public Result<List<Courses>> getCoursesByStudentId(String studentId){
        if (studentId.isEmpty()) {
            return Result.error("学生ID不能为空");
        }
        List<Courses> coursesList = coursesMapper.getCoursesByStudentId(studentId);
        return Result.success("成功查询到学生ID为" + studentId + "的课表", coursesList);
    }


    @Override
    public Result<List<Courses>> getCoursesByTeacherId(String teacherId) {
        if(teacherId.isEmpty()){
            return Result.error("学生ID不能为空");
        }
        List<Courses> coursesList = coursesMapper.getCoursesByStudentId(teacherId);
        return Result.success("成功查询到教师ID为"+teacherId+"的课表",coursesList);
    }
}
