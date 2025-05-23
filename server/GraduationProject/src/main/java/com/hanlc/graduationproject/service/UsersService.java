package com.hanlc.graduationproject.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Users;

/**
 * <p>
 * 用户基础表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:21:56
 */
public interface UsersService extends IService<Users> {

    Result getUserID(String openId);

    Result getUserInfo(String openId );

    Result getProfileInfo(String openId);

    /**
     * 获取用户总数
     * @return 用户总数
     */
    Result<Long> getUserCount();

    Result deleteUser(String userId);

    Result updateUser(Users user);
}
