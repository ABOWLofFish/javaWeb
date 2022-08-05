package javaWeb.test.service;

import javaWeb.test.bean.*;

import java.util.List;

public interface TeacherService {

    /**
     * @description: 显示老师信息
     * @param user_id: 用户ID
     * @return UserBean
     **/
    UserBean display_info(String user_id);

    /**
     * @description: 修改老师信息
     * @param user_id: 用户ID
     * @param user_name: 用户名
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @return boolean
     **/
    boolean update_info(String user_id, String user_name, String tel, String e_mail);

    /**
     * @description: 显示该导师对团队的评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    Review display_review(String user_id, String project_id,String team_id);

    /**
     * @description: 提交评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param review_content: 评审内容
     * @param review_level: 评审等级
     * @return boolean
     **/
    boolean make_review(String user_id, String project_id,String team_id, String review_content, Integer review_level);

    /**
     * @description: 显示所有评审内容
     * @return List<ReviewBean>
     **/
    List<ReviewBean> display_reviewsBean();

    /**
     * @description: 显示该活动所有团队
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    List<TeamBean> display_teams(String user_id, String project_id);

    /**
     * @description: 显示已被评审的队伍
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    List<TeamBean> display_teams_be_reviewed(String user_id, String project_id);

    /**
     * @description: 显示该团队所有评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    List<Review> display_team_review(String user_id, String project_id, String team_id);

    /**
     * @description: 查看团队报告
     * @param team_id: 团队ID
     * @return ReportBean
     **/
    ReportBean display_report(String team_id);
    /**
     * @description: 验证老师身份
     * @param project_id: 活动ID
     * @param user_name: 用户名
     * @return UserBean
     **/
    UserBean check_teacher_info(String project_id,String user_name);

    /**
     * @description: 查找指导老师ID
     * @param teacher_id: 老师ID
     * @return UserBean
     **/
    boolean find_teacher(String teacher_id);
}
