package com.hanlc.attendence.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生ID(与users.id关联)
     */
    private String id;

    /**
     * 学号
     */
    @TableField("student_number")
    private String studentNumber;

    /**
     * 班级ID
     */
    @TableField("class_id")
    private String classId;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 入学年份
     */
    @TableField("enrollment_year")
    private Integer enrollmentYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public Integer getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    @Override
    public String toString() {
        return "Students{" +
            "id = " + id +
            ", studentNumber = " + studentNumber +
            ", classId = " + classId +
            ", department = " + department +
            ", major = " + major +
            ", enrollmentYear = " + enrollmentYear +
        "}";
    }
}
