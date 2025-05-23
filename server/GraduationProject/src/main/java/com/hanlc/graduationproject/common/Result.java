package com.hanlc.graduationproject.common;

import com.hanlc.graduationproject.entity.enums.ResultCode;
import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 返回消息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功返回结果
     *
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @return Result对象
     */
    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @return Result对象
     */
    public static <T> Result<T> error(ResultCode errorCode) {
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

    /**
     * 失败返回结果
     *
     * @param message 错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message 错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(ResultCode errorCode, String message) {
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 警告返回结果
     *
     * @param message 警告信息
     * @return Result对象
     */
    public static <T> Result<T> warning(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.WARNING.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 未授权返回结果
     *
     * @return Result对象
     */
    public static <T> Result<T> unauthorized() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.UNAUTHORIZED.getCode());
        result.setMessage(ResultCode.UNAUTHORIZED.getMessage());
        return result;
    }

    /**
     * 禁止访问返回结果
     *
     * @return Result对象
     */
    public static <T> Result<T> forbidden() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FORBIDDEN.getCode());
        result.setMessage(ResultCode.FORBIDDEN.getMessage());
        return result;
    }

    /**
     * 资源不存在返回结果
     *
     * @return Result对象
     */
    public static <T> Result<T> notFound() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.NOT_FOUND.getCode());
        result.setMessage(ResultCode.NOT_FOUND.getMessage());
        return result;
    }

    /**
     * 服务器内部错误返回结果
     *
     * @return Result对象
     */
    public static <T> Result<T> serverError() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SERVER_ERROR.getCode());
        result.setMessage(ResultCode.SERVER_ERROR.getMessage());
        return result;
    }
}
