package com.spring.CourseElection.dao;

import com.spring.CourseElection.model.entity.ElectionDo;
import com.spring.CourseElection.model.entity.ElectionDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ElectionDoMapper {
    int countByExample(ElectionDoExample example);

    int deleteByExample(ElectionDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ElectionDo record);

    int insertSelective(ElectionDo record);

    List<ElectionDo> selectByExample(ElectionDoExample example);

    ElectionDo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ElectionDo record, @Param("example") ElectionDoExample example);

    int updateByExample(@Param("record") ElectionDo record, @Param("example") ElectionDoExample example);

    int updateByPrimaryKeySelective(ElectionDo record);

    int updateByPrimaryKey(ElectionDo record);

    @Update("UPDATE `election_info` set `grade` = (`usual` * #{proportion} + " +
            " `examination` * (100 - #{proportion})) / 100.0" +
            " WHERE `course_id` = #{courseId}")
    int refresh(@Param("proportion") Integer proportion,
                @Param("courseId") Integer courseId);
}