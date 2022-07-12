package com.lc.compiler.util;

import com.lc.compiler.enums.ResponseCodeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:11 PM
 */
@Builder
@Data
public class ResultData<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> ResultData<T> success(Integer code, String msg, T data) {
        return new ResultData<>(code, msg, data);
    }

    public static <T> ResultData<T> success(String msg, T data) {
        return new ResultData<>(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResultData<T> success(String msg) {
        return new ResultData<>(ResponseCodeEnum.SUCCESS.getCode(), msg, null);
    }


    public static <T> ResultData<T> error(Integer code, String msg, T data) {
        return new ResultData<>(code, msg, data);
    }

    public static <T> ResultData<T> error(String msg, T data) {
        return new ResultData<>(ResponseCodeEnum.ERROR.getCode(), msg, data);
    }

    public static <T> ResultData<T> error(String msg) {
        return new ResultData<>(ResponseCodeEnum.ERROR.getCode(), msg, null);
    }

    public static <T> ResultData<T> result(Integer code, String msg, T data) {
        return new ResultData<>(code, msg, data);
    }

    public ResultData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}

