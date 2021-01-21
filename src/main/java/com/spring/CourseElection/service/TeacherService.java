package com.spring.CourseElection.service;

import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.response.Result;

/**
* @program: TeacherService
* @Description: 教师接口
* @Author: SoCMo
* @Date: 2021/1/21
*/
public interface TeacherService {
    /**
    * @program: TeacherService
    * @Description: 查询自己能管理的课程
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    public Result list();
}
