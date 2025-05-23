package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.Students;
import com.hanlc.graduationproject.entity.request.StudentInfoRequest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentsMapper extends BaseMapper<Students> {

    @Select("SELECT student_number, department, major FROM students WHERE id = #{id}")
    StudentInfoRequest selectStudentInfoById(@Param("id") String id);

    @Select("SELECT student_number FROM students WHERE student_id = #{studentId}")
    String checkByStudentId(String studentId);

    @Delete("DELETE FROM students WHERE student_id = #{studentId}")
    int deleteByStudentId(String studentId);

    @Insert("INSERT INTO students (columns...) VALUES (#{property1}, #{property2})")
    int insert(Students student);

    @Update("UPDATE students SET column1 = #{property1} WHERE id = #{id}")
    int update(Students student);
}