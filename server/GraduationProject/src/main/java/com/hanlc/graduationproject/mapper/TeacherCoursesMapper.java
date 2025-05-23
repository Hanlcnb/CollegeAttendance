package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.TeacherCourses;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

public interface TeacherCoursesMapper extends BaseMapper<TeacherCourses> {

    @Insert("INSERT INTO teacher_courses (columns...) VALUES (#{property1}, #{property2})")
    int insert(TeacherCourses teacherCourse);

    @Update("UPDATE teacher_courses SET column1 = #{property1} WHERE id = #{id}")
    int update(TeacherCourses teacherCourse);

    @Delete("DELETE FROM teacher_courses WHERE id = #{id}")
    int deleteById(Long id);
}