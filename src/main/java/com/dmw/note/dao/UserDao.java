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

}
