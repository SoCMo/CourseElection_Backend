package com.spring.CourseElection.controller;

import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: LoginController
 * @Description: 登录接口
 * @Author: SoCMo
 * @Date: 2020/12/24
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
@Api(tags = "登录接口")
public class LoginController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "登录", notes = "输入参数为账号与密码")
    @PostMapping("")
    public Result login(@Validated @RequestBody LoginInfo loginInfo) {
        return userService.login(loginInfo);
    }
}
