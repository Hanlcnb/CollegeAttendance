package com.hanlc.graduationproject.entity.domain;

import java.io.Serializable;

/**
 * <p>
 * 教师信息表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师ID(与users.id关联)
     */
    private String id;

    /**
     * 工号
     */
    private String teacherNumber;

    /**
     * 院系
     */
    private String department;

    /**
     * 职称
     */
    private String title;

    /**
     * 办公室
     */
    private String office;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Teachers{" +
            "id = " + id +
            ", teacherNumber = " + teacherNumber +
            ", department = " + department +
            ", title = " + title +
            ", office = " + office +
        "}";
    }
}
