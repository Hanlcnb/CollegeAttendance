package com.hanlc.attendence.entity.request;

import lombok.Data;

@Data
public class UsersInfoRequest {

    private String id;

    private String username;

    private String realName;

    private String avatar;

    private String phone;

    private String email;

    private String role;

}
