/*4. Controller层，实现与web前端的交互: 登录*/


package javaWeb.test.Controller;

import io.swagger.annotations.*;
import javaWeb.test.bean.UserBean;
import javaWeb.test.bean.JSONResult;
import javaWeb.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "LoginController")
@Controller
public class LoginController {

    //将Service注入Web层
    @Autowired
    UserService userService;

    /**
     * @description: 登录
     * @param user: 用户
     * @return UserBean
     **/
    @ApiOperation(value = "用户登录", notes = "输入账号密码以登录")    //说明是什么方法(可以理解为方法注释)
    @RequestMapping (value = "",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_login_info(@RequestBody UserBean user){
        UserBean userBean = userService.get_login_info(user.getId(), user.getPassword(), user.getIdentity());
        JSONResult jsonResult = new JSONResult();
        if(userBean!=null){
            System.out.println("login success!");
            System.out.println("id: "+userBean.getId());
            System.out.println("pwd: "+userBean.getPassword());
            return jsonResult.ok();
        }
        else {
            System.out.println("login fail!");
            System.out.println(jsonResult.errorMsg("user doesn't exist"));
            return jsonResult.errorMsg("user doesn't exist");}

    }


    /**
     * @description: 注册
     * @param user: 用户
     * @return boolean
     **/
    @RequestMapping(value = "/Register",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_register_info(@RequestBody UserBean user) {
        JSONResult jsonResult = new JSONResult();
        System.out.println(user);
        String id = userService.put_register_info(
                user.getName(),
                user.getPassword(),
                user.getTel(),
                user.getEmail(),
                user.getIdentity());

        //根据注册状态返回结果
        if(id!=null){
            System.out.println("register success!");
            return jsonResult.ok(id);
        }
        else{
            System.out.println("register fail!");
            System.out.println(jsonResult.errorMsg("fail to register"));
            return jsonResult.errorMsg("fail to register");
        }
    }
}