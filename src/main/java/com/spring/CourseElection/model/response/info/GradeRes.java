package com.spring.CourseElection.model.response.info;

import lombok.Data;

/**
* @program: GradeRes
* @Description: 学生成绩信息返回
* @Author: SoCMo
* @Date: 2021/2/17
*/
@Data
public class GradeRes {
    private String userId;

    private Integer courseId;

    private String courseName;

    private String name;

    private String department;

    private String email;

    private Double grade;

    private Integer usual;

    private Integer examination;

    private Integer proportion;

    private Integer credit;
}
