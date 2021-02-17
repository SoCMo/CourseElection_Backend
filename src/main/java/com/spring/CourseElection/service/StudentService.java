package com.spring.CourseElection.service;

import com.spring.CourseElection.model.response.Result;

/**
* @program: StudentService
* @Description: 学生接口
* @Author: SoCMo
* @Date: 2021/1/22
*/
public interface StudentService {
    /**
    * @Description: 获取课程列表
    * @Param: []
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/22
    */
    Result list();

    /**
    * @Description: 选择课程
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/22
    */
    Result choose(Integer id);

    /**
    * @Description: 退课
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/23
    */
    Result drop(Integer id);
}
