package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.Teachers;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

public interface TeachersMapper extends BaseMapper<Teachers> {

    @Insert("INSERT INTO teachers(id,teacher_number,department,title,office) VALUES (#{id},#{teacherNumber},#{department},#{title},#{office})")
    int insert(Teachers teacher);

    @Update("UPDATE teachers SET column1 = #{property1} WHERE id = #{id}")
    int update(Teachers teacher);

    @Delete("DELETE FROM teachers WHERE id = #{id}")
    int deleteById(Long id);
}