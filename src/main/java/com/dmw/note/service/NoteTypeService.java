package com.dmw.note.service;

import com.dmw.note.dao.NoteTypeDao;
import com.dmw.note.po.NoteType;

import java.util.List;

/**
 * @author: 邓明维
 * @date: 2022/9/16
 * @description:
 */
public class NoteTypeService {
    private NoteTypeDao noteTypeDao = new NoteTypeDao();

    /**
     * 查询类型列表
     * 1. 调用Dao层的查询方法，通过用户ID查询类型集合
     * 2. 返回类型集合
     *
     * @param userId
     * @return
     */
    public List<NoteType> findTypeList(Integer userId) {
        List<NoteType> typeList = noteTypeDao.findTypeListByUserId(userId);
        return typeList;
    }
}
