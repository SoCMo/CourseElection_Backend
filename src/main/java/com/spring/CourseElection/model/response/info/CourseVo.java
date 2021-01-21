package com.spring.CourseElection.model.response.info;

import lombok.Data;

@Data
public class CourseVo {
    private Integer id;

    private String name;

    private String teacherId;

    private String teacherName;

    private Integer credit;

    private String address;

    private String courseTime;

    private Integer capacity;

    private Integer electionNum;
}