package javaWeb.test.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
/**
 * @Description: 转储队伍信息
 * @author： 李文博
 * @date: 2022/6/28 latest update by 庄婉如
 */
public class TeamBean {
    //@JsonProperty(value ="")
    private String team_id;
    //@JsonProperty(value ="")

    private String team_name;
    private String project_name;
    private String project_id;

    //@JsonProperty(value ="")
    private String captain_id ;
    //@JsonProperty(value ="")
    private String captain_name ;
    //@JsonProperty(value ="")
    private String member1_id;
    private String member1_name ;

    //@JsonProperty(value ="")
    private String member2_id;
    private String member2_name ;

    //@JsonProperty(value ="")
    private String teacher_id;
    private String teacher_name ;
    //@JsonProperty(value ="")
    private String report_content;
    // private List<Review> reviews;
    //@JsonProperty(value ="")
    private boolean report_status;
    //@JsonProperty(value ="")
    private double grade;

    public TeamBean() {
    }
    public TeamBean(String team_id, String team_name, String project_id, String captain_id,  String member1_id,String member2_id, String teacher_id, String report_content) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.project_id = project_id;
        this.captain_id = captain_id;
        this.member1_id = member1_id;
        this.member2_id = member2_id;
        this.teacher_id = teacher_id;
        this.report_content = report_content;
    }

    public String getMember1_name() {
        return member1_name;
    }

    public void setMember1_name(String member1_name) {
        this.member1_name = member1_name;
    }

    public String getMember2_name() {
        return member2_name;
    }

    public void setMember2_name(String member2_name) {
        this.member2_name = member2_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public String toString() {
        return "TeamBean{" +
                "team_id='" + team_id + '\'' +
                ",team_name='" + team_name +'\'' +
                ", teacher_name='" + teacher_id + '\'' +
                ", report_content='" + report_content + '\'' +
                ", report_status=" + report_status +
                ", grade=" + grade +
                '}';
    }

    public String getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCaptain_id() {
        return captain_id;
    }

    public void setCaptain_id(String captain_id) {
        this.captain_id = captain_id;
    }

    public String getCaptain_name() {
        return captain_name;
    }

    public void setCaptain_name(String captain_name) {
        this.captain_name = captain_name;
    }
    public String getMember1_id() {
        return member1_id;
    }

    public void setMember1_id(String member1_id) {
        this.member1_id = member1_id;
    }

    public String getMember2_id() {
        return member2_id;
    }

    public void setMember2_id(String member2_id) {
        this.member2_id = member2_id;
    }


    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    public boolean isReport_status() {
        return report_status;
    }

    public void setReport_status(boolean report_status) {
        this.report_status = report_status;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


    public TeamBean(String team_id, String project_name, String captain_id, String captain_name,String member1_id, String member2_id, String teacher_name, String report_content, boolean report_status, double grade) {
        this.team_id = team_id;
        this.project_name = project_name;
        this.captain_id = captain_id;
        this.captain_name = captain_name;
        this.member1_id = member1_id;
        this.member2_id = member2_id;
        this.teacher_id = teacher_name;
        this.report_content = report_content;
        this.report_status = report_status;
        this.grade = grade;
    }
    public TeamBean(String team_id, String captain_id, String captain_name,String team_name, String teacher_id) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.captain_id = captain_id;
        this.captain_name = captain_name;
        this.teacher_id = teacher_id;

    }

}
