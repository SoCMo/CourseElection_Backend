package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.CourseDoMapper;
import com.spring.CourseElection.dao.ElectionDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.*;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.model.response.info.CourseVo;
import com.spring.CourseElection.model.response.info.GradeRes;
import com.spring.CourseElection.model.response.info.StudentListRes;
import com.spring.CourseElection.service.StudentService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.ResultTool;
import com.spring.CourseElection.tools.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @program: StudentServiceImpl
* @Description: 学生接口
* @Author: SoCMo
* @Date: 2021/1/22
*/
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private CourseDoMapper courseDoMapper;

    @Resource
    private ElectionDoMapper electionDoMapper;

    @Resource
    private AuthTool authTool;

    @Override
    public Result list() {
        CourseDoExample courseDoExample = new CourseDoExample();
        List<CourseDo> courseDoList =
                courseDoMapper.selectByExample(courseDoExample);

        if(courseDoList == null || courseDoList.size() == 0){
            return ResultTool.error(EmAllException.DATABASE_ERROR.getErrCode(),
                    "当前没有可选的课程");
        }

        StudentListRes studentListRes = new StudentListRes();
        List<CourseVo> courseVoList = new ArrayList<>();
        for(CourseDo courseDo: courseDoList){
            CourseVo courseVo = new CourseVo();
            BeanUtils.copyProperties(courseDo, courseVo);
            courseVo.setCourseTime(TimeTool.loadTime(courseDo.getCourseTime()));
            courseVoList.add(courseVo);
        }

        ElectionDoExample electionDoExample = new ElectionDoExample();
        electionDoExample.createCriteria()
                .andStudentIdEqualTo(authTool.getUserId());

        List<ElectionDo> electionDoList = electionDoMapper.selectByExample(electionDoExample);
        if(electionDoList.isEmpty()){
            studentListRes.setChosenList(new ArrayList<>());
            studentListRes.setHasGradeList(new ArrayList<>());
            return ResultTool.success(studentListRes);
        }

        studentListRes.setChosenList(
                electionDoList.stream().map(ElectionDo::getCourseId)
                        .collect(Collectors.toList())
        );

        UserDo userDo;
        try {
            userDo = authTool.getUser();
        } catch (AllException e) {
            return ResultTool.error(EmAllException.DATABASE_ERROR);
        }

        List<GradeRes> gradeResList = new ArrayList<>();
        electionDoList.forEach(
                electionDo -> {
                    GradeRes gradeRes = new GradeRes();
                    String teacherName = "";
                    for(CourseDo courseDo: courseDoList){
                        if(courseDo.getId().equals(electionDo.getCourseId())){
                            gradeRes.setProportion(courseDo.getProportion());
                            gradeRes.setCredit(courseDo.getCredit());
                            gradeRes.setCourseName(courseDo.getName());
                            teacherName = courseDo.getTeacherName();
                            break;
                        }
                    }
                    BeanUtils.copyProperties(userDo, gradeRes);
                    BeanUtils.copyProperties(electionDo, gradeRes);
                    gradeRes.setName(teacherName);
                    gradeResList.add(gradeRes);
                }
        );
        studentListRes.setHasGradeList(gradeResList);
        studentListRes.setCourseVoList(courseVoList);
        return ResultTool.success(studentListRes);
    }

    @Override
    public Result choose(Integer id) {
        try{
            ElectionDo electionDo = new ElectionDo();
            UserDo user = authTool.getUser();

            electionDo.setStudentId(user.getUserId());
            electionDo.setStudentName(user.getName());

            CourseDo courseDo = courseDoMapper.selectByPrimaryKey(id);
            if(courseDo == null){
                return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "该课程不存在");
            }

            ElectionDoExample electionDoExample = new ElectionDoExample();
            electionDoExample.createCriteria()
                    .andStudentIdEqualTo(user.getUserId());
            List<ElectionDo> electionDoList = electionDoMapper.selectByExample(electionDoExample);
            if(electionDoList != null && !electionDoList.isEmpty()){
                StringBuilder timeRange = null;
                for(ElectionDo temp: electionDoList){
                    for(int i = 0; i < temp.getCourseTime().length(); i++){
                        if(timeRange == null){
                            timeRange = new StringBuilder(temp.getCourseTime());
                            break;
                        }else {
                            if(temp.getCourseTime().charAt(i) == '1'){
                                if(timeRange.charAt(i) == '1'){
                                    return ResultTool.error(EmAllException.DATABASE_ERROR.getErrCode(), "您课程中存在重复!");
                                }else {
                                    timeRange.setCharAt(i, '1');
                                }
                            }
                        }
                    }
                }

                for(int i = 0; i < courseDo.getCourseTime().length(); i++){
                    if(courseDo.getCourseTime().charAt(i) == '1'){
                        if(timeRange.charAt(i) == '1'){
                            return ResultTool.error(EmAllException.DATABASE_ERROR.getErrCode(), "该课程存在时间冲突!");
                        }
                    }
                }
            }

            electionDo.setCourseId(courseDo.getId());
            electionDo.setCourseName(courseDo.getName());
            electionDo.setCourseTime(courseDo.getCourseTime());
            if(electionDoMapper.insertSelective(electionDo) == 1){
                CourseDo courseDoUpdate = new CourseDo();
                courseDoUpdate.setId(courseDo.getId());
                courseDoUpdate.setElectionNum(courseDo.getElectionNum() + 1);
                if(courseDoMapper.updateByPrimaryKeySelective(courseDoUpdate) == 1){
                    return ResultTool.success();
                }
            }

            return ResultTool.error(EmAllException.DATABASE_ERROR);
        } catch (AllException e) {
            log.error(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }

    @Override
    public Result drop(Integer id) {
        ElectionDoExample electionDoExample = new ElectionDoExample();
        electionDoExample.createCriteria()
                .andCourseIdEqualTo(id)
                .andStudentIdEqualTo(authTool.getUserId());
        List<ElectionDo> electionDoList = electionDoMapper.selectByExample(electionDoExample);
        if(electionDoList == null || electionDoList.isEmpty()){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "你没有选课");
        }

        ElectionDo electionDo = electionDoList.get(0);

        if(electionDo.getUsual() != null ||
        electionDo.getExamination() != null ||
        electionDo.getGrade() != null){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "不能退选已有成绩的课程");
        }

        if(!electionDo.getStudentId().equals(authTool.getUserId())){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "只能退选自己的课程");
        }

        if(electionDoMapper.deleteByPrimaryKey(electionDo.getId()) == 1){
            CourseDo courseDo = courseDoMapper.selectByPrimaryKey(electionDo.getCourseId());
            courseDo.setElectionNum(courseDo.getElectionNum() - 1);
            if(courseDoMapper.updateByPrimaryKeySelective(courseDo) == 1){
                return ResultTool.success();
            }
        }

        return ResultTool.error(EmAllException.DATABASE_ERROR);
    }

    @Override
    public Result grade() {
        CourseDoExample courseDoExample = new CourseDoExample();
        List<CourseDo> courseDoList =
                courseDoMapper.selectByExample(courseDoExample);

        if(courseDoList == null || courseDoList.size() == 0){
            return ResultTool.error(EmAllException.DATABASE_ERROR.getErrCode(),
                    "当前没有课程");
        }

        ElectionDoExample electionDoExample = new ElectionDoExample();
        electionDoExample.createCriteria()
                .andStudentIdEqualTo(authTool.getUserId());

        List<ElectionDo> electionDoList = electionDoMapper.selectByExample(electionDoExample);
        if(electionDoList.isEmpty()){
            return ResultTool.error(EmAllException.BAD_REQUEST.getErrCode(), "当前没有选择的课程");
        }

        UserDo userDo;
        try {
            userDo = authTool.getUser();
        } catch (AllException e) {
            return ResultTool.error(EmAllException.DATABASE_ERROR);
        }

        List<GradeRes> gradeResList = new ArrayList<>();
        electionDoList.forEach(
                electionDo -> {
                    GradeRes gradeRes = new GradeRes();
                    for(CourseDo courseDo: courseDoList){
                        if(courseDo.getId().equals(electionDo.getCourseId())){
                            gradeRes.setProportion(courseDo.getProportion());
                            gradeRes.setCredit(courseDo.getCredit());
                            gradeRes.setCourseName(courseDo.getName());
                            break;
                        }
                    }
                    BeanUtils.copyProperties(userDo, gradeRes);
                    BeanUtils.copyProperties(electionDo, gradeRes);
                    gradeResList.add(gradeRes);
                }
        );
        return ResultTool.success(gradeResList);
    }
}
