package com.hanlc.graduationproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.entity.domain.Teachers;
import com.hanlc.graduationproject.entity.domain.Users;
import com.hanlc.graduationproject.entity.request.TeacherInfoRequest;
import com.hanlc.graduationproject.entity.request.UsersInfoRequest;
import com.hanlc.graduationproject.mapper.TeachersMapper;
import com.hanlc.graduationproject.mapper.UsersMapper;
import com.hanlc.graduationproject.service.TeachersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanlc.graduationproject.common.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 教师信息表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers> implements TeachersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TeachersMapper teachersMapper;

    @Override
    public TeacherInfoRequest getTeacherInfo(String openId) {
        // 根据openId查询用户信息
        UsersInfoRequest usersInfoRequest = usersMapper.selectByOpenId(openId);
        if (usersInfoRequest == null) {
            return null;
        }
        if( usersInfoRequest.getRole() == "studnet"){
            return null;
        }
        // 根据用户ID查询教师信息
        Teachers teacher = baseMapper.selectById(usersInfoRequest.getId());
        if (teacher == null) {
            return null;
        }

        // 组装返回数据
        TeacherInfoRequest request = new TeacherInfoRequest();
        BeanUtils.copyProperties(teacher, request);
        request.setName(usersInfoRequest.getRealName());
        return request;
    }

    /**
     * 添加教师信息
     */
    @Transactional
    public Result addTeacher(Teachers teacher) {
        try {
            // 检查教师是否已存在
            Teachers existingTeacher = teachersMapper.selectById(teacher.getId());
            if (existingTeacher != null) {
                return Result.error("该教师已存在");
            }

            // 添加教师信息
            int result = teachersMapper.insert(teacher);
            if (result > 0) {
                return Result.success("添加教师信息成功");
            } else {
                return Result.error("添加教师信息失败");
            }
        } catch (Exception e) {
            return Result.error("添加教师信息失败：" + e.getMessage());
        }
    }

    /**
     * 更新教师信息
     */
    @Transactional
    public Result updateTeacher(Teachers teacher) {
        try {
            // 检查教师是否存在
            Teachers existingTeacher = teachersMapper.selectById(teacher.getId());
            if (existingTeacher == null) {
                return Result.error("教师不存在");
            }

            // 更新教师信息
            int result = teachersMapper.updateById(teacher);
            if (result > 0) {
                return Result.success("更新教师信息成功");
            } else {
                return Result.error("更新教师信息失败");
            }
        } catch (Exception e) {
            return Result.error("更新教师信息失败：" + e.getMessage());
        }
    }

    /**
     * 删除教师信息
     */
    @Transactional
    public Result deleteTeacher(String userId) {
        try {
            // 检查教师是否存在
            Teachers teacher = teachersMapper.selectById(userId);
            if (teacher == null) {
                return Result.error("教师不存在");
            }

            // 删除教师信息
            int result = teachersMapper.deleteById(userId);
            if (result > 0) {
                return Result.success("删除教师信息成功");
            } else {
                return Result.error("删除教师信息失败");
            }
        } catch (Exception e) {
            return Result.error("删除教师信息失败：" + e.getMessage());
        }
    }
}
