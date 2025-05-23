package com.hanlc.graduationproject.entity.request;

import lombok.Data;

@Data
public class StudentInfoRequest {

    private String studentNumber;
    private String name;
    private String department;
    private String major;
}
