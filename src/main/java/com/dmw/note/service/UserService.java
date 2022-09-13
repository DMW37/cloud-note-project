package com.dmw.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.dmw.note.dao.UserDao;
import com.dmw.note.po.User;
import com.dmw.note.vo.ResultInfo;

public class UserService {

    private UserDao userDao = new UserDao();

    /**
     * 用户登录
     * 1. 判断参数是否为空
     * 如果为空
     * 设置ResultInfo对象的状态码和提示信息
     * 返回resultInfo对象
     * 2. 如果不为空，通过用户名查询用户对象
     * 3. 判断用户对象是否为空
     * 如果为空
     * 设置ResultInfo对象的状态码和提示信息
     * 返回resultInfo对象
     * 4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
     * 如果密码不正确
     * 设置ResultInfo对象的状态码和提示信息
     * 返回resultInfo对象
     * 5. 如果密码正确
     * 设置ResultInfo对象的状态码和提示信息
     * 6. 返回resultInfo对象
     *
     * @param username
     * @param password
     * @return
     */
    public ResultInfo<User> userLogin(String username, String password) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        // 失败时数据回显
        User user = new User();
        user.setUname(username);
        user.setUpwd(password);
        resultInfo.setResult(user);
        // 1. 判断参数是否为空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            resultInfo.setCode(0).setMsg("用户名或密码不能为空");
            // 返回
            return resultInfo;
        }

        // 2.如果不为空，通过用户名查询用户对象
        user = userDao.queryUserByName(username);

        // 3.判断用户对象是否为空
        if (user == null) {
            resultInfo.setCode(0).setMsg("用户不存在!");
            return resultInfo;
        }

        // 4.如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
        // MD5加密算法
        password = DigestUtil.md5Hex(password);
        // 密码不正确
        if (!user.getUpwd().equals(password)) {
            resultInfo.setCode(0).setMsg("用户密码不正确！");
            return resultInfo;
        }

        // 正确
        resultInfo.setCode(1).setResult(user);
        return resultInfo;
    }
}
