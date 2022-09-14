package com.dmw.note.web;


import com.dmw.note.po.User;
import com.dmw.note.service.UserService;
import com.dmw.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航高亮
        request.setAttribute("menu_page","user");
        // 接收用户行为
        String actionName = request.getParameter("actionName");
        // 判断用户行为
        // 用户登录
        if ("login".equals(actionName)) {
            userLogin(request, response);
        }else if ("logout".equals(actionName)){
            userLogout(request,response);
        }else if ("userCenter".equals(actionName)){
            userCenter(request,response);
        }else if ("userHead".equals(actionName)){
            userHead(request,response);
        }else if ("checkUniqueNick".equals(actionName)){
            checkUniqueNick(request,response);
        }
        else if ("updataUser".equals(actionName)){
            updataUser(request,response);
        }
    }

    /**
     *    注：文件上传必须在Servlet类上提那家注解！！！ @MultipartConfig
     *             1. 调用Service层的方法，传递request对象作为参数，返回resultInfo对象
     *             2. 将resultInfo对象存到request作用域中
     *             3. 请求转发跳转到个人中心页面 （user?actionName=userCenter）
     * @param request
     * @param response
     */
    private void updataUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1. 调用Service层的方法，传递request对象作为参数，返回resultInfo对象
        ResultInfo<User> resultInfo = userService.updataUser(request);
//        2. 将resultInfo对象存到request作用域中
        request.setAttribute("resultInfo",resultInfo);
//        3. 请求转发跳转到个人中心页面 （user?actionName=userCenter）
        request.getRequestDispatcher("user?actionName=userCenter").forward(request,response);
    }

    /**
     *             1. 获取参数（昵称）
     *             2. 从session作用域获取用户对象，得到用户ID
     *             3. 调用Service层的方法，得到返回的结果
     *             4. 通过字符输出流将结果响应给前台的ajax的回调函数
     *             5. 关闭资源
     * @param request
     * @param response
     */
    private void checkUniqueNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        1. 获取参数（昵称）
        String nick = request.getParameter("nick");
//        2. 从session作用域获取用户对象，得到用户ID
        User user = (User) request.getSession().getAttribute("user");
//        3. 调用Service层的方法，得到返回的结果
       Integer code = userService.checkUniqueNick(user.getUserId(),nick);
       if (code==1){
           // 修改session
           user.setNick(nick);
           request.getSession().setAttribute("user",user);
       }
//        4. 通过字符输出流将结果响应给前台的ajax的回调函数
        PrintWriter writer = response.getWriter();
        writer.write(String.valueOf(code));
//        5. 关闭资源
        writer.close();

    }

    /**
     *   1. 获取参数 （图片名称）
     *         2. 得到图片的存放路径 （request.getServletContext().getealPathR("/")）
     *         3. 通过图片的完整路径，得到file对象
     *         4. 通过截取，得到图片的后缀
     *         5. 通过不同的图片后缀，设置不同的响应的类型
     *         6. 利用FileUtils的copyFile()方法，将图片拷贝给浏览器
     * @param request
     * @param response
     */
    private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("user")!=null){
            String imageName = request.getParameter("imageName");
            String realPath = getServletContext().getRealPath("/WEB-INF/upload/");
            File file = new File(realPath+"/"+imageName);
            // 获取文件MIME类型
            String mimeType = getServletContext().getMimeType(imageName);
            // 响应数据
            response.setContentType(mimeType+";charset=UTF-8");
            // 使用FileUtils.copyFile(),将图片传递给浏览器
            FileUtils.copyFile(file,response.getOutputStream());
        }

    }

    /**
     * 进入个人中心
     *  1.设置首页包含的页面值
     *  2.请求转发到index
     * @param request
     * @param response
     */
    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("changePage",request.getContextPath()+"/user/info.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
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
