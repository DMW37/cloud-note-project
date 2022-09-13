package com.dmw.note.vo;

/**
 * @author: 邓明维
 * @date: 2022/9/9
 * @description:
 */

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 封装返回结果的类
 *      状态码
 *          成功=1，失败=0
 *      提示信息
 *      返回的对象（字符串、JavaBean、集合、Map等）
 */
@Data
/**
 * 链式编程
 */
@Accessors(chain = true)
public class ResultInfo<T> {

    private Integer code; // 状态码 成功=1，失败=0
    private String msg; // 提示信息
    private T result; // 返回的对象（字符串、JavaBean、集合、Map等）

}