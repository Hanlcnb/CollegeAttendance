package com.hanlc.graduationproject.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生选课表
 * </p>
 *
 * @author Hanlc
 * @since 2025-04-02 01:24:40
 */
@TableName("student_courses")
public class StudentCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 选课时间
     */
    private LocalDateTime selectedAt;

    /**
     * 状态(1:正常,0:退课)
     */
    private Boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getSelectedAt() {
        return selectedAt;
    }

    public void setSelectedAt(LocalDateTime selectedAt) {
        this.selectedAt = selectedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StudentCourses{" +
            "id = " + id +
            ", studentId = " + studentId +
            ", courseId = " + courseId +
            ", selectedAt = " + selectedAt +
            ", status = " + status +
        "}";
    }
}
