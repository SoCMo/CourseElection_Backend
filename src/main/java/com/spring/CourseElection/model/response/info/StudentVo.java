package com.spring.CourseElection.model.response.info;

import lombok.Data;

/**
 * program: UserVo
 * description: 学生视图
 * author: SoCMo
 * create: 2021/1/21 16:51
 */
@Data
public class StudentVo {
    private String userId;

    private String name;

    private Integer gender;

    private String department;

    private Double grade;

    private Double usual;

    private Double examination;
}
