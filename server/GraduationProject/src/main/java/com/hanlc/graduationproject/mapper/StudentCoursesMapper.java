package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.StudentCourses;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface StudentCoursesMapper extends BaseMapper<StudentCourses> {

    @Select("SELECT course_id FROM student_courses WHERE student_id = #{studentId}")
    List<String> selectByStudentId(String studentId);

    @Select("SELECT id, student_id, selected_at, status FROM student_courses WHERE course_id = #{courseId}")
    List<StudentCourses> selectByCourseId(String courseId);

    @Insert("INSERT INTO student_courses (columns...) VALUES (#{property1}, #{property2})")
    int insert(StudentCourses studentCourse);

    @Update("UPDATE student_courses SET column1 = #{property1} WHERE id = #{id}")
    int update(StudentCourses studentCourse);

    @Delete("DELETE FROM student_courses WHERE id = #{id}")
    int deleteById(Long id);
}