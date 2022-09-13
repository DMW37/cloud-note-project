package com.dmw.note.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 邓明维
 * @date: 2022/9/14
 * @description: 主页控制
 */
@WebServlet(name = "indexServlet", value = "/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("changePage", "note/list.jsp");
        request.getRequestDispatcher(request.getContextPath() + "/index.jsp").forward(request, response);
    }
}
