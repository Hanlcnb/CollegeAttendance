package com.hanlc.attendence.entity.enums;

public enum MessageType {

    TEXT("text", "文本"),

    ATTEND("attend", "签到"),

    IMAGE("image", "图片"),

    FILE("file", "文件");

    private final String type;

    private final String description;

    MessageType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static MessageType getByType(String type) {
        for (MessageType type1 : values()) {
            if (type1.type.equals(type)) {
                return type1;
            }
        }
        return null;
    }

    public static boolean isValid(String type) {
        return getByType(type) != null;
    }
}
