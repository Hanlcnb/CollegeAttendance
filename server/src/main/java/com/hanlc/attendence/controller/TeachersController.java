package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.entity.domain.Teachers;
import com.hanlc.attendence.entity.request.TeacherInfoRequest;
import com.hanlc.attendence.service.impl.TeachersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 教师信息表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/teachers")
public class TeachersController {

    @Autowired
    private TeachersServiceImpl teachersService;

    /**
     * 根据openId查询教师信息
     *
     * @param userid UID
     * @return 教师信息
     */
    @GetMapping("/teacherInfo")
    public Result teacherInfo(@RequestParam String userid) {
        TeacherInfoRequest request = teachersService.getTeacherInfo(userid);
        if (request == null) {
            return Result.notFound();
        }
        return Result.success("成功获取教师信息", request);
    }

    /**
     * 添加教师信息
     *
     * @param teacher 教师信息
     * @return 操作结果
     */
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teachers teacher) {
        if (teacher == null || teacher.getId().isEmpty()) {
            return Result.error("教师信息不完整");
        }
        return teachersService.addTeacher(teacher);
    }

    /**
     * 更新教师信息
     *
     * @param teacher 教师信息
     * @return 操作结果
     */
    @PutMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teachers teacher) {
        if (teacher == null || teacher.getId().isEmpty()) {
            return Result.error("教师信息不完整");
        }
        return teachersService.updateTeacher(teacher);
    }

    /**
     * 删除教师信息
     *
     * @param openId 教师openId
     * @return 操作结果
     */
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestParam String openId) {
        if (openId == null || openId.isEmpty()) {
            return Result.error("openId不能为空");
        }
        return teachersService.deleteTeacher(openId);
    }
}
