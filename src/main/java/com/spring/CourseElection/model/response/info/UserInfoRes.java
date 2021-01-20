package com.spring.CourseElection.model.response.info;

import lombok.Data;

import java.util.List;

/**
 * program: UserInfoRes
 * description: 个人信息返回体
 * author: SoCMo
 * create: 2021/1/4 22:20
 */
@Data
public class UserInfoRes {
    private String userId;

    private String name;

    private Integer gender;

    private String department;

    private String education;

    private List<String> identity;

    private String officePhone;

    private String mobilePhone;

    private String email;

    private Boolean pending;
}
