package javaWeb.test.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ProjectBean {
    private String project_id;
    //@JsonProperty(value ="")
    private String project_name;
    //@JsonProperty(value ="")
    private String project_start;
    //@JsonProperty(value ="")
    private String project_end;
    //@JsonProperty(value ="")
    private String project_description;
    //@JsonProperty(value ="")
    private boolean status;

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
    public String getProject_start() {
        return project_start;
    }

    public void setProject_start(String project_start) {
        this.project_start = project_start;
    }
    public String getProject_end() {
        return project_end;
    }

    public void setProject_end(String project_end) {
        this.project_end = project_end;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ProjectBean() {
    }
    public ProjectBean(String project_id, String project_name, String project_start, String project_end, boolean status) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_start = project_start;
        this.project_end = project_end;
        this.status = status;
    }
    public ProjectBean(String project_id, String project_name, String project_start, String project_end) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_start = project_start;
        this.project_end = project_end;

    }
    public ProjectBean(String project_id) {
        //sqlsession
        this.project_id = project_id;
    }


    @Override
    public String toString() {
        return "ProjectBean{" +
                "project_id='" + project_id + '\'' +
                ", project_name='" + project_name + '\'' +
                ", project_start=" + project_start +
                ", project_end=" + project_end +
                ", project_description='" + project_description + '\'' +
                ", status=" + status +
                '}';
    }
}
