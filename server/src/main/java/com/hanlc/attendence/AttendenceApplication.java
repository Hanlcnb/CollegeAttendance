package com.hanlc.attendence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hanlc.attendence.mapper")
public class AttendenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendenceApplication.class, args);
    }

}
