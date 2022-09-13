package com.dmw.note.web;


import com.dmw.note.po.User;
import com.dmw.note.service.UserService;
import com.dmw.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收用户行为
        String actionName = request.getParameter("actionName");
        // 判断用户行为
        // 用户登录
        if ("login".equals(actionName)) {
            userLogin(request, response);
        }else if ("logout".equals(actionName)){
            userLogout(request,response);
        }
    }

    /**
     *         1. 销毁Session对象
     *         2. 删除Cookie对象
     *         3. 重定向跳转到登录页面
     * @param request
     * @param response
     */
    private void userLogout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        Cookie cookie = new Cookie("user",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(302);
        response.setHeader("location",request.getContextPath()+"/login.jsp");
    }

    /**
     * 1. 获取参数 （姓名、密码）
     * 2. 调用Service层的方法，返回ResultInfo对象
     * 3. 判断是否登录成功
     * 如果失败
     * 将resultInfo对象设置到request作用域中
     * 请求转发跳转到登录页面
     * 如果成功
     * 将用户信息设置到session作用域中
     * 判断用户是否选择记住密码（rem的值是1）
     * 如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
     * 如果否，清空原有的cookie对象
     * 重定向跳转到index页面
     *
     * @param request
     * @param response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取参数 （姓名、密码）
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2.调用Service层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo = userService.userLogin(username, password);

        // 3.判断是否登录成功
        if (resultInfo.getCode() == 1) {
            // 成功
            // 将用户信息设置到session作用域中
            request.getSession().setAttribute("user",resultInfo.getResult());
            // 判断用户是否选择记住密码（rem的值是1）
            String rem = request.getParameter("rem");
            // 如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
            if ("1".equals(rem)){
                Cookie cookie =new Cookie("user",username+"_"+password);
                // 设置失效时间
                cookie.setMaxAge(1*60*60);
                response.addCookie(cookie);
            }else {
                // 如果否，清空原有的cookie对象
                Cookie cookie  = new Cookie("user",null);
                // 删除cookie
                cookie.setMaxAge(0);
                // 响应给客户端
                response.addCookie(cookie);
            }

            // 重定向跳转到index页面,进入主页的控制器
            response.sendRedirect("index");
        } else {
            // 失败
            // 将resultInfo对象设置到request作用域中
            request.setAttribute("resultInfo", resultInfo);
            // 转发
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }
}
