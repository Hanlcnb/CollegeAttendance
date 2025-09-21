package com.hanlc.attendence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanlc.attendence.entity.domain.Teachers;
import com.hanlc.attendence.entity.request.TeacherInfoRequest;

/**
 * <p>
 * 教师信息表 服务类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public interface TeachersService extends IService<Teachers> {

    /**
     * 根据openId查询教师信息
     *
     * @param openId 微信openId
     * @return 教师信息
     */
    TeacherInfoRequest getTeacherInfo(String openId);
} 