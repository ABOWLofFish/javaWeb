package javaWeb.test.mapper;

import javaWeb.test.bean.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    // 学生登录
    UserBean get_login_info(String user_id, String password);

    // 学生注册/管理员新增学生
    boolean insert_user(String user_id, String user_name, String password, String tel, String e_mail);

    // 显示学生信息
    UserBean display_info(String user_id);

    // 学生修改信息
    int update_info(String user_id, String user_name, String tel, String e_mail);

    // 显示各期报告信息
    //List<TeamBean> get_reports(String user_id);
    List <ReportBean> get_reports(String user_id);

    //获取活动通道信息
    List<ProjectBean> get_projects();

    // 管理员修改学生信息
    boolean update_user( String user_id,String user_name,String tel, String e_mail);

    // 管理员删除学生信息
    boolean delete_user(String user_id);

    //管理员筛选学生信息
    List<UserBean> filter_user(String user_id, String user_name, String tel, String e_mail);
    // 获取当前最大账号
    String get_Max_id();

    List<Review> get_reviews(String user_id, String project_id);

    ProjectBean get_report(String user_id, String project_id);

    List<UserBean> find_all_students();

    //查看是否已经被评审
    List<Review> find_review(String project_id , String user_id);

    // 查看是否注册团队
    TeamBean get_team_in_project(String project_id, String user_id);
    // 查看是否提交报告
    String get_report_in_team(String team_id);

    TeamBean display_teamInfo(String user_id, String project_id);

    ReportBean get_report_info(String project_id,String team_id);

    //检查队员id
    List<UserBean> find_students(String captain_id, String member1_id, String member2_id);
}
