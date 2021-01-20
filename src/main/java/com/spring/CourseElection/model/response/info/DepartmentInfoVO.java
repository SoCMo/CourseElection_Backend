package com.spring.CourseElection.model.response.info;

import lombok.Data;

/**
 * program: DepartmentInfoVO
 * description: 部门信息展示
 * author: SoCMo
 * create: 2019/12/6 20:54
 */
@Data
public class DepartmentInfoVO {
    private Integer id;

    private String departmentName;

    private String email;

    private String telphone;
}
