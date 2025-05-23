package com.hanlc.graduationproject.service.impl;


import com.hanlc.graduationproject.entity.domain.Classes;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanlc.graduationproject.mapper.ClassesMapper;
import com.hanlc.graduationproject.service.ClassesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes> implements ClassesService {

}
