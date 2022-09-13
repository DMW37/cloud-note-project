package com.dmw.note.web;


import com.dmw.note.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();


}
