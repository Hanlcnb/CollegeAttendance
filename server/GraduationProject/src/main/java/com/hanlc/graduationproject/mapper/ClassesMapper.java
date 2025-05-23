package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.Classes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {

    @Insert("INSERT INTO classes (columns...) VALUES (#{property1}, #{property2})")
    int insert(Classes clazz);

    @Update("UPDATE classes SET column1 = #{property1} WHERE id = #{id}")
    int update(Classes clazz);

    @Delete("DELETE FROM classes WHERE id = #{id}")
    int deleteById(Long id);
}