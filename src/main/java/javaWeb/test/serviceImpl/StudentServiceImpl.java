package javaWeb.test.serviceImpl;

import javaWeb.test.bean.*;
import javaWeb.test.mapper.StudentMapper;
import javaWeb.test.mapper.TeamMapper;
import javaWeb.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     @description: 显示学生信息
     @param user_id:用户ID
     @return UserBean
     **/
    @Override
    public UserBean display_info(String user_id) {
        return studentMapper.display_info(user_id);
    }

    /**
     @description: 更新学生信息
     @param user_id:用户ID
     @param user_name: 用户姓名
     @param tel: 用户电话
     @param e_mail: 用户邮箱
     @return int
     **/
    @Override
    public int update_info(String user_id, String user_name, String tel, String e_mail) {
        return studentMapper.update_info(user_id, user_name, tel, e_mail);
    }

    /**
     @description: 获取所有提交报告
     @param user_id:用户ID
     @return List<ReportBean>
     **/
    @Override
    public List<ReportBean> get_reports(String user_id) {
        List<ReportBean> reports = studentMapper.get_reports(user_id);
        reports.stream().forEach(x-> {
            if (x.getReport_content() == null)
                x.setSubmmit_status(false);
            else x.setSubmmit_status(true);
            if (studentMapper.find_review(x.getProject_id(),user_id) == null)
                x.setReview_status(false);
            else x.setReview_status(true);
        });
        return reports;
    }

    /**
     @description: 获取所有提交报告
     @return List<ProjectBean>
     **/
    @Override
    public List<ProjectBean> get_projects() {
        List<ProjectBean> projects = studentMapper.get_projects();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(ProjectBean project:projects) {
            project.setStatus(now.compareTo(project.getProject_end()) < 0);
        }
        return projects;
    }

    /**
     @description: 查看评审
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<Review>
     **/
    @Override
    public List<Review> get_reviews(String user_id,String project_id){return studentMapper.get_reviews(user_id,project_id);}

    /**
     @description: 查看报告
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/
    @Override
    public ProjectBean get_report(String user_id, String project_id){
        return studentMapper.get_report(user_id, project_id);}

    /**
     @description: 查看是否注册团队
     @param project_id: 活动ID
     @param user_id: 用户ID
     @return TeamBean
     **/
    @Override
    public TeamBean get_team_in_project(String project_id, String user_id) {
        return studentMapper.get_team_in_project(project_id,user_id);
    }

    /**
     @description: 查看是否提交报告
     @param team_id:团队ID
     @return String
     **/
    @Override
    public String get_report_in_team(String team_id) {
        return studentMapper.get_report_in_team(team_id);

    }

    /**
     @description: 查看团队信息
     @param user_id:用户ID
     @param project_id: 活动ID
     @return TeamBean
     **/
    @Override
    public TeamBean display_teamInfo(String user_id,String project_id) {
        return studentMapper.display_teamInfo(user_id,project_id);
    }

    /**
     @description: 查看报告
     @param team_id:团队ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/
    @Override
    public ReportBean get_report_info(String project_id,String team_id){
        return studentMapper.get_report_info(project_id,team_id);
    };

    /**
     @description: 检查团队队员id
     @param captain_id:队长ID
     @param member1_id: 队员1ID
     @param member2_id: 队员2ID
     @return List<ReportBean>
     **/
    @Override
    public boolean find_students(String captain_id, String member1_id, String member2_id){
        if(studentMapper.find_students(captain_id,member1_id,member2_id).size()!=3)
            return false;
        else return true;
    }

}
