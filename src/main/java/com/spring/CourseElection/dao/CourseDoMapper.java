package com.spring.CourseElection.dao;

import com.spring.CourseElection.model.entity.CourseDo;
import com.spring.CourseElection.model.entity.CourseDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseDoMapper {
    int countByExample(CourseDoExample example);

    int deleteByExample(CourseDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CourseDo record);

    int insertSelective(CourseDo record);

    List<CourseDo> selectByExample(CourseDoExample example);

    CourseDo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CourseDo record, @Param("example") CourseDoExample example);

    int updateByExample(@Param("record") CourseDo record, @Param("example") CourseDoExample example);

    int updateByPrimaryKeySelective(CourseDo record);

    int updateByPrimaryKey(CourseDo record);
}