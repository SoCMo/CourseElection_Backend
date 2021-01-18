package com.spring.CourseElection.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: LoginInfo
 * @Description: 用户登录请求信息
 * @Author: SoCMo
 * @Date: 2020/12/21
 */
@Data
@ApiModel(value = "用户登录请求体", description = "用户登录请求体")
public class LoginInfo {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true, example = "18120198")
    private String userId;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true, example = "12345678")
    private String password;
//    @NotBlank(message = "Code不能为空")
//    private String Code;
//
//    @NotBlank(message = "url不能为空")
//    private String url;
}
