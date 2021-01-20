package com.spring.CourseElection.model.request;

import lombok.Data;

import javax.validation.Valid;

/**
 * program: UserUpdateInfo
 * description: 个人信息更新
 * author: SoCMo
 * create: 2019/12/6 20:21
 */
@Data
public class UserUpdateInfo {
    private String phone;

    private String email;
}
