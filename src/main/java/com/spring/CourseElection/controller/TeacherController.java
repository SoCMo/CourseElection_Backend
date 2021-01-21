package com.spring.CourseElection.controller;

import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * program: TeacherController
 * description: 教师接口
 * author: SoCMo
 * create: 2021/1/21 10:10
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher")
@Api(tags = "教师接口")
@Slf4j
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/list")
    @ApiOperation(value = "获取课程列表")
    public Result list(){
        return teacherService.list();
    }
}
