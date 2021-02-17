package com.spring.CourseElection.controller;

import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.service.StudentService;
import com.spring.CourseElection.tools.ResultTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/student")
@Api(tags = "学生接口")
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/list")
    @ApiOperation(value = "获取课程列表")
    public Result list(){
        return studentService.list();
    }

    @GetMapping("/choice")
    @ApiOperation(value = "选择课程")
    public Result choose(Integer id) {
        return studentService.choose(id);
    }

    @GetMapping("/dropping")
    @ApiOperation(value = "退课")
    public Result dropping(Integer id){
        return studentService.drop(id);
    }
}
