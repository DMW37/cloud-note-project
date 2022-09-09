package com.dmw.note.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */
@Slf4j
public class DBUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            InputStream rs = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            PROPERTIES.load(rs);
            Class.forName(PROPERTIES.getProperty("jdbc.driver"));
        } catch (Exception e) {
            log.error("数据库驱动异常");
        }
    }

    /**
     * 获取连接方法
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(PROPERTIES.getProperty("jdbc.url"), PROPERTIES.getProperty("jdbc.username"), PROPERTIES.getProperty("jdbc.password"));
        } catch (SQLException throwables) {
            log.error("获取数据库连接异常");
        }
        return connection;
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                log.error("resultSet关闭资源异常");
            }
        }
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                log.error("statement关闭资源异常");
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.error("connection关闭资源异常");
            }
        }
    }

}
