package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.CheckInRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckInRecordsMapper extends BaseMapper<CheckInRecords> {

    @Select("SELECT task_id , check_in_time , status , location FROM check_in_records WHERE student_id = #{studentId}")
    List<CheckInRecords> selectByStudentId(String studentId);

    @Select({
            "<script>",
            "SELECT COUNT(1) FROM check_in_records",
            "WHERE student_id = #{studentId}",
            "<if test=\"status != null and status != ''\">",
            "  AND status = #{status}",
            "</if>",
            "<if test=\"startDate != null and startDate != ''\">",
            "  AND check_in_time &gt;= #{startDate}",
            "</if>",
            "<if test=\"endDate != null and endDate != ''\">",
            "  AND check_in_time &lt;= #{endDate}",
            "</if>",
            "</script>"
    })
    long countByStudentId(@Param("studentId") String studentId,
                          @Param("status") String status,
                          @Param("startDate") String startDate,
                          @Param("endDate") String endDate);

    @Select({
            "<script>",
            "SELECT id, task_id, student_id, check_in_time, status, location FROM check_in_records",
            "WHERE student_id = #{studentId}",
            "<if test=\"status != null and status != ''\">",
            "  AND status = #{status}",
            "</if>",
            "<if test=\"startDate != null and startDate != ''\">",
            "  AND check_in_time &gt;= #{startDate}",
            "</if>",
            "<if test=\"endDate != null and endDate != ''\">",
            "  AND check_in_time &lt;= #{endDate}",
            "</if>",
            "ORDER BY check_in_time DESC",
            "LIMIT #{size} OFFSET #{offset}",
            "</script>"
    })
    List<CheckInRecords> selectByStudentIdPaged(@Param("studentId") String studentId,
                                                @Param("status") String status,
                                                @Param("startDate") String startDate,
                                                @Param("endDate") String endDate,
                                                @Param("size") int size,
                                                @Param("offset") int offset);

    @Select("SELECT student_id , check_in_time , status , location FROM check_in_records WHERE task_id = #{taskId}")
    List<CheckInRecords> selectByTaskId(String taskId);

    @Insert("INSERT INTO check_in_records (task_id, student_id, check_in_time, status) " +
            "VALUES ( #{taskId}, #{studentId}, #{checkInTime}, #{status})")
    int insert(CheckInRecords record);

    @Delete("DELETE FROM check_in_records WHERE id = #{id}")
    int deleteById(Long id);
}