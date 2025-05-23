package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.CheckInRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface CheckInRecordsMapper extends BaseMapper<CheckInRecords> {

    @Select("SELECT * FROM check_in_records WHERE student_id = #{studentId}")
    List<CheckInRecords> selectByStudentId(String studentId);

    @Select("SELECT * FROM check_in_records WHERE task_id = #{taskId}")
    List<CheckInRecords> selectByTaskId(String taskId);

    @Insert("INSERT INTO check_in_records (task_id, student_id, check_in_time, status) " +
            "VALUES ( #{taskId}, #{studentId}, #{checkInTime}, #{status})")
    int insert(CheckInRecords record);

    @Update("UPDATE check_in_records SET column1 = #{property1} WHERE id = #{id}")
    int update(CheckInRecords record);

    @Delete("DELETE FROM check_in_records WHERE id = #{id}")
    int deleteById(Long id);
}