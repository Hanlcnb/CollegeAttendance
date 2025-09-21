package com.hanlc.attendence.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 教师授课表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@TableName("teacher_courses")
public class TeacherCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 是否主讲(1:是,0:否)
     */
    private Boolean isMain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    @Override
    public String toString() {
        return "TeacherCourses{" +
            "id = " + id +
            ", teacherId = " + teacherId +
            ", courseId = " + courseId +
            ", isMain = " + isMain +
        "}";
    }
}
