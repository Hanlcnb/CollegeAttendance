package com.hanlc.graduationproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Students;
import com.hanlc.graduationproject.entity.domain.Users;
import com.hanlc.graduationproject.entity.request.StudentInfoRequest;
import com.hanlc.graduationproject.entity.request.UsersInfoRequest;
import com.hanlc.graduationproject.mapper.StudentsMapper;
import com.hanlc.graduationproject.mapper.UsersMapper;
import com.hanlc.graduationproject.service.StudentsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements StudentsService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private StudentsMapper studentsMapper;

    @Override
    public StudentInfoRequest getStudentInfo(String openId) {
        // 根据openId查询用户信息
        UsersInfoRequest usersInfoRequest = usersMapper.selectByOpenId(openId);
        if (usersInfoRequest == null) {
            return null;
        }

        // 根据用户ID查询学生信息
        Students student = baseMapper.selectById(usersInfoRequest.getId());
        if (student == null) {
            return null;
        }

        // 组装返回数据
        StudentInfoRequest request = new StudentInfoRequest();
        BeanUtils.copyProperties(student, request);
        request.setName(usersInfoRequest.getRealName());
        return request;
    }

    /**
     * 添加学生信息
     */
    @Transactional
    public Result addStudent(Students student) {
        try {
            // 根据学号检查学生是否已存在
            String number = studentIsExist(student.getId());
            if (number == null) {
                return Result.error("学生不存在");
            }

            // 添加学生信息
            int result = studentsMapper.insert(student);
            if (result > 0) {
                return Result.success("添加学生信息成功");
            } else {
                return Result.error("添加学生信息失败");
            }
        } catch (Exception e) {
            return Result.error("添加学生信息失败：" + e.getMessage());
        }
    }

    /**
     * 更新学生信息
     */
    @Transactional
    public Result updateStudent(Students student) {
        try {
            // 检查学生是否存在
            String number = studentIsExist(student.getId());
            if (number == null) {
                return Result.error("学生不存在");
            }

            // 更新学生信息
            int result = studentsMapper.updateById(student);
            if (result > 0) {
                return Result.success("更新学生信息成功");
            } else {
                return Result.error("更新学生信息失败");
            }
        } catch (Exception e) {
            return Result.error("更新学生信息失败：" + e.getMessage());
        }
    }

    /**
     * 删除学生信息
     */
    @Transactional
    public Result deleteStudent(String studentId) {
        try {
            // 检查学生是否存在
            String number = studentIsExist(studentId);
            if (number == null) {
                return Result.error("学生不存在");
            }

            // 删除学生信息
            int result = studentsMapper.deleteByStudentId(studentId);
            if (result > 0) {
                return Result.success("删除学生信息成功");
            } else {
                return Result.error("删除学生信息失败");
            }
        } catch (Exception e) {
            return Result.error("删除学生信息失败：" + e.getMessage());
        }
    }

    private String studentIsExist(String studentId){
        return studentsMapper.checkByStudentId(studentId);
    }
}
