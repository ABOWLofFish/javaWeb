package javaWeb.test.service;

import javaWeb.test.bean.*;

import java.sql.Date;
import java.util.List;

public interface AdminService {

    /**
     @description: 显示管理员信息
     @param user_id:用户ID
     @return UserBean
     **/
    UserBean display_info(String user_id);

    /**
     @description: 修改管理员信息
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @return boolean
     **/
    boolean update_info(String user_id, String user_name, String tel, String e_mail);

    /**
     @description: 修改导师/学生信息
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @param identity: 身份
     @return boolean
     **/
    boolean update_user(String user_id,String user_name, String tel, String e_mail,String identity);

    /**
     @description: 删除导师/学生信息
     @param user_id:用户ID
     @param identity: 身份
     @return boolean
     **/
    boolean delete_user(String user_id, String identity);

    /**
     @description: 找到所有老师
     @return List<UserBean>
     **/
    List<UserBean> find_all_teachers();

    /**
     @description: 筛选导师/学生
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @param identity: 身份
     @return List<UserBean>
     **/
    List<UserBean> filter_user(String user_id,String user_name,String tel, String e_mail,String identity );

    /**
     @description: 新建活动
     @param project_name:活动名
     @param project_start: 开始时间
     @param project_end: 结束时间
     @param project_description: 活动描述
     @return boolean
     **/
    boolean add_project(String project_name, String project_start, String project_end, String project_description);

    /**
     @description: 新建评审
     @param project_id: 活动ID
     @param review_name:评审名
     @param review_start: 开始时间
     @param review_end: 结束时间
     @param review_description: 评审描述
     @return boolean
     **/
    boolean add_review(String project_id, String review_name, String review_start,String review_end,String review_description);

    /**
     @description: 显示所有活动
     @return List<ProjectBean>
     **/
    List<ProjectBean> display_projects();

    /**
     @description: 显示该活动所有团队
     @param project_id: 活动ID
     @return List<TeamBean>
     **/
    List<TeamBean>display_teams(String project_id);

    /**
     @description: 显示该团队的报告
     @param project_id:活动ID
     @param team_id: 团队ID
     @return ReportBean
     **/
    ReportBean display_report(String project_id, String team_id);

    /**
     @description: 显示所有评审活动
     @return List<ReviewBean>
     **/
    List<ReviewBean> display_reviews();

    /**
     @description: 显示该团队所有评审意见
     @param project_id:活动ID
     @param team_id: 团队ID
     @return List<Review>
     **/
    List<Review> display_team_reviews(String project_id, String team_id);

    /**
     @description: 新增负责评审的老师们
     @param project_id:活动ID
     @param teacher_id: 老师ID
     @return boolean
     **/
    boolean add_review_teacher(String project_id,String teacher_id);

    /**
     @description: 查找评审对应活动
     @param project_id:活动ID
     @return boolean
     **/
    public boolean match_exist_project(String project_id);
}
