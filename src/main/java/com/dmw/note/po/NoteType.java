package com.dmw.note.po;

import lombok.Data;

@Data
public class NoteType {

    private Integer typeId; // 类型ID
    private String typeName; // 类型名称
    private Integer userId; // 用户ID

}
