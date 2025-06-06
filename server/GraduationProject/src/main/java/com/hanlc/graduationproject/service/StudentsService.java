package com.hanlc.graduationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.graduationproject.entity.domain.Students;
import com.hanlc.graduationproject.entity.request.StudentInfoRequest;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface StudentsService extends IService<Students> {

    public StudentInfoRequest getStudentInfo(String userOpenId);
}
