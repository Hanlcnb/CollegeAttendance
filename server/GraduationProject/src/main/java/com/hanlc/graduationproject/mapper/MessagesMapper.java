package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.Messages;
import com.hanlc.graduationproject.entity.request.MessagesRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

    @Select("SELECT sender_id , receiver_id , content , type , is_read , created_at AS time " +
            "FROM messages " +
            "WHERE receiver_id = #{userId} And course_id = #{courseId}")
    List<MessagesRequest> selectByUserIdAndCourseId(String userId, String courseId);

    @Insert("INSERT INTO messages (sender_id, receiver_id, course_id, content, type, is_read, created_at) " +
            "VALUES (#{senderId}, #{receiverId}, #{courseId}, #{content}, #{type}, #{isRead}, #{createdAt})")
    int insert(Messages message);

    @Update("UPDATE messages SET column1 = #{property1} WHERE id = #{id}")
    int update(Messages message);

    @Delete("DELETE FROM messages WHERE id = #{id}")
    int deleteById(Long id);
}
