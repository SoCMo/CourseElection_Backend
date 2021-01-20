package com.spring.CourseElection.service;

import com.spring.CourseElection.model.entity.UserDo;
import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.request.UserUpdateInfo;
import com.spring.CourseElection.model.response.Result;

/**
 * @program: UserService
 * @Description: 普通用户接口
 * @Author: SoCMo
 * @Date: 2020/12/21
 */
public interface UserService {
    /**
     * @Description: 登录接口
     * @Param: [loginInfo]
     * @Return: com.shu.TechEthics.model.response.Result
     * @Author: SoCMo
     * @Date: 2020/12/21
     */
    Result login(LoginInfo loginInfo);

    /**
    * @Description: 获取个人信息
    * @Param: [id]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/20
    */
    Result info(String userId);

    /**
    * @Description: 获取部门信息
    * @Param: [userDo]
    * @Return: com.spring.CourseElection.model.response.Result
    * @Author: SoCMo
    * @Date: 2021/1/20
    */
    Result departmentInfo();

    /**
     * @Description: 个人信息更新
     * @Param: [userUpdateInfo, user]
     * @Return: com.meeting.model.response.Result
     * @Author: SoCMo
     * @Date: 2019/12/6
     */
    public Result userInfoUpdate(UserUpdateInfo userUpdateInfo);

}
