package com.tml.otowbackend.engine.sql;

import io.github.geniusay.template.sql.annotation.ColumnLength;
import io.github.geniusay.template.sql.annotation.TextType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldMapper {

    // Java 类型到 SQL 类型的映射
    private static final Map<Class<?>, String> JAVA_TO_SQL_TYPE = new HashMap<>();

    static {
        JAVA_TO_SQL_TYPE.put(String.class, "VARCHAR(255)");
        JAVA_TO_SQL_TYPE.put(Integer.class, "INT");
        JAVA_TO_SQL_TYPE.put(Long.class, "BIGINT");
        JAVA_TO_SQL_TYPE.put(Boolean.class, "BIT");
        JAVA_TO_SQL_TYPE.put(boolean.class, "BIT");
        JAVA_TO_SQL_TYPE.put(Double.class, "DOUBLE");
        JAVA_TO_SQL_TYPE.put(Float.class, "FLOAT");
        JAVA_TO_SQL_TYPE.put(java.util.Date.class, "DATETIME");
        JAVA_TO_SQL_TYPE.put(java.time.LocalDate.class, "DATE");
        JAVA_TO_SQL_TYPE.put(java.time.LocalDateTime.class, "TIMESTAMP");
    }

    /**
     * 映射字段的 Java 类型到 SQL 类型
     *
     * @param field 字段
     * @return SQL 类型
     */
    public static String mapJavaTypeToSQLType(Field field) {
        if (field.isAnnotationPresent(TextType.class)) {
            return "LONGTEXT";
        }

        Class<?> fieldType = field.getType();
        String sqlType = JAVA_TO_SQL_TYPE.get(fieldType);

        if (sqlType == null) {
            throw new UnsupportedOperationException("Unsupported field type: " + fieldType.getName());
        }

        // 动态处理 String 类型的长度
        if (fieldType == String.class && field.isAnnotationPresent(ColumnLength.class)) {
            int length = field.getAnnotation(ColumnLength.class).value();
            return length > 512 ? "TEXT" : "VARCHAR(" + length + ")";
        }

        return sqlType;
    }
}