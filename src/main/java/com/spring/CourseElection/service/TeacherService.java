package com.spring.CourseElection.service;

import com.spring.CourseElection.model.request.CourseCreateInfo;
import com.spring.CourseElection.model.request.CourseUpdateInfo;
import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.request.StudentDelReq;
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
    Result list();

    /**
    * @Description: 创建课程
    * @Param: [courseCreateInfo]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    Result creation(CourseCreateInfo courseCreateInfo);

    /**
    * @Description: 删除课程
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    Result deletion(Integer id);

    /**
    * @Description: 更新课程信息
    * @Param: [courseUpdateInfo]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/23
    */
    Result update(CourseUpdateInfo courseUpdateInfo);

    /**
    * @Description: 获取课程学生列表
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/2/17
    */
    Result studentList(Integer id);

    /**
    * @Description: 从课程中删除学生
    * @Param: [studentDelReq]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/2/17
    */
    Result studentDel(StudentDelReq studentDelReq);
}
