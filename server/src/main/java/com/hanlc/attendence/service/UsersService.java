package com.hanlc.attendence.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Users;

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

    Result getProfileInfo(String openId);

    Result getUserInfoByOpenId(String openId);

    Result getUserInfoByUserId(String userId);

    Result loginVerity(String username,String password);

    Result getPassword(String email);

    /**
     * 获取用户总数
     * @return 用户总数
     */
    Result<Long> getUserCount();

    Result addUser(Users user);

    Result deleteUser(String userId);

    Result updateUser(Users user);
}
