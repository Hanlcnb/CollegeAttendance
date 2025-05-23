package com.hanlc.graduationproject.service.impl;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Users;
import com.hanlc.graduationproject.entity.request.UsersInfoRequest;
import com.hanlc.graduationproject.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户基础表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:21:56
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Result getUserID(String openId) {
        if(openId == null){
            return Result.error("openId不能为空");
        }
        String userID = usersMapper.selectUserIDByOpenId(openId);
        return Result.success("成功通过openId查找用户信息",userID);
    }

    //通过openId查找用户信息
    @Override
    public Result getUserInfo(String openId){
        if(openId == null){
            return Result.error("openId不能为空");
        }
        UsersInfoRequest usersInfoRequest = usersMapper.selectByOpenId(openId);
        if(usersInfoRequest == null) {
            return Result.notFound();
        }
        return Result.success("成功通过openId查找用户信息",usersInfoRequest);
    }

    @Override
    public Result<Users> getProfileInfo(String openId){
        Users users = usersMapper.selectById(openId);
        
        if(users == null) {
            return Result.notFound();
        }
        
        // 可以在这里添加一些额外的用户信息处理逻辑
        // 例如：敏感信息过滤、数据格式化等
        return Result.success("获取用户个人信息成功",users);
    }

    /**
     * 删除用户
     */
    @Transactional
    public Result deleteUser(String userId) {
        try {
            // 删除用户
            int result = usersMapper.deleteById(userId);
            if (result > 0) {
                return Result.success("删除用户成功");
            } else {
                return Result.error("删除用户失败");
            }
        } catch (Exception e) {
            return Result.error("删除用户失败：" + e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @Transactional
    public Result updateUser(Users user) {
        try {
            // 检查用户是否存在
            Users existingUser = usersMapper.selectById(user.getOpenId());
            if (existingUser == null) {
                return Result.error("用户不存在");
            }

            // 更新用户信息
            int result = usersMapper.updateById(user);
            if (result > 0) {
                return Result.success("更新用户信息成功");
            } else {
                return Result.error("更新用户信息失败");
            }
        } catch (Exception e) {
            return Result.error("更新用户信息失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Long> getUserCount() {
        try {
            long count = count();
            return Result.success("获取用户总数成功", count);
        } catch (Exception e) {
            return Result.error("获取用户总数失败：" + e.getMessage());
        }
    }

}
