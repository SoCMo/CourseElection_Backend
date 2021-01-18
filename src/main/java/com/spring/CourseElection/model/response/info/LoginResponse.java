package com.spring.CourseElection.model.response.info;

import lombok.Data;

/**
 * @description: 登录请求返回内容
 * @author: 0GGmr0
 * @create: 2019-12-01 21:34
 */
@Data
public class LoginResponse {
    private String token;
    private String userId;
    private String name;
    private String[] identity;
    private String department;
}
