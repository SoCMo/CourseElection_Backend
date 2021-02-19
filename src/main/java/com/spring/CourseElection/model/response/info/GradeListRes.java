package com.spring.CourseElection.model.response.info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @program: GradeListRes
* @Description: 学生成绩返回列表
* @Author: SoCMo
* @Date: 2021/2/19
*/
@Data
@NoArgsConstructor
public class GradeListRes {
    private List<GradeRes> gradeResList;

    private Integer proportion;
}
