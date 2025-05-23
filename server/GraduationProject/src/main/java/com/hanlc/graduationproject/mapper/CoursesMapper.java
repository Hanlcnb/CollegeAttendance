package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.Courses;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Mapper
public interface CoursesMapper extends BaseMapper<Courses> {

    /**
     * 根据学生ID查询课程列表
     *
     * @param studentId 学生ID
     * @return 课程列表
     */
    @Select("SELECT c.* FROM courses c " +
            "INNER JOIN student_courses sc ON c.id = sc.course_id " +
            "WHERE sc.student_id = #{studentId}")
    List<Courses> getCoursesByStudentId(String studentId);

    /**
     * 根据教师ID查询课程列表
     *
     * @param teacherId 教师ID
     * @return 课程列表
     */
    @Select("SELECT c.* FROM courses c " +
            "INNER JOIN teacher_courses tc ON c.id = tc.course_id " +
            "WHERE tc.teacher_id = #{teacherId}")
    List<Courses> getCoursesByTeacherId(String teacherId);

    @Insert("INSERT INTO courses (columns...) VALUES (#{property1}, #{property2})")
    int insert(Courses course);

    @Update("UPDATE courses SET column1 = #{property1} WHERE id = #{id}")
    int update(Courses course);

    @Delete("DELETE FROM courses WHERE id = #{id}")
    int deleteById(Long id);
}
