package com.dmw.note.dao;

import com.dmw.note.po.User;
import org.junit.Test;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */
public class UserDaoTest {

    @Test
    public void queryUserByName() {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user);
    }
}