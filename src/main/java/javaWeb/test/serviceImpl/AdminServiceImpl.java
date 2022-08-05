package javaWeb.test.serviceImpl;

import javaWeb.test.bean.*;
import javaWeb.test.mapper.AdminMapper;
import javaWeb.test.mapper.StudentMapper;
import javaWeb.test.mapper.TeacherMapper;
import javaWeb.test.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private AdminMapper adminMapper;

    /**
     @description: 显示管理员信息
     @param user_id:用户ID
     @return UserBean
     **/
    public UserBean display_info(String user_id) {
        return adminMapper.display_info(user_id);
    }

    /**
     @description: 修改管理员信息
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @return boolean
     **/
    public boolean update_info(String user_id, String user_name, String tel, String e_mail) {
        return adminMapper.update_info(user_id, user_name, tel, e_mail);
    }

    /**
     @description: 修改导师/学生信息
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @param identity: 身份
     @return boolean
     **/
    public boolean update_user(String user_id,String user_name, String tel, String e_mail,String identity) {
        return switch (identity) {
            case "Student" -> studentMapper.update_user(user_id,user_name, tel, e_mail);
            case "Teacher" -> teacherMapper.update_user(user_id,user_name, tel, e_mail);
            default -> false;
        };
    }

    /**
     @description: 删除导师/学生信息
     @param user_id:用户ID
     @param identity: 身份
     @return boolean
     **/
    public boolean delete_user(String user_id, String identity) {
        return switch (identity) {
            case "Student" -> studentMapper.delete_user(user_id);
            case "Teacher" -> teacherMapper.delete_user(user_id);
            default -> false;
        };
    }

    /**
     @description: 筛选导师/学生
     @param user_id:用户ID
     @param user_name: 用户名
     @param tel: 电话
     @param e_mail: 邮箱
     @param identity: 身份
     @return List<UserBean>
     **/
    public List<UserBean> filter_user(String user_id, String user_name, String tel, String e_mail, String identity){
        List<UserBean> users;
        System.out.println("tel: "+tel);
        switch(identity){
            case "Student":
                users = studentMapper.filter_user(user_id,user_name,tel, e_mail);
                users.stream().forEach(x->{x.setIdentity("学生");});
                break;
            case "Teacher":
                users = teacherMapper.filter_user(user_id,user_name,tel, e_mail);
                users.stream().forEach(x->{x.setIdentity("老师");});
                break;
            default:
                List<UserBean> students = studentMapper.filter_user(user_id,user_name,tel, e_mail);
                students.stream().forEach(x->{x.setIdentity("学生");});
                List<UserBean> teachers =teacherMapper.filter_user(user_id,user_name,tel, e_mail);
                teachers.stream().forEach(x->{x.setIdentity("老师");});
                users = Stream.concat(students.stream(),teachers.stream()).toList();
        };
        System.out.println(users);
        return users;
    }

    /**
     @description: 新建活动
     @param project_name:活动名
     @param project_start: 开始时间
     @param project_end: 结束时间
     @param project_description: 活动描述
     @return boolean
     **/
    public boolean add_project(String project_name, String project_start, String project_end, String project_description) {
        //获取project_id
        Calendar calendar = Calendar.getInstance();
        String project_id= adminMapper.get_Max_project_id(
                calendar.get(Calendar.YEAR) + String.format("%02d",calendar.get(Calendar.MONTH)+1));
        if(project_id==null) { // 本年月第一个活动
            project_id = calendar.get(Calendar.YEAR) + String.format("%02d",calendar.get(Calendar.MONTH)+1) + "01";
        } else {
            // 活动id = 年月+2位序号
            project_id = project_id.substring(0,6) + String.format("%02d",(Integer.parseInt(project_id.substring(6)) + 1));
        }
        System.out.println("project_id: "+project_id);
        return adminMapper.add_project(project_id, project_name, project_start, project_end, project_description);
    }

    /**
     @description: 新建评审
     @param project_id: 活动ID
     @param review_name:评审名
     @param review_start: 开始时间
     @param review_end: 结束时间
     @param review_description: 评审描述
     @return boolean
     **/
    public boolean add_review(String project_id, String review_name,String review_start,String review_end,String review_description) {
        return adminMapper.add_review(project_id,review_name,review_start,review_end,review_description);
    }


    /**
     @description: 显示所有活动
     @return List<ProjectBean>
     **/
    public List<ProjectBean> display_projects() {
        List<ProjectBean> projects = adminMapper.display_projects();
        // 1、普通的时间转换
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(ProjectBean project:projects) {
            project.setStatus(now.compareTo(project.getProject_end()) < 0);
        }
        return projects;
    }

    /**
     @description: 显示该活动所有团队
     @param project_id: 活动ID
     @return List<TeamBean>
     **/
    public List<TeamBean>display_teams(String project_id) {
        return adminMapper.display_teams(project_id);
    }

    /**
     @description: 显示该团队的报告
     @param project_id:活动ID
     @param team_id: 团队ID
     @return ReportBean
     **/
    public ReportBean display_report(String project_id, String team_id) {
        return adminMapper.display_report(project_id, team_id);
    }

    /**
     @description: 显示所有评审活动
     @return List<ReviewBean>
     **/
    public List<ReviewBean> display_reviews() {
        List<ReviewBean> reviews = adminMapper.display_reviews();
        // 1、普通的时间转换
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(ReviewBean review:reviews) {
            review.setStatus(now.compareTo(review.getReview_end()) < 0);
        }
        return reviews;
    }

    /**
     @description: 显示该团队所有评审意见
     @param project_id:活动ID
     @param team_id: 团队ID
     @return List<Review>
     **/
    public List<Review> display_team_reviews(String project_id, String team_id) {
        return adminMapper.display_team_reviews(team_id);
    }

    /**
     @description: 找到所有老师
     @return List<UserBean>
     **/
    public List<UserBean> find_all_teachers(){
        return teacherMapper.find_all_teachers();
    };

    /**
     @description: 新增负责评审的老师们
     @param project_id:活动ID
     @param teacher_id: 老师ID
     @return boolean
     **/
    public boolean add_review_teacher(String project_id,String teacher_id){
        return adminMapper.add_review_teacher(project_id,teacher_id);
    }

    /**
     @description: 查找评审对应活动
     @param project_id:活动ID
     @return boolean
     **/
    public boolean match_exist_project(String project_id) {
        if(adminMapper.match_exist_project(project_id)!=null)
            return true;
        else return false;
    }
}
