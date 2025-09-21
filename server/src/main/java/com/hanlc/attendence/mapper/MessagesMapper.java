package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.Messages;
import com.hanlc.attendence.entity.request.MessagesRequest;
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

    @Select("SELECT sender_id , receiver_id , content , type , is_read , created_at " +
            "FROM messages " +
            "WHERE receiver_id = #{userId} And course_id = #{courseId}")
    List<MessagesRequest> selectByUserIdAndCourseId(String userId, String courseId);

    @Select("SELECT sender_id , receiver_id , content , type , is_read , created_at " +
            "FROM messages " +
            "WHERE course_id = #{courseId}")
    List<MessagesRequest> selectByCourseId(String courseId);

    @Select("SELECT sender_id , receiver_id , content , type , is_read , created_at " +
            "FROM messages " +
            "WHERE receiver_id = #{userId}")
    List<MessagesRequest> selectByUserId(String userId);

    @Select("SELECT sender_id , content , type , created_at " +
            "FROM messages "+
            "WHERE course_id  = #{courseId} "+
            "AND is_read = FALSE ORDER BY created_at DESC LIMIT 1 ")
    MessagesRequest selectLastMessageByCourseId(String courseId);

    @Select("SELECT COUNT(*) AS unreadCount " +
            "FROM messages "+
            "WHERE course_id  = #{courseId} AND is_read = FALSE  AND receiver_id = #{userId}")
    int getUnreadCount(String courseId , String userId);

    @Insert("INSERT INTO messages (sender_id, receiver_id, course_id, content, type, is_read, created_at) " +
            "VALUES (#{senderId}, #{receiverId}, #{courseId}, #{content}, #{type}, #{isRead}, #{createdAt})")
    int insert(Messages message);

    @Update("UPDATE messages SET column1 = #{property1} WHERE id = #{id}")
    int update(Messages message);

    @Update("UPDATE messages SET is_read = true WHERE receiver_id = #{userId} AND course_id = #{courseId}")
    int updateIsRead(String userId, String courseId);

    @Delete("DELETE FROM messages WHERE id = #{id}")
    int deleteById(Long id);
}
