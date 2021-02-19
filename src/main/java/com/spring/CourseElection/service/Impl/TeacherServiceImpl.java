package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.CourseDoMapper;
import com.spring.CourseElection.dao.ElectionDoMapper;
import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.*;
import com.spring.CourseElection.model.request.CourseCreateInfo;
import com.spring.CourseElection.model.request.CourseUpdateInfo;
import com.spring.CourseElection.model.request.StudentDelReq;
import com.spring.CourseElection.model.request.StudentUpReq;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.model.response.info.CourseVo;
import com.spring.CourseElection.model.response.info.GradeListRes;
import com.spring.CourseElection.model.response.info.GradeRes;
import com.spring.CourseElection.service.TeacherService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.ResultTool;
import com.spring.CourseElection.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

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
    private UserDoMapper userDoMapper;

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

    @Override
    public Result update(CourseUpdateInfo courseUpdateInfo) {
        CourseDo courseDoOlder = courseDoMapper.selectByPrimaryKey(courseUpdateInfo.getId());
        if(courseDoOlder == null){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "该课程不存在");
        }

        if(!courseDoOlder.getTeacherId().equals(authTool.getUserId())){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "只能更改自己的课程信息");
        }

        CourseDo courseDo = new CourseDo();
        BeanUtils.copyProperties(courseUpdateInfo, courseDo);
        courseDo.setCourseTime(TimeTool.saveTime(courseUpdateInfo.getCourseTime()));
        if(courseDoMapper.updateByPrimaryKeySelective(courseDo) == 1){
            return ResultTool.success();
        }

        return ResultTool.error(EmAllException.DATABASE_ERROR);
    }

    @Override
    public Result studentList(Integer id) {
        try{
            CourseDo courseDo = courseDoMapper.selectByPrimaryKey(id);
            if(courseDo == null){
                return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "该课程不存在");
            }

            if(!courseDo.getTeacherId().equals(authTool.getUserId())){
                return ResultTool.error(EmAllException.REQUEST_FORBIDDEN.getErrCode(), "仅能查询自己课程的学生");
            }

            ElectionDoExample electionDoExample = new ElectionDoExample();
            electionDoExample.createCriteria()
                    .andCourseIdEqualTo(id);
            List<ElectionDo> electionDoList = electionDoMapper.selectByExample(electionDoExample);
            if(electionDoList.isEmpty()){
                return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "未有学生加入课程");
            }

            List<GradeRes> gradeResList = new ArrayList<>();
            for(ElectionDo electionDo : electionDoList){
                GradeRes gradeRes = new GradeRes();
                BeanUtils.copyProperties(electionDo, gradeRes);

                UserDo userDo = userDoMapper.selectByPrimaryKey(electionDo.getStudentId());
                if(userDo == null) throw new AllException(EmAllException.DATABASE_ERROR);
                BeanUtils.copyProperties(userDo, gradeRes);

                gradeResList.add(gradeRes);
            }

            GradeListRes gradeListRes = new GradeListRes();
            gradeListRes.setGradeResList(gradeResList);
            gradeListRes.setProportion(courseDo.getProportion());

            return ResultTool.success(gradeListRes);
        }catch (AllException e){
            log.error(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }

    @Override
    public Result studentDel(StudentDelReq studentDelReq) {
        CourseDo courseDo = courseDoMapper.selectByPrimaryKey(studentDelReq.getCourseId());
        if(courseDo == null){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "该课程不存在");
        }

        if(!courseDo.getTeacherId().equals(authTool.getUserId())){
            return ResultTool.error(EmAllException.REQUEST_FORBIDDEN.getErrCode(), "只能删除自己课程的学生");
        }

        ElectionDoExample electionDoExample = new ElectionDoExample();
        electionDoExample.createCriteria()
                .andCourseIdEqualTo(studentDelReq.getCourseId())
                .andStudentIdEqualTo(studentDelReq.getUserId());
        if(electionDoMapper.deleteByExample(electionDoExample) > 0){
            return ResultTool.success();
        }else return ResultTool.error(EmAllException.DATABASE_ERROR);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result studentUp(StudentUpReq studentUpReq) {
        try {
            CourseDo courseDo = courseDoMapper.selectByPrimaryKey(studentUpReq.getCourseId());
            if(courseDo == null){
                return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "该课程不存在");
            }

            if(!courseDo.getTeacherId().equals(authTool.getUserId())){
                return ResultTool.error(EmAllException.REQUEST_FORBIDDEN.getErrCode(), "只能删除自己课程的学生");
            }

            ElectionDoExample electionDoExample = new ElectionDoExample();
            electionDoExample.createCriteria()
                    .andCourseIdEqualTo(studentUpReq.getCourseId())
                    .andStudentIdEqualTo(studentUpReq.getUserId());

            ElectionDo electionDo = new ElectionDo();
            electionDo.setUsual(studentUpReq.getUsual());
            electionDo.setExamination(studentUpReq.getExamination());
            electionDo.setGrade(
                    studentUpReq.getUsual().doubleValue()
                            * studentUpReq.getProportion() +
                            studentUpReq.getExamination().doubleValue()
                                    * (1 - studentUpReq.getProportion())
            );
            if(electionDoMapper.updateByExampleSelective(electionDo, electionDoExample) == 1){
                if(!courseDo.getProportion().equals(studentUpReq.getProportion())){
                    courseDo.setProportion(studentUpReq.getProportion());
                    if(courseDoMapper.updateByPrimaryKeySelective(courseDo) != 1){
                        throw new AllException(EmAllException.DATABASE_ERROR);
                    }

                    if(electionDoMapper.refresh(studentUpReq.getProportion(), courseDo.getId()) < 1){
                        throw new AllException(EmAllException.DATABASE_ERROR);
                    }
                }

                return ResultTool.success();
            }else {
                return ResultTool.error(EmAllException.DATABASE_ERROR);
            }
        } catch (AllException e) {
            log.error(e.getMsg());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }
}
