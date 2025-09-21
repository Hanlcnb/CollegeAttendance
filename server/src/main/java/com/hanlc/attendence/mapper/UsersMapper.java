package com.hanlc.attendence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.attendence.entity.domain.Users;
import com.hanlc.attendence.entity.request.UsersInfoRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 用户基础表 Mapper 接口
 * @author Hanlc
 */
public interface UsersMapper extends BaseMapper<Users> {


    @Select("SELECT password FROM users WHERE email=#{email}")
    String getPasswordByEmail(String email);

    @Select("SELECT password FROM users WHERE username=#{username}")
    String getPasswordByUsername(String username);

    @Select("SELECT openId FROM users WHERE username=#{username}")
    String getOpenId(String username);

    @Select("SELECT id,username,password,real_name,avatar,phone,email,role FROM users WHERE username = #{username}")
    UsersInfoRequest selectByUsername(String username);

    @Select("SELECT id,username,password,real_name,avatar,phone,email,role FROM users WHERE openid = #{openId}")
    UsersInfoRequest selectByOpenId(String openId);

    @Select("SELECT id,username,password,real_name,avatar,phone,email,role FROM users WHERE id = #{Id}")
    UsersInfoRequest selectById(String Id);

    @Select("SELECT id FROM users WHERE openid = #{openId}")
    String selectUserIDByOpenId(String openId);

    @Select("SELECT id,username,password,real_name,avatar,phone,email,role FROM users WHERE id = #{Id}")
    Users CheckById(String Id);

    @Select("SELECT username FROM users WHERE id = #{id}")
    String selectUsernameById(String id);

    @Insert("Insert into users(id,openid,username,password,real_name,avatar,phone,email,role,status,created_at,updated_at) " +
            "values(#{id},#{openId},#{username},#{password},#{realName},#{avatar},#{phone},#{email},#{role},#{status},#{createdAt},#{updatedAt})")
    int insertUsers(Users users);

}
