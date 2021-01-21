package com.spring.CourseElection.controller;

import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.request.CourseCreateInfo;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.service.TeacherService;
import com.spring.CourseElection.tools.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/creation")
    @ApiOperation(value = "获取课程列表")
    public Result createConference(@Validated @RequestBody CourseCreateInfo courseCreateInfo){
        return teacherService.creation(courseCreateInfo);
    }

    @GetMapping("/deletion")
    @ApiOperation(value = "删除课程")
    public Result deletion(Integer id){
        if(id <= 0 ) return ResultTool.error(EmAllException.BAD_REQUEST);
        return teacherService.deletion(id);
    }
}
