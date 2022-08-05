package javaWeb.test.bean;

import javaWeb.test.mapper.StudentMapper;
import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 转储个人活动-报告信息和状态
 * @author： 庄婉如
 * @date: 2022/6/28 latest update by 庄婉如
 */
public class ReportBean {
    //@JsonProperty(value ="")
    String project_id;
    //@JsonProperty(value ="")
    String project_name;
    //@JsonProperty(value ="")
    String team_name;
    //@JsonProperty(value ="")
    String report_content;
    //@JsonProperty(value ="")
    String teacher_name;
    //@JsonProperty(value ="")
    boolean review_status;
    //@JsonProperty(value ="")
    boolean submit_status;

    public ReportBean()
    {}

    public ReportBean(String project_id,String project_name, String report_content, String teacher_name, boolean review_status, boolean submmit_status) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.report_content = report_content;
        this.teacher_name = teacher_name;
        this.review_status = review_status;
        this.submit_status = submmit_status;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public boolean isSubmit_status() {
        return submit_status;
    }

    public void setSubmit_status(boolean submit_status) {
        this.submit_status = submit_status;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public boolean isReview_status() {
        return review_status;
    }

    public void setReview_status(boolean review_status) {
        this.review_status = review_status;
    }

    public boolean isSubmmit_status() {
        return submit_status;
    }
    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
    public void setSubmmit_status(boolean submmit_status) {
        this.submit_status = submmit_status;
    }

    @Override
    public String toString() {
        return "ReportBean{" +
                "project=" + project_name +
                ", report_content='" + report_content + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                ", review_status=" + review_status +
                ", submmit_status=" + submit_status +
                '}';
    }
}
