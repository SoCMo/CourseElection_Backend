package com.spring.CourseElection.service;

import com.spring.CourseElection.model.request.LoginInfo;
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
}
