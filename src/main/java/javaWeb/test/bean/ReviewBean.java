package javaWeb.test.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ReviewBean {
    @JsonProperty("review_id")
    private String project_id;
    private String review_name;
    private String review_start;
    private String review_end;
    private String review_description;
    private boolean status;
    private String[] ids;

    public ReviewBean() {
    }
    public ReviewBean(String project_id, String review_name, String review_start, String review_end, String review_description, boolean status) {
        this.project_id = project_id;
        this.review_name = review_name;
        this.review_start = review_start;
        this.review_end = review_end;
        this.review_description = review_description;
        this.status = status;
    }
    public ReviewBean(String project_id, String review_name, String review_start, String review_end) {
        this.project_id = project_id;
        this.review_name = review_name;
        this.review_start = review_start;
        this.review_end = review_end;
    }

    public String getProject_id() {
        return project_id;
    }
    public void setProject_id(String review_id) {
        this.project_id = review_id;
    }
    public String getReview_name() {
        return review_name;
    }
    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }
    public String getReview_start() {
        return review_start;
    }
    public String getReview_end() {
        return review_end;
    }
    public String getReview_description() {
        return review_description;
    }

    public void setReview_start(String review_start) {
        this.review_start = review_start;
    }

    public void setReview_end(String review_end) {
        this.review_end = review_end;
    }

    public void setReview_description(String review_description) {
        this.review_description = review_description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "ReviewBean{" +
                "project_id='" + project_id + '\'' +
                ", review_name='" + review_name + '\'' +
                ", review_start=" + review_start +
                ", review_end=" + review_end +
                ", review_description='" + review_description + '\'' +
                ", status=" + status +
                '}';
    }
}
