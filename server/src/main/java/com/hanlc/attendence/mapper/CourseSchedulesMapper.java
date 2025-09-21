package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.CourseSchedules;
import com.hanlc.attendence.entity.request.CourseSchedulesRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface CourseSchedulesMapper extends BaseMapper<CourseSchedules> {

    @Select("SELECT c.name, cs.course_id , cs.week_day, cs.start_week, cs.end_week, " +
             "cs.section , cs.section_count, " +
            "cs.location " +
            "FROM course_schedules cs JOIN courses c ON cs.course_id = c.id " +
            "WHERE cs.course_id IN (SELECT course_id FROM student_courses WHERE student_id = #{studentId})")
    List<CourseSchedulesRequest> selectByStudentId(String studentId);

    @Select("SELECT c.name, cs.course_id , cs.week_day, cs.start_week, cs.end_week, " +
            "cs.section , cs.section_count, " +
            "cs.location " +
            "FROM course_schedules cs JOIN courses c ON cs.course_id = c.id " +
            "WHERE cs.course_id  = #{courseId}")
    List<CourseSchedulesRequest> selectByCourseId(String courseId);

    @Insert("INSERT INTO course_schedules (course_id , week_day , start_week , end_week , section , section_count , location) " +
            "VALUES (#{courseId}, #{weekDay} , #{startWeek} , #{endWeek} , #{section} , #{sectionCount} , #{location})")
    int insert(CourseSchedules schedule);

    @Update("UPDATE course_schedules " +
            "SET week_day = #{weekDay} , start_week = #{startWeek} , end_week  = #{endWeek} , section = #{section} , section_count = #{sectionCount} , location = #{location} " +
            "WHERE course_id = #{courseId}")
    int update(CourseSchedules schedule);

    @Delete("DELETE FROM course_schedules WHERE id = #{id}")
    int deleteById(Long id);
}