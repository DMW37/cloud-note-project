package com.dmw.note.dao;

import com.dmw.note.po.NoteType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 邓明维
 * @date: 2022/9/16
 * @description:
 */
public class NoteTypeDao {
    /**
     * 通过用户ID查询类型集合
     1. 定义SQL语句
     String sql = "select typeId,typeName,userId from tb_note_type where userId = ? ";
     2. 设置参数列表
     3. 调用BaseDao的查询方法，返回集合
     4. 返回集合
     * @param userId
     * @return
     */
    public List<NoteType> findTypeListByUserId(Integer userId) {
        // 1. 定义SQL语句
        String sql = "select typeId,typeName,userId from tb_note_type where userId = ? ";
        // 2. 设置参数列表
        List<Object> params = new ArrayList<>();
        params.add(userId);
        // 3. 调用BaseDao的查询方法，返回集合
        List<NoteType> list = BaseDao.queryRows(sql, params, NoteType.class);
        // 4. 返回集合
        return list;
    }
}
