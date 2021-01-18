package com.spring.CourseElection.model.response;

import lombok.Data;

/**
 * @description: 通用返回
 * @author: 0GGmr0
 * @create: 2019-12-01 21:23
 */
@Data
public class Result<T> {
    /**
     * 标识码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
}
