package com.hanlc.attendence.entity.enums;

import lombok.Getter;

/**
 * 统一返回状态码
 */
@Getter
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 警告
     */
    WARNING(100, "操作警告"),
    
    /**
     * 错误
     */
    ERROR(400, "操作失败"),
    
    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),
    
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),
    
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(500, "服务器内部错误"),
    
    /**
     * 参数错误
     */
    PARAM_ERROR(1001, "参数错误"),
    
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(1002, "数据不存在"),
    
    /**
     * 数据已存在
     */
    DATA_EXIST(1003, "数据已存在"),
    
    /**
     * 业务异常
     */
    BUSINESS_ERROR(2000, "业务异常");

    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态信息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
} 