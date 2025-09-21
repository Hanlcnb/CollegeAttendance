package com.hanlc.attendence.entity.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    private String id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班主任ID
     */
    private String adviserId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAdviserId() {
        return adviserId;
    }

    public void setAdviserId(String adviserId) {
        this.adviserId = adviserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Classes{" +
            "id = " + id +
            ", name = " + name +
            ", department = " + department +
            ", major = " + major +
            ", grade = " + grade +
            ", adviserId = " + adviserId +
            ", createdAt = " + createdAt +
        "}";
    }
}
