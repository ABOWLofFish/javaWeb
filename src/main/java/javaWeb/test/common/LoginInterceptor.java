package javaWeb.test.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 注册登录的拦截器
 * @author:庄婉如
 * @filter: expect /&/Register
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    //预处理回调方法
    public boolean preHandler(HttpServletRequest request, HttpServletResponse response,Object handler)
        throws Exception{
        Object user = request.getSession().getAttribute("user");
        //验证user的session状态，如果有登录信息可以继续访问，没有则返回无权限操作的提示
        if(user==null){
            request.setAttribute("msg","请先登陆后再进行访问！");
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
        return true;
    }
}

