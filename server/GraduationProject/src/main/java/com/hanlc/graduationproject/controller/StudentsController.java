package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.Result;
import com.hanlc.graduationproject.entity.domain.Students;
import com.hanlc.graduationproject.entity.request.StudentInfoRequest;
import com.hanlc.graduationproject.service.impl.StudentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@RestController
@RequestMapping("/api/wechat/students")
public class StudentsController {

    @Autowired
    private StudentsServiceImpl studentsService;

    /**
     * 根据openId查询学生信息
     *
     * @param userId 用户id
     * @return 学生信息
     */
    @GetMapping("/studentInfo")
    public Result studentInfo(@RequestParam String userId) {
        StudentInfoRequest request = studentsService.getStudentInfo(userId);
        if (request == null) {
            return Result.notFound();
        }
        return Result.success("成功获取学生信息", request);
    }

    /**
     * 添加学生信息
     *
     * @param student 学生信息
     * @return 操作结果
     */
    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Students student) {
        if (student == null || student.getId().isEmpty()) {
            return Result.error("学生信息不能为空");
        }
        return studentsService.addStudent(student);
    }

    /**
     * 更新学生信息
     *
     * @param student 学生信息
     * @return 操作结果
     */
    @PutMapping("/updateStudent")
    public Result updateStudent(@RequestBody Students student) {
        if (student == null || student.getId().isEmpty()) {
            return Result.error("学生信息不完整");
        }
        return studentsService.updateStudent(student);
    }

    /**
     * 删除学生信息
     *
     * @param openId 学生openId
     * @return 操作结果
     */
    @DeleteMapping("/deleteStudent")
    public Result deleteStudent(@RequestParam String openId) {
        if (openId == null || openId.isEmpty()) {
            return Result.error("openId不能为空");
        }
        return studentsService.deleteStudent(openId);
    }
}
