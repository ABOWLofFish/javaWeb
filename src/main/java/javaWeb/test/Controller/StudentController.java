package javaWeb.test.Controller;

import javaWeb.test.bean.*;
import javaWeb.test.service.RedisService;
import javaWeb.test.service.StudentService;
import javaWeb.test.service.TeacherService;
import javaWeb.test.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeamService teamService;

    @Autowired
    RedisService redisService;
    @Autowired
    TeacherService teacherService;

    /**
     @description: 显示学生信息
     @param id:用户ID
     @return UserBean
     **/
    @RequestMapping(value = "/student{user_id}/info",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_info(@PathVariable(name = "user_id")String id){
        System.out.println("login user:"+id);
        UserBean user = studentService.display_info(id);
        System.out.println("login user tele:"+ user.getTel());
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(user);
    }

    /**
     @description: 更新学生信息
     @param user:用户
     @return int
     **/
    @RequestMapping(value = "/student{user_id}/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult update_info(@PathVariable(name = "user_id")String id,@RequestBody UserBean user){
        int status = studentService.update_info(id, user.getName(),user.getTel(),user.getEmail());
        System.out.println(status);
        if(status==1) {// 修改成功
            System.out.println("update ok");
            JSONResult jsonResult = new JSONResult();
            return jsonResult.ok();
        } else {
            System.out.println("update fail");
            JSONResult jsonResult = new JSONResult();
            return jsonResult.errorMsg("update fail");
        }
    }

    /**
     @description: 获取所有提交报告
     @return List<ProjectBean>
     **/
    @RequestMapping(value ="/student{user_id}/projects",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult get_projects(@PathVariable(name = "user_id")String user_id) {
        List<ProjectBean> projects = studentService.get_projects();
        List<Boolean> team_in_project = new ArrayList<>();
        List<Boolean> report_in_team = new ArrayList<>();
        //查看活动对应团队是否存在，是否提交报告
        projects.forEach(x-> {
            TeamBean result = studentService.get_team_in_project(x.getProject_id(), user_id);
            team_in_project.add(result!=null);
            report_in_team.add(result != null && (studentService.get_report_in_team(result.getTeam_id()) != null));
        });
        System.out.println(projects);
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(Stream.concat(Stream.concat(projects.stream(),team_in_project.stream()),report_in_team.stream()).toList());
    }

    /**
     * @description: 注册团队
     * @param project_id: 活动ID
     * @param team: 团队
     * @return boolean
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/registerTeam",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult register_team(@PathVariable(name = "project_id")String project_id,
                                    @RequestBody TeamBean team) {
        JSONResult jsonResult = new JSONResult();
        //查找不到学生id
        if (!(studentService.find_students(team.getCaptain_id(),team.getMember1_id(),team.getMember2_id())))
                return jsonResult.errorMsg("find no student");
        //查找不到学生id
        if (!(teacherService.find_teacher(team.getTeacher_id())))
            return jsonResult.errorMsg("find no teacher");
        boolean status = teamService.register_team(project_id,team.getTeam_name(), team.getCaptain_id(),team.getMember1_id(),team.getMember2_id(), team.getTeacher_id());
        if(status) {// 修改成功
            System.out.println("register team ok");
            return jsonResult.ok();
        } else {
            System.out.println("register team fail");
            return jsonResult.errorMsg("register team fail");
        }
    }

    /**
     @description: 查看团队信息
     @param user_id:用户ID
     @param project_id: 活动ID
     @return TeamBean
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/display_teamInfo",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult display_teamInfo(@PathVariable(name = "user_id")String user_id,
                                    @PathVariable(name = "project_id")String project_id) {
        TeamBean team = studentService.display_teamInfo(user_id,project_id);
        System.out.println(team);
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(team);
    }



    /**
     * @description: 注册团队
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param report_content: 评审内容
     * @return boolean
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/summitReport{team_id}",method = RequestMethod.POST)
    //@ModelAttribute
    /****拦截器：没有组队则转到组队页面*****/

    @ResponseBody
    public JSONResult submit_report(@PathVariable(name = "team_id")String team_id,
                                    @PathVariable(name = "project_id")String project_id,
                                    @RequestBody String report_content) {
        System.out.println("src"+report_content);
        report_content = report_content.replace("{\"report_content\":\"","");
        report_content = report_content.replace("\"}","");
        System.out.println("target"+report_content);
        boolean status = teamService.submit_report(team_id, project_id,report_content);
        JSONResult jsonResult = new JSONResult();
        if(status) {// 修改成功
            System.out.println("alter ok");
            return jsonResult.ok();
        } else {
            System.out.println("alter fail");
            return jsonResult.errorMsg("alter my information fail");
        }
    }

    /**
     @description: 查看评审
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<Review>
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/review{team_id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult get_reivews(@PathVariable(name = "user_id")String user_id,
                                  @PathVariable(name = "project_id")String project_id
                                  ) {
        System.out.println("Enter get reviews!!!!!!!!!!!!!!!!!!!!1");
        JSONResult jsonResult = new JSONResult();
        List<Review> reviews = studentService.get_reviews(user_id,project_id);
        reviews.stream().forEach(x->System.out.println("review"+x));
        return jsonResult.ok(reviews);
    }

    /**
     @description: 获取所有提交报告
     @param user_id:用户ID
     @return List<ReportBean>
     **/
    @RequestMapping(value ="/student{user_id}/myReportsStatus",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult get_reports(@PathVariable(name = "user_id")String user_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(studentService.get_reports(user_id));
    }

    /**
     @description: 查看活动
     @param user_id:用户ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/

    @RequestMapping(value ="/student{user_id}/{project_id}/myReportsStatus",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult get_report(@PathVariable(name = "user_id")String user_id,
                                 @PathVariable(name = "project_id")String project_id) {
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(studentService.get_report(user_id, project_id));
    }

    /**
     @description: 查看报告
     @param team_id:团队ID
     @param project_id: 活动ID
     @return List<ReportBean>
     **/
    @RequestMapping(value = "/student{user_id}/{project_id}/report{team_id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult get_report_info(@PathVariable(name = "user_id")String user_id,
                                      @PathVariable(name = "project_id")String project_id,
                                      @PathVariable(name = "team_id")String team_id){
        JSONResult jsonResult = new JSONResult();
        return jsonResult.ok(studentService.get_report_info(project_id,team_id));
    }

    /**
     @description: 读取缓存
     @param project_id: 活动ID
     @param user_id: 用户ID
     @return String
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/submitReport", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult read_cache(@PathVariable(name = "user_id")String user_id,
                                 @PathVariable(name = "project_id")String project_id) {
        String result = redisService.get(user_id+" "+project_id);
        JSONResult jsonResult = new JSONResult();
        if(result!=null) {
            return jsonResult.ok(result);
        } else {
            return jsonResult.errorMsg("no cache message found");
        }
    }

    /**
     * @description: 写入缓存
     * @param user_id: 用户ID
     * @param project_id: 活动ID
     * @param report_content: 报告内容
     * @return boolean
     **/
    @RequestMapping(value ="/student{user_id}/{project_id}/submitReport/cache", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult write_into_cache(@PathVariable(name = "user_id")String user_id,
                                       @PathVariable(name = "project_id")String project_id,
                                       @RequestBody String report_content) {
        System.out.println(report_content);
        report_content = report_content.replace("{\"report_content\":\"","");
        report_content = report_content.replace("\"}","");
        System.out.println(report_content);
        boolean status = redisService.set(user_id+" "+project_id, report_content);
        JSONResult jsonResult = new JSONResult();
        if(status) {// 修改成功
            System.out.println("save into cache ok");
            return jsonResult.ok();
        } else {
            System.out.println("save into cache fail");
            return jsonResult.errorMsg("save into cache fail");
        }
    }
}
