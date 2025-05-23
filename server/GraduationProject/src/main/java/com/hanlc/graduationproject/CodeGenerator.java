package com.hanlc.graduationproject;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/bsdesign" +
                        "?useUnicode=true&characterEncoding=utf-8" +
                        "&useSSL=true&serverTimezone=Asia/Shanghai", "root", "password")
                .globalConfig(builder -> {
                    builder.author("Hanlc") // 设置作者
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir("D://EveryFile//CodeGenerator//GraduationProject"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com") // 设置父包名
                                .moduleName("hanlc") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://EveryFile//CodeGenerator//GraduationProject")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("teachers",
                                "teacher_courses",
                                "students",
                                "student_courses",
                                "messages",
                                "courses",
                                "course_schedules",
                                "classes",
                                "check_in_tasks",
                                "check_in_records") // 设置需要生成的表名
                                //.addTablePrefix("t_", "c_") // 设置过滤表前缀

                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
