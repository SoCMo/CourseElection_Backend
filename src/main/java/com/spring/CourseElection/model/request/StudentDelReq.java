package com.spring.CourseElection.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @program: StudentDelReq
* @Description: 从课程中删除学生请求体
* @Author: SoCMo
* @Date: 2021/2/17
*/
@Data
@ApiModel(value = "从课程中删除学生请求体", description = "从课程中删除学生请求体")
public class StudentDelReq {
    @NotBlank(message = "学生学号不能为空")
    @ApiModelProperty(value = "学号", required = true, example = "18120001")
    private String userId;

    @NotNull(message = "课程号不能为空")
    @ApiModelProperty(value = "课程号", required = true, example = "1")
    private Integer courseId;
}
