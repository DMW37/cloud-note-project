package com.dmw.note.po;

import lombok.Data;

@Data
public class User {
    /**
     * 用户ID，主键
     * 用户名称
     * 用户密码
     * 用户昵称
     * 用户头像
     * 用户心情
     */
    private Integer userId;
    private String uname;
    private String upwd;
    private String nick;
    private String head;
    private String mood;
}