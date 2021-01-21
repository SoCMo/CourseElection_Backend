package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.CourseDoMapper;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.CourseDo;
import com.spring.CourseElection.model.entity.CourseDoExample;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.model.response.info.CourseVo;
import com.spring.CourseElection.service.TeacherService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.ResultTool;
import com.spring.CourseElection.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @program: TeacherServiceImpl
* @Description: 教师接口
* @Author: SoCMo
* @Date: 2021/1/21
*/
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private CourseDoMapper courseDoMapper;

    @Resource
    private AuthTool authTool;

    @Override
    public Result list() {
        CourseDoExample courseDoExample = new CourseDoExample();
        courseDoExample.createCriteria()
                .andTeacherIdEqualTo(authTool.getUserId());
        List<CourseDo> courseDoList
                = courseDoMapper.selectByExample(courseDoExample);

        if(courseDoList == null || courseDoList.size() == 0){
            return ResultTool.error(EmAllException.DATABASE_ERROR.getErrCode(),
                    "您当前没有可管理的课程");
        }

        List<CourseVo> courseVoList = new ArrayList<>();
        for(CourseDo courseDo: courseDoList){
            CourseVo courseVo = new CourseVo();
            BeanUtils.copyProperties(courseDo, courseVo);
            courseVo.setCourseTime(TimeTool.);
        }
    }
}
