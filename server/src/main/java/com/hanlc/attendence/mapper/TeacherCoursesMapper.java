package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.TeacherCourses;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface TeacherCoursesMapper extends BaseMapper<TeacherCourses> {

    @Select("SELECT course_id FROM teacher_courses WHERE teacher_id = #{teacherId}")
    List<String> selectByTeacherId(String teacherId);

    @Insert("INSERT INTO teacher_courses (columns...) VALUES (#{property1}, #{property2})")
    int insert(TeacherCourses teacherCourse);

    @Update("UPDATE teacher_courses SET column1 = #{property1} WHERE id = #{id}")
    int update(TeacherCourses teacherCourse);

    @Delete("DELETE FROM teacher_courses WHERE id = #{id}")
    int deleteById(Long id);
}