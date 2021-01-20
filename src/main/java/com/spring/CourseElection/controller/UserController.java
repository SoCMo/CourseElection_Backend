package com.spring.CourseElection.controller;

import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.request.UserUpdateInfo;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.service.UserService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private AuthTool authTool;

    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "userId", value = "学工号", required = true, dataType = "String")
    public Result info(String userId) {
        if (StringUtils.isBlank(userId)) return ResultTool.error(EmAllException.BAD_REQUEST);
        return userService.info(userId);
    }

    @GetMapping("/departmentInfo")
    @ApiOperation(value = "获取部门信息")
    public Result DepartmentInfo(){
        return userService.departmentInfo();
    }

    @PostMapping("/userInfoUpdate")
    @ApiOperation(value = "更新个人信息")
    public Result UserInfoUpdate(@RequestBody UserUpdateInfo userUpdateInfo) {
        return userService.userInfoUpdate(userUpdateInfo);
    }
}
