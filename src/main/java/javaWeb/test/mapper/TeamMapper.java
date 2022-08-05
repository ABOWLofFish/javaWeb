package javaWeb.test.mapper;


import javaWeb.test.bean.Review;
import javaWeb.test.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMapper {
    // 注册团队
    boolean register_team(String project_id,String team_id, String team_name, String captain_id,String member1_id,String member2_id,String teacher_id);

    // 提交报告
    boolean submit_report(String team_id, String project_id,String report_content);

    // 获取最大团队号
    String get_Max_teamId(String project_id);

    // 三个都在表里返回true，否则返回false
    List<UserBean> check_register_info(String captain_id, String member1_id, String member2_id);
}
