package javaWeb.test.Controller;
import javaWeb.test.bean.*;
import javaWeb.test.bean.JSONResult;
import javaWeb.test.service.StudentService;
import javaWeb.test.serviceImpl.AdminServiceImpl;
import javaWeb.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Api(tags = "AdminController")
@Controller
public class AdminController {

    //将Service注入Web层
    @Autowired
    private UserService userService;
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private StudentService studentService;

    /*个人中心*/

    /**
     @description: 显示管理员信息
     @param id:用户ID
     @return UserBean
     **/
    @RequestMapping(value = "/admin{user_id}/info",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_info(@PathVariable(name = "user_id")String id){
        System.out.println("login user:"+id);
        JSONResult jsonResult = new JSONResult();
        System.out.println(jsonResult.ok(adminService.display_info(id)));
        return jsonResult.ok(adminService.display_info(id));
    }

    /**
     @description: 修改管理员信息
     @param user:用户
     @return boolean
     **/
    @RequestMapping(value ="/admin{user_id}/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult update_info(@PathVariable(name = "user_id")String id,@RequestBody UserBean user){
        boolean status = adminService.update_info(id, user.getName(),user.getTel(),user.getEmail());
        JSONResult jsonResult = new JSONResult();
        if(status) {// 修改成功
            System.out.println(jsonResult.ok());
            System.out.println("update ok");
            return jsonResult.ok();
        } else {
            System.out.println("update fail");
            System.out.println(jsonResult.errorMsg("insert fail"));
            return jsonResult.errorMsg("update fail");
        }
    }

    /**
     * @description: 显示用户
     * @return List<UserBean>
     **/
    @RequestMapping(value = "/admin{user_id}/",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_users(){
        JSONResult jsonResult = new JSONResult();
        List<UserBean> users =  userService.display_users();
        return jsonResult.ok(users);
    }

    /**
     * @description: 增加老师
     * @param user: 用户
     * @return boolean
     **/
    @RequestMapping(value = "/admin{user_id}/addUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult insert_user(@RequestBody UserBean user) {
        JSONResult jsonResult = new JSONResult();
        System.out.println(user.getIdentity());
        boolean status = userService.add_teacher(user.getName(),user.getTel(),user.getEmail(),user.getIdentity());
        if(status) {// 新增成功
            System.out.println("insert ok");
            return jsonResult.ok();
        } else {
            System.out.println("insert fail");
            return jsonResult.errorMsg("insert fail");
        }
    }

    /**
     @description: 修改导师/学生信息
     @param user:用户
     @return boolean
     **/
    @RequestMapping(value = "/admin{user_id}/alterUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult update_user(@RequestBody UserBean user) {
        JSONResult jsonResult = new JSONResult();
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getTel());
        System.out.println(user.getEmail());
        System.out.println(user.getIdentity());
        boolean status = adminService.update_user(user.getId(),user.getName(), user.getTel(), user.getEmail(), user.getIdentity());
        if(status) {// 修改成功
            System.out.println("alter ok");
            return jsonResult.ok();
        } else {
            System.out.println("alter fail");
            return jsonResult.errorMsg("alter fail");
        }
    }

    /**
     @description: 删除导师/学生信息
     @param user:用户
     @return boolean
     **/
    @RequestMapping(value = "/admin{user_id}/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult delete_user(@RequestBody UserBean user) {
        System.out.println("delete:"+user);
        JSONResult jsonResult = new JSONResult();
        boolean status = adminService.delete_user(user.getId(), user.getIdentity());
        if(status) {// 删除成功
            System.out.println("delete ok");
            return jsonResult.ok();
        } else {
            System.out.println("delete fail");
            return jsonResult.errorMsg("delete fail");
        }
    }

    /**
     @description: 筛选导师/学生
     @param user:用户
     @return List<UserBean>
     **/
    @RequestMapping(value = "/admin{user_id}/filterUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult filter_user(@RequestBody UserBean user){
        JSONResult jsonResult = new JSONResult();
        List<UserBean> users = adminService.filter_user(user.getId(),user.getName(),user.getTel(),user.getEmail(),user.getIdentity());
        return jsonResult.ok(users);
    }

    /**
     @description: 新建活动
     @param project:活动
     @return boolean
     **/
    @RequestMapping(value = "/admin{user_id}/addProject",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult add_project(@RequestBody ProjectBean project) {
        JSONResult jsonResult = new JSONResult();
        boolean status = adminService.add_project(
                project.getProject_name(),
                project.getProject_start(),
                project.getProject_end(),
                project.getProject_description());
        if(status) {// 新增成功
            System.out.println("insert ok");
            return jsonResult.ok();
        } else {
            System.out.println("insert fail");
            return jsonResult.errorMsg("insert fail");
        }
    }

    /**
     @description: 找到所有老师
     @return List<UserBean>
     **/
    @RequestMapping(value = "/admin{user_id}/addReview/get_teachers",method= RequestMethod.GET)
    @ResponseBody
    public JSONResult get_all_teachers(){
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(adminService.find_all_teachers());
    }

    /**
     @description: 新建评审
     @param review: 评审
     @return boolean
     **/
    @RequestMapping(value = "/admin{user_id}/addReview",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult add_review(@RequestBody ReviewBean review) {
        JSONResult jsonResult = new JSONResult();
        //检查输入活动号是否存在
        boolean find_match = adminService.match_exist_project(review.getProject_id());
        if(!find_match)
            return jsonResult.errorMsg("no such project");
        //检查是否插入成功
        boolean status = adminService.add_review(review.getProject_id(),
                review.getReview_name(),
                review.getReview_start(),
                review.getReview_end(),
                review.getReview_description());
        String[] ids =  review.getIds();
        int len = ids.length;
        for(int i =0;i<len;i++){
            adminService.add_review_teacher(review.getProject_id(),ids[i]);
        }
        if(status) {// 新增成功
            return jsonResult.ok();
        } else {
            return jsonResult.errorMsg("insert fail");
        }
    }

    /**
     @description: 显示所有活动
     @return List<ProjectBean>
     **/
    @RequestMapping(value = "/admin{user_id}/displayProjects",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult diaplay_projects() {
        JSONResult jsonResult = new JSONResult();
        List<ProjectBean> projects = adminService.display_projects();
        System.out.println(projects);
        return jsonResult.ok(projects);
    }

    /**
     @description: 显示该活动所有团队
     @param project_id: 活动ID
     @return List<TeamBean>
     **/
    @RequestMapping(value = "/admin{user_id}/{project_id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_teams(@PathVariable("project_id")String project_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(adminService.display_teams(project_id));
    }

    /**
     @description: 查看团队信息
     @param user_id:用户ID
     @param project_id: 活动ID
     @return TeamBean
     **/
    @RequestMapping(value ="/admin{user_id}/{project_id}/display_teamInfo",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_teamInfo(@PathVariable(name = "user_id")String user_id,
                                       @PathVariable(name = "project_id")String project_id) {
        TeamBean team = studentService.display_teamInfo(user_id,project_id);
        System.out.println(team);
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(team);
    }

    /**
     @description: 显示该团队的报告
     @param project_id:活动ID
     @param team_id: 团队ID
     @return ReportBean
     **/
    @RequestMapping(value = "/admin{user_id}/{project_id}/report{team_id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_report(@PathVariable("project_id")String project_id,
                                     @PathVariable("team_id") String team_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(adminService.display_report(project_id, team_id));
    }

    /**
     @description: 显示所有评审活动
     @return List<ReviewBean>
     **/
    @RequestMapping(value = "/admin{user_id}/displayReviews",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_reviews() {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(adminService.display_reviews());
    }

    /**
     @description: 显示该团队所有评审意见
     @param project_id:活动ID
     @param team_id: 团队ID
     @return List<Review>
     **/
    @RequestMapping(value = "/admin{user_id}/{project_id}/review{team_id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_team_review(@PathVariable("project_id") String project_id,
                                          @PathVariable("team_id") String team_id) {
        JSONResult jsonResult = new JSONResult();
        System.out.println(project_id);
        System.out.println(team_id);
        List<Review> reviews = adminService.display_team_reviews(project_id, team_id);
        System.out.println(reviews);
        return jsonResult.ok(reviews);
    }



}
