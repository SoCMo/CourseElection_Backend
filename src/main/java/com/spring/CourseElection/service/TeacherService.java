package com.spring.CourseElection.service;

import com.spring.CourseElection.model.request.CourseCreateInfo;
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

    /**
    * @Description: 创建课程
    * @Param: [courseCreateInfo]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    public Result creation(CourseCreateInfo courseCreateInfo);

    /**
    * @Description: 删除课程
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    public Result deletion(Integer id);
}
