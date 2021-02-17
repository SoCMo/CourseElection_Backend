package com.spring.CourseElection.model.response.info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @program: StudentListRes
* @Description: 学生查询课程列表返回
* @Author: SoCMo
* @Date: 2021/1/23
*/
@Data
@NoArgsConstructor
public class StudentListRes {
    List<CourseVo> courseVoList;

    List<Integer> chosenList;
}
