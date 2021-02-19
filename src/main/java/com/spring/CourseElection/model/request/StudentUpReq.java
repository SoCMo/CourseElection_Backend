package com.spring.CourseElection.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* @program: StudentUpReq
* @Description: 学生成绩更新请求体
* @Author: SoCMo
* @Date: 2021/2/19
*/
@Data
@ApiModel(value = "学生成绩更新请求体", description = "学生成绩更新请求体")
public class StudentUpReq {
    @NotBlank(message = "学生id不能为空")
    @ApiModelProperty(value = "学生id", required = true, example = "1812xxxx")
    private String userId;

    @NotNull(message = "课程id不能为空")
    @ApiModelProperty(value = "课程号", required = true, example = "1")
    private Integer courseId;

    @NotNull(message = "平时成绩不能为空")
    @Range(min = 0, max = 100, message = "平时成绩范围应在[0,100]中")
    @ApiModelProperty(value = "平时成绩", required = true, example = "1")
    private Integer usual;

    @NotNull(message = "考试成绩不能为空")
    @Range(min = 0, max = 100, message = "考试成绩范围应在[0,100]中")
    @ApiModelProperty(value = "考试成绩", required = true, example = "1")
    private Integer examination;

    @NotNull(message = "成绩占比不能为空")
    @Range(min = 0, max = 100, message = "成绩占比范围应在[0,100]中")
    @ApiModelProperty(value = "成绩占比", required = true, example = "60")
    private Integer proportion;
}
