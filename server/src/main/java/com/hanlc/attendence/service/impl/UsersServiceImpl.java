package com.hanlc.attendence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Users;
import com.hanlc.attendence.entity.request.UsersInfoRequest;
import com.hanlc.attendence.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.attendence.service.UsersService;
import com.hanlc.attendence.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        return Result.success("成功通过openId查找用户ID",userID);
    }

    //通过openId查找用户信息
    @Override
    public Result getUserInfoByOpenId(String openId){
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
    public Result getUserInfoByUserId(String userId){
        if(userId == null){
            return Result.error("openId不能为空");
        }
        UsersInfoRequest usersInfoRequest = usersMapper.selectById(userId);
        if(usersInfoRequest == null) {
            return Result.notFound();
        }
        return Result.success("成功通过openId查找用户信息",usersInfoRequest);
    }

    @Override
    public Result loginVerity(String username,String password){
        String p = usersMapper.getPasswordByUsername(username);
        if(p.isEmpty()){
            return Result.notFound();
        }
        if(p.equals(password)){
            UsersInfoRequest usersInfoRequest= usersMapper.selectByUsername(username);
            return Result.success("密码验证成功，用户信息如下",usersInfoRequest);
        }
        return Result.error("查询失败");
    }

    @Override
    public Result<UsersInfoRequest> getProfileInfo(String openId){
        UsersInfoRequest users = usersMapper.selectByOpenId(openId);
        
        if(users == null) {
            return Result.notFound();
        }
        
        // 可以在这里添加一些额外的用户信息处理逻辑
        // 例如：敏感信息过滤、数据格式化等
        return Result.success("获取用户个人信息成功",users);
    }

    @Override
    @Transactional
    public Result addUser(Users user) {
        Users newUser = new Users();
        QueryWrapper<Users> queryWrapper;
        String userID;
        do{
            userID = RandomUtils.randomUID();
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userID);
        }while(usersMapper.selectOne(queryWrapper) != null);
        newUser.setId(userID);
        newUser.setOpenId(user.getOpenId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setRealName(user.getRealName());
        newUser.setAvatar(user.getAvatar());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setStatus(true);
        newUser.setCreatedAt(LocalDateTime.now().withNano(0));
        newUser.setUpdatedAt(LocalDateTime.now().withNano(0));
        System.out.println(newUser);
        try{
            if(usersMapper.insertUsers(newUser) > 0){
                return Result.success("用户注册成功");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return Result.error("用户注册失败");
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
    @Override
    @Transactional
    public Result updateUser(Users user) {
        try {
            // 检查用户是否存在
            Users existingUser = usersMapper.CheckById(user.getId());
            System.out.println(existingUser);
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

    @Override
    public Result getPassword(String email){
        String p = usersMapper.getPasswordByEmail(email);
        return Result.success("根据用户邮箱成功查找到密码",p);
    }

}
