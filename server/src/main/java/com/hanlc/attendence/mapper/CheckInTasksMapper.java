package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.CheckInTasks;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;


public interface CheckInTasksMapper extends BaseMapper<CheckInTasks> {

    @Select("SELECT id, course_id, teacher_id, title, " +
            "CASE method " +
            "WHEN 'password' THEN 'PASSWORD' " +
            "WHEN 'face' THEN 'FACE' " +
            "WHEN 'gesture' THEN 'GESTURE' " +
            "WHEN 'QR' THEN 'QR' " +
            "END as method, " +
            "secret, start_time, end_time, location, allow_distance, created_at, status " +
            "FROM check_in_tasks WHERE course_id = #{courseId}")
    List<CheckInTasks> selectByCourseId(String courseId);

    @Insert("INSERT INTO check_in_tasks (id, course_id, teacher_id, title, method, secret, start_time, end_time, location, allow_distance, created_at, status) " +
            "VALUES (#{id}, #{courseId}, #{teacherId}, #{title}, #{method}, #{secret}, #{startTime}, #{endTime}, #{location}, #{allowDistance}, #{createdAt}, #{status})")
    int insert(CheckInTasks task);

    @Update("UPDATE check_in_tasks SET column1 = #{property1} WHERE id = #{id}")
    int update(CheckInTasks task);

    @Delete("DELETE FROM check_in_tasks WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT start_time, end_time FROM check_in_tasks WHERE id = #{taskId}")
    List<Object> getTime(String taskId);
}