package com.lc.compiler.global;

import com.lc.compiler.enums.ResponseCodeEnum;
import com.lc.compiler.util.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:16 PM
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResultData<String> handleException(Exception e) {

        log.error("", e);

        return ResultData.error("操作失败");
    }


    @ExceptionHandler(BindException.class)
    public ResultData<?> validationErrorHandler(BindException ex) {
        List<String> collect = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        collect.forEach(sb::append);

        log.error("", ex);

        return ResultData.builder()
                .code(ResponseCodeEnum.ERROR.getCode())
                .msg(sb.toString()).build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData<?> validationErrorHandler(MethodArgumentNotValidException ex) {
        List<String> collect = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        collect.forEach(e -> sb.append(e).append(" "));

        log.error("", ex);

        return ResultData.builder()
                .code(ResponseCodeEnum.ERROR.getCode())
                .msg(sb.toString()).build();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResultData<?> validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        errorInformation.forEach(sb::append);

        log.error("", ex);

        return ResultData.builder()
                .code(ResponseCodeEnum.ERROR.getCode())
                .msg(sb.toString()).build();
    }
}
