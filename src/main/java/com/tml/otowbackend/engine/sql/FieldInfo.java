package com.tml.otowbackend.engine.sql;

import lombok.Data;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class FieldInfo {

    private String columnName;    // 字段名
    private String columnType;    // 字段类型
    private String defaultValue;  // 默认值
    private boolean primaryKey;   // 是否主键
    private boolean autoIncrement; // 是否自增
    private boolean notNull;      // 是否非空
    private String comment;       // 字段注释
}