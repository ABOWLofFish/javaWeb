/*3. 服务层 - 业务接口类*/

package javaWeb.test.service;

import javaWeb.test.bean.UserBean;

import java.util.List;

public interface UserService {

    /**
     * @description: 登录
     * @param user_id: 用户ID
     * @param password: 密码
     * @param identity: 身份
     * @return UserBean
     **/
    UserBean get_login_info(String user_id, String password, String identity);

    /**
     * @description: 注册
     * @param user_name: 用户名
     * @param password: 密码
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @param identity: 身份
     * @return boolean
     **/
    String put_register_info(String user_name, String password, String tel, String e_mail, String identity);

    /**
     * @description: 显示用户
     * @return List<UserBean>
     **/
    public List<UserBean> display_users();

    /**
     * @description: 新增老师
     * @param user_name: 用户名
     * @param tel: 电话
     * @param e_mail: 邮箱
     * @param identity: 身份
     * @return boolean
     **/
    public boolean add_teacher(String user_name,String tel,String e_mail,String identity);
}