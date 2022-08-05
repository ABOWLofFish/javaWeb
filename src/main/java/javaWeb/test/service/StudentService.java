package javaWeb.test.service;

import javaWeb.test.bean.*;

import java.util.List;

public interface StudentService {
    /**
     @description: 显示学生信息
     @param user_id:用户ID
     @return UserBean
     **/
    UserBean display_info(String user_id);

    /**
     @description: 更新学生信息
     @param user_id:用户ID
     @param user_name: 用户姓名
     @param tel: 用户电话
     @param e_mail: 用户邮箱
     @return int
     **/
    int update_info(String user_id, String user_name, String tel, String e_mail);

    /**
     @description: 获取所有提交报告
     @param user_id:用户ID
     @return List<ReportBean>
     **/
    List<ReportBean> get_reports(String user_id);

    /**
     @description: 获取所有提交报告
     @return List<ProjectBean>
     **/
    List<ProjectBean> get_projects();

    /**
     @description: 查看评审
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<Review>
     **/
    List<Review>get_reviews(String user_id,String project_id);

    /**
     @description: 查看是否注册团队
     @param project_id: 活动ID
     @param user_id: 用户ID
     @return TeamBean
     **/
    TeamBean get_team_in_project(String project_id, String user_id);

    /**
     @description: 查看是否提交报告
     @param team_id:团队ID
     @return String
     **/
    String get_report_in_team(String team_id);

    /**
     @description: 查看报告
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/
    ProjectBean get_report(String user_id, String project_id);

    /**
     @description: 查看团队信息
     @param user_id:用户ID
     @param project_id: 活动ID
     @return TeamBean
     **/
    TeamBean display_teamInfo(String user_id,String project_id);

    /**
     @description: 查看报告
     @param team_id:团队ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/
    ReportBean get_report_info(String project_id,String team_id);
    /**
     @description: 检查团队队员id
     @param captain_id:队长ID
     @param member1_id: 队员1ID
     @param member2_id: 队员2ID
     @return List<ReportBean>
     **/
    boolean find_students(String captain_id,String member1_id,String member2_id);
}
