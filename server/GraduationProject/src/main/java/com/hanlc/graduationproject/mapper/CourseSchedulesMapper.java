package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.CourseSchedules;
import com.hanlc.graduationproject.entity.request.CourseSchedulesRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface CourseSchedulesMapper extends BaseMapper<CourseSchedules> {

    @Select("SELECT c.name, cs.course_id , cs.week_day, cs.start_week, cs.end_week, " +
            "TIME_FORMAT(cs.start_time, '%H:%i:%s') as start_time, " +
            "TIME_FORMAT(cs.end_time, '%H:%i:%s') as end_time, " +
            "cs.location " +
            "FROM course_schedules cs JOIN courses c ON cs.course_id = c.id " +
            "WHERE cs.course_id = #{courseId}")
    List<CourseSchedulesRequest> selectByCourseId(String courseId);

    @Insert("INSERT INTO course_schedules (columns...) VALUES (#{property1}, #{property2})")
    int insert(CourseSchedules schedule);

    @Update("UPDATE course_schedules SET column1 = #{property1} WHERE id = #{id}")
    int update(CourseSchedules schedule);

    @Delete("DELETE FROM course_schedules WHERE id = #{id}")
    int deleteById(Long id);
}