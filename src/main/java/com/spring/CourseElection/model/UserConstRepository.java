package com.spring.CourseElection.model;

/**
 * @description: 常量存储类
 * @author: 0GGmr0
 * @create: 2019-12-01 21:23
 */
public final class UserConstRepository {
    // TOKEN时间有效期
//    public final static int TEMPORARY_TOKEN_VALIDITY_MINUTE = 15; // 临时token的有效期---分钟
    public final static int NORMAL_TOKEN_VALIDITY_DAY = 5; // 正常token的有效期---天

    // TOKEN返回值
    public final static int SIGNATURE_VERIFICATION_EXCEPTION = -3; // token签名内容失效
    public final static int TOKEN_EXPIRED_EXCEPTION = -2; // token超时
    public final static int FAKE_TOKEN = -1; // 虚假token

//    public final static int TEMPORARY_TOKEN = 1; // 临时token
    public final static int NORMAL_TOKEN = 2; // 正常token

    //TOKEN_HEAD
    public final static String TOKEN_HEADER = "Bearer ";

    //Login_URL
    public static final String LOGIN_URL = "/login";

    //用户身份程序样式
    public final static String[] IDENTITY = {
            "ROLE_STUDENT",
            "ROLE_TEACHER",
            "ROLE_ADMIN"
    };

    //用户身份中文样式
    public final static String[] IDENTITY_CN = {
            "学生",
            "教师",
            "管理员"
    };

    private UserConstRepository() {
    }
}
