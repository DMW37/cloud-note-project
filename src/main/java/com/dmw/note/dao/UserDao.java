package com.dmw.note.dao;

import com.dmw.note.po.User;
import com.dmw.note.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */
public class UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDao.class);

    /**
     * 根据用户名查询用户,用户名查询对象唯一
     * @param username
     * @return
     */
    public User queryUserByName(String username) {
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return user;
    }

}
