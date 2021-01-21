package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.CourseDoMapper;
import com.spring.CourseElection.dao.ElectionDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.CourseDo;
import com.spring.CourseElection.model.entity.CourseDoExample;
import com.spring.CourseElection.model.entity.ElectionDoExample;
import com.spring.CourseElection.model.entity.UserDo;
import com.spring.CourseElection.model.request.CourseCreateInfo;
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
    private ElectionDoMapper electionDoMapper;

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
            courseVo.setCourseTime(TimeTool.loadTime(courseDo.getCourseTime()));

            ElectionDoExample electionDoExample = new ElectionDoExample();
            electionDoExample.createCriteria()
                    .andCourseIdEqualTo(courseDo.getId());
            courseVo.setElectionNum(electionDoMapper.countByExample(electionDoExample));
            courseVoList.add(courseVo);
        }
        return ResultTool.success(courseVoList);
    }

    @Override
    public Result creation(CourseCreateInfo courseCreateInfo) {
        try {
            UserDo userDo = authTool.getUser();

            CourseDo courseDo = new CourseDo();
            BeanUtils.copyProperties(courseCreateInfo, courseDo);
            courseDo.setTeacherId(userDo.getUserId());
            courseDo.setTeacherName(userDo.getName());
            courseDo.setCourseTime(TimeTool.saveTime(courseCreateInfo.getCourseTime()));

            if(courseDoMapper.insertSelective(courseDo) == 1){
                return ResultTool.success();
            }else {
                return ResultTool.error(EmAllException.DATABASE_ERROR);
            }

        } catch (AllException e) {
            log.error(e.getMsg());
            return ResultTool.error(EmAllException.DATABASE_ERROR);
        }
    }

    @Override
    public Result deletion(Integer id) {
        CourseDo courseDo = courseDoMapper.selectByPrimaryKey(id);
        if(courseDo.getTeacherId().equals(authTool.getUserId())){
            if(courseDoMapper.deleteByPrimaryKey(id) == 1){
                return ResultTool.success();
            }else {
                return ResultTool.error(EmAllException.DATABASE_ERROR);
            }
        }else {
            return ResultTool.error(EmAllException.REQUEST_FORBIDDEN);
        }
    }
}
