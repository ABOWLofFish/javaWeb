package javaWeb.test.mapper;

import javaWeb.test.bean.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    // 导师登录
    UserBean get_login_info(String user_id, String password);

    // 导师注册
    boolean insert_user(String user_id, String user_name, String password, String tel, String e_mail);

    // 获取导师信息
    UserBean display_info(String user_id);

    // 导师修改信息
    boolean update_info(String user_id, String user_name, String tel, String e_mail);

    // 显示该导师意见
    Review display_review(String user_id, String project_id,String team_id);

    // 导师提交评审意见
    boolean make_review(String user_id, String project_id,String team_id, String review_content, Integer review_level);

    // 管理员修改导师信息
    boolean update_user( String user_id,String user_name,String tel, String e_mail);

    // 管理员删除导师信息
    boolean delete_user(String user_id);

    //管理员筛选导师信息
    List<UserBean> filter_user(String user_id, String user_name, String tel, String e_mail);

    // 获取当前最大账号
    String get_Max_id();

    List<ReviewBean> display_reviewsBean();
    // 显示该活动所有团队
    List<TeamBean> display_teams(String user_id, String project_id);

    // 显示该团队所有评审意见
    List<Review> display_team_review(String user_id, String project_id, String team_id);

    List<UserBean> find_all_teachers();

    ReportBean display_report(String team_id);
    List<TeamBean> display_teams_be_reviewed(String user_id, String project_id);
    //验证导师身份
    UserBean check_teacher_info(String project_id,String user_name);

}
