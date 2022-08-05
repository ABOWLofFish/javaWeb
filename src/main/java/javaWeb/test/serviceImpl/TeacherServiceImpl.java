package javaWeb.test.serviceImpl;

import javaWeb.test.bean.*;
import javaWeb.test.mapper.TeacherMapper;
import javaWeb.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * @description: 显示老师信息
     * @param user_id: 用户ID
     * @return UserBean
     **/
    public UserBean display_info(String user_id) {
        return teacherMapper.display_info(user_id);
    }

    /**
     * @description: 修改老师信息
     * @param user_id: 用户ID
     * @param user_name: 用户名
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @return boolean
     **/
    public boolean update_info(String user_id, String user_name, String tel, String e_mail) {
        return teacherMapper.update_info(user_id, user_name, tel, e_mail);
    }

    /**
     * @description: 显示该导师对团队的评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    public Review display_review(String user_id, String project_id,String team_id) {
        return teacherMapper.display_review(user_id,project_id,team_id);
    }

    /**
     * @description: 提交评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param review_content: 评审内容
     * @param review_level: 评审等级
     * @return boolean
     **/
    public boolean make_review(String user_id, String project_id ,String team_id, String review_content, Integer review_level) {
        return teacherMapper.make_review(user_id,project_id,team_id, review_content,review_level);
    }

    /**
     * @description: 显示所有评审内容
     * @return List<ReviewBean>
     **/
    public List<ReviewBean> display_reviewsBean(){
        List<ReviewBean> reviews = teacherMapper.display_reviewsBean();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for(ReviewBean review:reviews) {
            review.setStatus(now.compareTo(review.getReview_end()) < 0);
        }
        return reviews;
    }

    /**
     * @description: 显示该活动所有团队
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    public List<TeamBean> display_teams(String user_id, String project_id) {
        return teacherMapper.display_teams(user_id, project_id);
    }

    /**
     * @description: 显示该团队所有评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    public List<Review> display_team_review(String user_id, String project_id, String team_id) {
        return teacherMapper.display_team_review(user_id,project_id,team_id);
    }

    /**
     * @description: 查看团队报告
     * @param team_id: 团队ID
     * @return ReportBean
     **/
    public ReportBean display_report(String team_id) {
        return teacherMapper.display_report(team_id);
    }

    /**
     * @description: 显示已被评审的队伍
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    public List<TeamBean> display_teams_be_reviewed(String user_id, String project_id){
        return teacherMapper.display_teams_be_reviewed(user_id,project_id);
    };

    /**
     * @description: 验证老师身份
     * @param project_id: 活动ID
     * @param user_name: 用户名
     * @return UserBean
     **/
    public UserBean check_teacher_info(String project_id,String user_name){
        return teacherMapper.check_teacher_info(project_id,user_name);
    };

    /**
     * @description: 查找指导老师ID
     * @param teacher_id: 老师ID
     * @return UserBean
     **/
    @Override
    public boolean find_teacher(String teacher_id){
        if(teacherMapper.display_info(teacher_id)!=null)
            return true;
        else return false;
    }
}
