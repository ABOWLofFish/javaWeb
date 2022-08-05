package javaWeb.test.Controller;

import com.alibaba.fastjson.JSON;
import javaWeb.test.bean.*;
import javaWeb.test.bean.JSONResult;
import javaWeb.test.service.RedisService;
import javaWeb.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    RedisService redisService;

    /*个人中心*/

    /**
     * @description: 显示老师信息
     * @param id: 用户ID
     * @return UserBean
     **/
    @RequestMapping(value = "/teacher{user_id}/info",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_info(@PathVariable(name = "user_id")String id){
        System.out.println("login user:"+id);
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(teacherService.display_info(id));
    }

    /**
     * @description: 修改老师信息
     * @param user: 用户
     * @return boolean
     **/
    @RequestMapping(value ="/teacher{user_id}/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult update_info(@PathVariable(name = "user_id")String id,@RequestBody UserBean user){
        boolean status = teacherService.update_info(id,user.getName(),user.getTel(),user.getEmail());
        JSONResult jsonResult = new JSONResult();
        if(status) {// 修改成功
            System.out.println("update ok");
            return jsonResult.ok();
        } else {
            System.out.println("update fail");
            return jsonResult.errorMsg("update fail");
        }
    }

    /**
     * @description: 显示所有评审内容
     * @return List<ReviewBean>
     **/
    @RequestMapping(value ="/teacher{user_id}/review",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_reviewsBean() {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(teacherService.display_reviewsBean());
    }

    /**
     * @description: 显示该活动所有团队
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/displayteams",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_teams(@PathVariable(name = "user_id")String user_id,
                                    @PathVariable(name = "project_id")String project_id) {
        JSONResult jsonResult = new JSONResult();
        System.out.println(project_id);
        System.out.println(user_id);
        return jsonResult.ok(teacherService.display_teams(user_id, project_id));
    }

    /**
     * @description: 显示已被评审的队伍
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @return List<TeamBean>
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/displayteams_reviewed",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_teams_reviewed(@PathVariable(name = "user_id")String user_id,
                                           @PathVariable(name = "project_id")String project_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(teacherService.display_teams_be_reviewed(user_id, project_id));
    }

    /**
     * @description: 显示该团队所有评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/displaymyReviews",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_team_review(@PathVariable(name = "user_id")String user_id,
                                          @PathVariable(name = "project_id")String project_id,
                                          @PathVariable(name = "project_id")String team_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(teacherService.display_team_review(user_id,project_id,team_id));
    }

    /**
     * @description: 显示该导师对团队的评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @return List<Review>
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/{team_id}/myReview",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_review(@PathVariable(name = "user_id")String user_id,
                                      @PathVariable(name = "project_id")String project_id,
                                      @PathVariable(name = "team_id")String team_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(teacherService.display_review(user_id,project_id,team_id));
    }

    /**
     * @description: 提交评审意见
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param review: 评审
     * @return boolean
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/{team_id}/makeReview",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult make_review(@PathVariable(name = "user_id")String user_id,
                                  @PathVariable(name = "project_id")String project_id,
                                  @PathVariable(name = "team_id")String team_id,
                                  @RequestBody Review review) {
        boolean status = teacherService.make_review(user_id, project_id,team_id, review.getReview_content(), review.getReview_level());
        JSONResult jsonResult = new JSONResult();
        if(status) {// 提交成功
            System.out.println("submit ok");
            return jsonResult.ok();
        } else {
            System.out.println("submit fail");
            return jsonResult.errorMsg("submit fail");
        }
    }

    /**
     * @description: 查看团队报告
     * @param team_id: 团队ID
     * @return ReportBean
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/{team_id}/report",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_report(@PathVariable(name = "user_id")String user_id,
                                     @PathVariable(name = "project_id")String project_id,
                                     @PathVariable(name = "team_id")String team_id) {

        ReportBean report = teacherService.display_report(team_id);
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(report);
    }

    /**
     @description: 读取缓存
     @param project_id: 活动ID
     @param user_id: 用户ID
     @return String
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/{team_id}/report/cache",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult read_cache(@PathVariable(name = "user_id")String user_id,
                                     @PathVariable(name = "project_id")String project_id,
                                     @PathVariable(name = "team_id")String team_id) {
        JSONResult jsonResult = new JSONResult();
        Review result = JSON.parseObject(redisService.get(user_id+" "+project_id), Review.class); // 缓存
        System.out.println(result);
        return jsonResult.ok(result);
    }

    /**
     * @description: 写入缓存
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param review: 评审
     * @return boolean
     **/
    @RequestMapping(value ="/teacher{user_id}/{project_id}/makeReview/cache", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult write_into_cache(@PathVariable(name = "user_id")String user_id,
                                       @PathVariable(name = "project_id")String project_id,
                                       @RequestBody Review review) {
        System.out.println(user_id);
        System.out.println(project_id);
        System.out.println(review.getReview_content());
        boolean status = redisService.set(user_id+" "+project_id, JSON.toJSONString(review));
        JSONResult jsonResult = new JSONResult();
        if(status) {// 修改成功
            System.out.println("save into cache success");
            return jsonResult.ok();
        } else {
            System.out.println("save into cache fail");
            return jsonResult.errorMsg("save into cache fail");
        }
    }

    /**
     * @description: 验证老师身份
     * @param project_id: 活动ID
     * @param user: 用户
     * @return UserBean
     **/
    @RequestMapping(value = "teacher/{project_id}/check",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult check_teacher_info(@PathVariable(name = "project_id")String project_id,
                                         @RequestBody UserBean user){
        System.out.println(project_id);
        System.out.println(user.getName());
        JSONResult jsonResult = new JSONResult();
        UserBean teacher =  teacherService.check_teacher_info(project_id,user.getName());
        if(teacher!=null)
            return jsonResult.ok(teacher);
        else return jsonResult.errorMsg("Wrong answer!find no teacher");
    }
}
