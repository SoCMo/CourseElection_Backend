package com.spring.CourseElection.controller;

import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description: 异常处理接收
 * @author: 0GGmr0
 * @create: 2019-12-01 21:30
 */
@Slf4j
@CrossOrigin
@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handler(Exception ex) {
        Result result = new Result();
        if (ex instanceof AllException) {
            AllException exception = (AllException) ex;
            result.setCode(exception.getErrCode());
            result.setMessage(exception.getMsg());
        } else if (ex instanceof MethodArgumentNotValidException) {
            // 参数校验失败
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            BindingResult res1 = exception.getBindingResult();
            final List<FieldError> fieldErrors = res1.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : fieldErrors) {
                builder.append(error.getDefaultMessage()).append("\n");
            }
            result.setMessage(builder.toString());
            result.setCode(EmAllException.BAD_REQUEST.getErrCode());
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException exception = (HttpMessageNotReadableException) ex;
            if (Objects.requireNonNull(exception.getMessage()).contains("java.time.LocalDateTime")) {
                result.setMessage("时间格式有误");
            } else {
                result.setMessage(exception.getMessage());
            }
            result.setCode(EmAllException.BAD_REQUEST.getErrCode());
        } else {
            // 未知错误
            result.setCode(500);
            result.setMessage(ex.getMessage());
        }
        return result;
    }
}