package com.spring.CourseElection.dao;

import com.spring.CourseElection.model.entity.PasswordDo;
import com.spring.CourseElection.model.entity.PasswordDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PasswordDoMapper {
    int countByExample(PasswordDoExample example);

    int deleteByExample(PasswordDoExample example);

    int deleteByPrimaryKey(String userId);

    int insert(PasswordDo record);

    int insertSelective(PasswordDo record);

    List<PasswordDo> selectByExample(PasswordDoExample example);

    PasswordDo selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") PasswordDo record, @Param("example") PasswordDoExample example);

    int updateByExample(@Param("record") PasswordDo record, @Param("example") PasswordDoExample example);

    int updateByPrimaryKeySelective(PasswordDo record);

    int updateByPrimaryKey(PasswordDo record);
}