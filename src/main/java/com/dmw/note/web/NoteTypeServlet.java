package com.dmw.note.web;

import com.dmw.note.po.NoteType;
import com.dmw.note.po.User;
import com.dmw.note.service.NoteTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: 邓明维
 * @date: 2022/9/16
 * @description:
 */
@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {
    private NoteTypeService noteTypeService = new NoteTypeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航的高亮值
        request.setAttribute("menu_page", "type");
        // 得到用户行为
        String actionName = request.getParameter("actionName");
        // 判断用户行为
        if ("list".equals(actionName)) {
            // 查询类型列表
            typeList(request, response);
        } else if ("delete".equals(actionName)) {
            // 删除类型
            // deleteType(request, response);
        } else if ("addOrUpdate".equals(actionName)) {
            // 添加或修改类型
            //  addOrUpdate(request, response);
        }
    }

    /**
     * 查询类型列表
     * 1. 获取Session作用域设置的user对象
     * 2. 调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
     * 3. 将类型列表设置到request请求域中
     * 4. 设置首页动态包含的页面值
     * 5. 请求转发跳转到index.jsp页面
     *
     * @param request
     * @param response
     */
    private void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取Session作用域设置的user对象
        User user = (User) request.getSession().getAttribute("user");
        // 2. 调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
        List<NoteType> typeList = noteTypeService.findTypeList(user.getUserId());
        // 3. 将类型列表设置到request请求域中
        request.setAttribute("typeList", typeList);
        // 4. 设置首页动态包含的页面值
        request.setAttribute("changePage", "type/list.jsp");
        // 5. 请求转发跳转到index.jsp页面
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
