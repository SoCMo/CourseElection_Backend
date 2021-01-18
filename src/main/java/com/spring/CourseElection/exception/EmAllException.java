package com.spring.CourseElection.exception;

/**
 * @description: 报错枚举
 * @author: 0GGmr0
 * @create: 2019-12-01 21:27
 */
public enum EmAllException implements CommonError {

    NO_LOGIN_AUTHORIZATION(403, "没有登录权限"),

    IDENTITY_ERROR(403, "没有权限"),

    USER_AND_PASSWORD_ERROR(400, "输入密码账号或者密码错误"),

    TOKEN_PHASE_ERROR(500, "解析token出错"),

    BAD_REQUEST(400, "请求参数格式有误"),

    BAD_FILE_TYPE(400, "文件格式错误"),

    INSERT_ERROR(500, "插入数据失败"),

    USER_ALREADY_EXISTS(410, "用户已存在"),

    DATABASE_ERROR(500, "系统异常或数据有误");

    // 错误码
    private Integer code;

    // 错误信息
    private String msg;

    EmAllException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getErrCode() {
        return this.code;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.msg = errMsg;
        return this;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
