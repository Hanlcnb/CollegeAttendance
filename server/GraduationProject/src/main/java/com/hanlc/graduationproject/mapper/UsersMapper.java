package com.hanlc.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanlc.graduationproject.entity.domain.Users;
import com.hanlc.graduationproject.entity.request.UsersInfoRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户基础表 Mapper 接口
 * @author Hanlc
 */
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据openId查询用户信息
     * @param openId 微信唯一标识openId
     * @return 用户信息
     */
    @Select("SELECT id,username,password,real_name,avatar,phone,email,role FROM users WHERE openid = #{openId}")
    UsersInfoRequest selectByOpenId(String openId);

    @Select("SELECT id FROM users WHERE openid = #{openId}")
    String selectUserIDByOpenId(String openId);

}
