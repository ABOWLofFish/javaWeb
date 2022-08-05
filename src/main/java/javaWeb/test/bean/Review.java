package javaWeb.test.bean;

public class Review {
    String review_content; // 评审意见
    Integer grade; // 评审评级
    String teacher_id;
    public Review() {

    }

    public Integer getReview_level() {
        return grade;
    }

    public void setReview_level(Integer review_level) {
        this.grade = review_level;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review_content='" + review_content + '\'' +
                ", review_level='" + grade + '\'' +
                '}';
    }
}
