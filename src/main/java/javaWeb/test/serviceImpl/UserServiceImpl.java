/*3. 服务层 - 业务实现类: 团队用户*/

package javaWeb.test.serviceImpl;

import javaWeb.test.bean.UserBean;
import javaWeb.test.mapper.AdminMapper;
import javaWeb.test.mapper.StudentMapper;
import javaWeb.test.mapper.TeacherMapper;
import javaWeb.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    //将DAO注入Service层
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * @description: 登录
     * @param user_id: 用户ID
     * @param password: 密码
     * @param identity: 身份
     * @return UserBean
     **/
    @Override
    public UserBean get_login_info(String user_id, String password, String identity) {
        // 根据身份选择通道
        return switch (identity) {
            case "管理员" -> adminMapper.get_login_info(user_id,password);
            case "学生" -> studentMapper.get_login_info(user_id, password);
            case "导师" -> teacherMapper.get_login_info(user_id, password);
            default -> null;
        };
    }

    /**
     * @description: 新增老师
     * @param user_name: 用户名
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @param identity: 身份
     * @return boolean
     **/
    @Override
    public boolean add_teacher(String user_name,String tel,String e_mail,String identity){
        switch (identity){
            case "导师"->{
                String user_id = teacherMapper.get_Max_id();
                System.out.println(user_id);
                System.out.println(Long.parseLong(user_id)+1);
                if(user_id==null) { // 第一个导师
                    user_id = "10000000";
                } else {
                    user_id = String.valueOf(Long.parseLong(user_id)+1);
                }
                return adminMapper.add_teacher(user_id, user_name, tel, e_mail);
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * @description: 注册
     * @param user_name: 用户名
     * @param password: 密码
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @param identity: 身份
     * @return boolean
     **/
    @Override
    public String put_register_info(String user_name, String password, String tel, String e_mail, String identity) {

        // 根据身份选择通道
        switch (identity) {
            case "管理员" -> {
                // 生成管理员账号
                String user_id = adminMapper.get_Max_id();
                if(user_id==null) { // 第一个管理员
                    user_id = "10000000";
                } else {
                    user_id = String.valueOf(Long.parseLong(user_id)+1);
                }
                System.out.println("admin_user_id: "+user_id);
                adminMapper.insert_user(user_id, user_name, password, tel, e_mail);
                return user_id;

            }
            case "学生" -> {
                String user_id = studentMapper.get_Max_id();
                if(user_id==null) { // 第一个学生
                    user_id = "10000000";
                } else {
                    user_id = String.valueOf(Long.parseLong(user_id)+1);
                }
                System.out.println("student_user_id: "+user_id);
                studentMapper.insert_user(user_id, user_name, password, tel, e_mail);
                return user_id;
            }
            case "导师" -> {
                String user_id = teacherMapper.get_Max_id();
                if(user_id==null) { // 第一个导师
                    user_id = "10000000";
                } else {
                    user_id = String.valueOf(Long.parseLong(user_id)+1);
                }
                System.out.println("teacher_user_id: "+user_id);
                teacherMapper.insert_user(user_id, user_name, password, tel, e_mail);
                return user_id;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * @description: 显示用户
     * @return List<UserBean>
     **/
    @Override
    public List<UserBean> display_users(){
        List<UserBean> students = studentMapper.find_all_students();
        students.stream().forEach(x->x.setIdentity("学生"));
        List<UserBean> teachers = teacherMapper.find_all_teachers();
        teachers.stream().forEach(x->x.setIdentity("老师"));
        return Stream.concat(students.stream(),teachers.stream()).toList();
    }

}