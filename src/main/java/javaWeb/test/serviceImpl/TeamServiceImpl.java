package javaWeb.test.serviceImpl;

import javaWeb.test.mapper.TeamMapper;
import javaWeb.test.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

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
    @Override
    public boolean register_team(String project_id,String team_name, String captain_id,String member1_id,String member2_id,String teacher_id) {
        //
        if(teamMapper.check_register_info(captain_id,member1_id,member2_id).size()!=3) return false;
        String team_id = teamMapper.get_Max_teamId(project_id);
        if(team_id==null) { // 本活动第一个团队
            team_id = project_id + "001";
        } else {
            team_id = team_id.substring(0,8) + String.format("%03d",Integer.parseInt(team_id.substring(8))+1);
        }
        System.out.println("team_id: "+team_id);
        boolean flag = teamMapper.register_team(project_id,team_id, team_name,captain_id,member1_id,member2_id,teacher_id);
        System.out.println(flag);
        return flag;
    }

    /**
     * @description: 提交报告
     * @param project_id: 活动ID
     * @param team_id: 团队ID
     * @param report_content: 评审内容
     * @return boolean
     **/
    @Override
    public boolean submit_report(String team_id, String project_id,String report_content) {
        return teamMapper.submit_report(team_id, project_id, report_content);
    }

}
