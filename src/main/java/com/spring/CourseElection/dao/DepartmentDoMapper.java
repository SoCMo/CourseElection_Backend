package com.spring.CourseElection.dao;

import com.spring.CourseElection.model.entity.DepartmentDo;
import com.spring.CourseElection.model.entity.DepartmentDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentDoMapper {
    int countByExample(DepartmentDoExample example);

    int deleteByExample(DepartmentDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentDo record);

    int insertSelective(DepartmentDo record);

    List<DepartmentDo> selectByExample(DepartmentDoExample example);

    DepartmentDo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DepartmentDo record, @Param("example") DepartmentDoExample example);

    int updateByExample(@Param("record") DepartmentDo record, @Param("example") DepartmentDoExample example);

    int updateByPrimaryKeySelective(DepartmentDo record);

    int updateByPrimaryKey(DepartmentDo record);
}