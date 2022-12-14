package com.dmw.note.dao;

import com.dmw.note.po.User;
import com.dmw.note.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */
public class UserDao {
    /**
     * logger: slf4j日志对象
     */
    private Logger logger = LoggerFactory.getLogger(UserDao.class);

    public User queryUserByName(String username) {
        String sql = "select userId,uname,upwd,nick,head,mood from tb_user where uname =?";
        List<Object> params = new ArrayList<>();
        params.add(username);
        User user = (User) BaseDao.queryRow(sql, params, User.class);
        return user;
    }

    /**
     * 根据用户名查询用户,用户名查询对象唯一
     *
     * @param username
     * @return
     */
    public User queryUserByName_re(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select userId,uname,upwd,nick,head,mood from tb_user where uname =?";
            logger.info("queryUserByName=>{}", sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(resultSet.getString("uname"));
                user.setUpwd(resultSet.getString("upwd"));
                user.setNick(resultSet.getString("nick"));
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }
        return user;
    }

    /**
     * 1. 定义SQL语句
     * 通过用户ID查询除了当前登录用户之外是否有其他用户使用了该昵称
     * 指定昵称  nick （前台传递的参数）
     * 当前用户  userId （session作用域中的user对象）
     * String sql = "select * from tb_user where nick = ? and userId != ?";
     * 2. 设置参数集合
     * 3. 调用BaseDao的查询方法
     *
     * @param userId
     * @param nick
     * @return
     */
    public User queryUserByUserIdAndNick(Integer userId, String nick) {
        String sql = "select userId,nick from tb_user where userId = ? and nick = ?";
        List params = new ArrayList();
        params.add(userId);
        params.add(nick);
        return (User) BaseDao.queryRow(sql, params, User.class);
    }

    public int updateNickByUserId(Integer userId, String nick) {
        String sql = "update tb_user set nick = ? where userId = ?";
        List params = new ArrayList();
        params.add(nick);
        params.add(userId);
        return BaseDao.executeUpdate(sql, params);
    }

    /**
     * 1. 定义SQL语句
     * String sql = "update tb_user set nick = ?, mood = ?, head = ? where userId = ? ";
     * 2. 设置参数集合
     * 3. 调用BaseDao的更新方法，返回受影响的行数
     * 4. 返回受影响的行数
     *
     * @param user
     * @return
     */
    public int updateUser(User user) {
        String sql = "update tb_user set nick = ?, mood = ?, head = ? where userId = ? ";
        List params = new ArrayList();
        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());
        params.add(user.getUserId());
        return BaseDao.executeUpdate(sql, params);
    }
}
