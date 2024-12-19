package com.tml.otowbackend.engine.sql;

import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import lombok.Data;

/**
 * 描述: 字段信息类
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

    /**
     * 静态构建器，简化外部构造
     */
    public static class Builder {
        private final FieldInfo fieldInfo;

        public Builder(String columnName, String columnType) {
            this.fieldInfo = new FieldInfo();
            this.fieldInfo.setColumnName(columnName);
            this.fieldInfo.setColumnType(columnType);
        }

        public Builder defaultValue(String defaultValue) {
            this.fieldInfo.setDefaultValue(defaultValue);
            return this;
        }

        public Builder primaryKey(boolean primaryKey) {
            this.fieldInfo.setPrimaryKey(primaryKey);
            return this;
        }

        public Builder autoIncrement(boolean autoIncrement) {
            this.fieldInfo.setAutoIncrement(autoIncrement);
            return this;
        }

        public Builder notNull(boolean notNull) {
            this.fieldInfo.setNotNull(notNull);
            return this;
        }

        public Builder comment(String comment) {
            this.fieldInfo.setComment(comment);
            return this;
        }

        public FieldInfo build() {
            return this.fieldInfo;
        }
    }
}