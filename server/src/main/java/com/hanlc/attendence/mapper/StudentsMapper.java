package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.Students;
import com.hanlc.attendence.entity.request.StudentInfoRequest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentsMapper extends BaseMapper<Students> {

    @Select("SELECT student_number, department, major FROM students WHERE id = #{id}")
    StudentInfoRequest selectStudentInfoById(@Param("id") String id);

    @Select("SELECT student_number FROM students WHERE id = #{userId}")
    String checkById(String userId);

    @Delete("DELETE FROM students WHERE id = #{userId}")
    int deleteById(String userId);

    @Insert("INSERT INTO students (id,student_number , class_id,department , major , enrollment_year) " +
            "VALUES (#{id} , #{studentNumber} , #{classId} , #{department} , #{major} , #{enrollmentYear})")
    int insert(Students student);

    @Update("UPDATE students SET column1 = #{property1} WHERE id = #{id}")
    int update(Students student);
}