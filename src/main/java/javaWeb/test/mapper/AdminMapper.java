/*1. DAO层 - 数据库访问接口类*/


package javaWeb.test.mapper;

import javaWeb.test.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Mapper
public interface AdminMapper {

    // 管理员登录
    UserBean get_login_info(String user_id, String password);

    // 获取管理员信息
    UserBean display_info(String user_id);

    // 修改管理员信息
    boolean update_info(String user_id, String user_name, String tel, String e_mail);

    //新增导师
    public boolean add_teacher(String user_id,String user_name,String tel,String email);

    // 新增管理员
    boolean insert_user(String user_id,String user_name, String password, String tel, String e_mail);

    // 新增活动
    boolean add_project(String project_id, String project_name, String project_start, String project_end, String project_description);

    // 新增评审链接
    boolean add_review(String project_id,String review_name,String review_start,String review_end,String review_description);

    // 查看所有活动
    List<ProjectBean> display_projects();

    // 查看该活动的所有团队
    List<TeamBean> display_teams(String project_id);
    // 查看该活动，该团队报告
    ReportBean display_report(String project_id, String team_id);

    // 查看所有评审
    List<ReviewBean> display_reviews();

    // 查看该活动，该团队所有评审意见
    List<Review> display_team_reviews(String team_id);

    // 获取最大管理员号(用于注册新管理员是id++)
    String get_Max_id();

    // 获取最大活动id,查找前六位和年月匹配且最大的id
    String get_Max_project_id(String YEAR_MONTH);
    List<UserBean> find_all_teachers();
    //新增负责该评审的老师们
    boolean add_review_teacher(
            String project_id,
            String teacher_id);

    //查找评审对应活动
    ProjectBean match_exist_project(String project_id);
}