package com.spring.CourseElection.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description: 创建会议室请求体
 * @Author: SoCMo
 * @Date: 2019/12/4
 */
@Data
@ApiModel(value = "课程创建请求体", description = "课程创建请求体")
public class CourseCreateInfo {
    @NotBlank(message = "课程名不能为空")
    @ApiModelProperty(value = "课程名", required = true, example = "数据库原理(1)")
    private String name;

    @NotNull(message = "课程学分不能为空")
    @Min(value = 0, message = "学分至少为0")
    @ApiModelProperty(value = "学分", required = true, example = "1")
    private Integer credit;

    @NotBlank(message = "上课地点不能为空")
    @ApiModelProperty(value = "上课地点", required = true, example = "A315")
    private String address;

    @NotBlank(message = "上课时间不能为空")
    @Pattern(regexp = "^([一二三四五六七]:\\d(-\\d)?;)*?[一二三四五六七]:\\d(-\\d)?;?$", message = "请按格式填写上课时间")
    @ApiModelProperty(value = "上课时间", required = true, example = "二:2-4;五:7-8;")
    private String courseTime;

    @NotNull(message = "课程人数容量不能为空")
    @Min(value = 0, message = "课程容量至少为0")
    private Integer capacity;
}
