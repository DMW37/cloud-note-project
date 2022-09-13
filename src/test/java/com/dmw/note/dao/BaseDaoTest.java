package com.dmw.note.dao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: 邓明维
 * @date: 2022/9/13
 * @description:
 */
public class BaseDaoTest {

    @Test
    public void executeUpdate() {
        String sql = "insert into tb_user (uname,upwd,nick,head,mood) values(?,?,?,?,?)";
        List<Object> params = new ArrayList<>();
        params.add("lisi");
        params.add("e10adc3949ba59abbe56e057f20f883e");
        params.add("里斯");
        params.add("520.png");
        params.add("Hello");
        System.out.println(BaseDao.executeUpdate(sql, params));
    }
}