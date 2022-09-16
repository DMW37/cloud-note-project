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

    /**
     * 通过类型ID删除指定的类型记录，返回受影响的行数
     * @param typeId
     * @return
     */
    public int deleteTypeById(String typeId) {
        // 定义SQL语句
        String sql = "delete from tb_note_type where typeId = ?";
        // 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        // 调用BaseDao
        int row = BaseDao.executeUpdate(sql, params);
        return row;
    }

    /**
     * 通过类型ID查询云记记录的数量，返回云记数量
     *
     * @param typeId
     * @return
     */
    public long findNoteCountByTypeId(String typeId) {
        // 定义SQL语句
        String sql = "select count(1) from tb_note where typeId = ?";
        // 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        // 调用BaseDao
        long count = (long) BaseDao.findSingleValue(sql, params);
        return count;
    }
}
