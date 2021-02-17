package com.spring.CourseElection.model.response.info;

import lombok.Data;

import java.util.List;

/**
 * program: CourseDetail
 * description: 课程详情
 * author: SoCMo
 * create: 2021/1/21 16:50
 */
@Data
public class CourseDetail {
    private CourseVo courseVo;

    private List<StudentVo> studentVoList;
}
