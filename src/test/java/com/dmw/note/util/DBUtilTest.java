package com.dmw.note.util;

import org.junit.Test;

import java.sql.Connection;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */
public class DBUtilTest {
    @Test
    public void testGetConection() {
        Connection connection = DBUtil.getConnection();
        DBUtil.close(connection, null, null);
    }
}