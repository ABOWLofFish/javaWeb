package javaWeb.test.service;

public interface TeamService {

    /**
     * @description: 注册团队
     * @param project_id: 活动ID
     * @param team_name: 团队名
     * @param captain_id: 队长名
     * @param member1_id: 队员1姓名
     * @param member2_id: 队员2姓名
     * @param teacher_id: 老师编号
     * @return boolean
     **/
    boolean register_team(String project_id,String team_name, String captain_id,String member1_id,String member2_id,String teacher_id);

    /**
     * @description: 提交报告
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param report_content: 评审内容
     * @return boolean
     **/
    boolean submit_report(String team_id,String project_id, String report_content);
}
