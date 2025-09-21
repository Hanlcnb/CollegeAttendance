package com.hanlc.attendence.entity.enums;

import lombok.Getter;

/**
 * 签到方式枚举
 */
@Getter
public enum CheckInMethod {
    
    /**
     * 人脸识别签到
     */
    FACE("face", "人脸识别签到"),
    
    /**
     * 密码签到
     */
    PASSWORD("password", "密码签到"),
    
    /**
     * 手势签到
     */
    GESTURE("gesture", "手势签到"),

    /**
     * 二维码签到
     */
    QR("QR", "二维码签到");

    /**
     * 签到方式代码
     */
    private final String code;
    
    /**
     * 签到方式描述
     */
    private final String description;

    CheckInMethod(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据代码获取枚举值
     *
     * @param code 代码
     * @return 枚举值
     */
    public static CheckInMethod getByCode(String code) {
        for (CheckInMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 判断代码是否有效
     *
     * @param code 代码
     * @return 是否有效
     */
    public static boolean isValid(String code) {
        return getByCode(code) != null;
    }
} 