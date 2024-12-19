package com.tml.otowbackend.engine.sql;

import com.tml.otowbackend.engine.generator.utils.TypeConverter;
import com.tml.otowbackend.engine.sql.annotation.ColumnLength;
import com.tml.otowbackend.engine.sql.annotation.TextType;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 字段类型映射工具类
 * 负责将 Java 类型或自定义字段类型 (ftype) 映射为 SQL 数据库类型
 */
public class FieldMapper {

    // Java 类型到 SQL 类型的映射
    private static final Map<Class<?>, String> JAVA_TO_SQL_TYPE = new HashMap<>();

    static {
        // 初始化 Java 类型到 SQL 类型的映射
        JAVA_TO_SQL_TYPE.put(String.class, "VARCHAR(255)");
        JAVA_TO_SQL_TYPE.put(Integer.class, "INT");
        JAVA_TO_SQL_TYPE.put(Long.class, "BIGINT");
        JAVA_TO_SQL_TYPE.put(Boolean.class, "BIT");
        JAVA_TO_SQL_TYPE.put(boolean.class, "BIT");
        JAVA_TO_SQL_TYPE.put(Double.class, "DOUBLE");
        JAVA_TO_SQL_TYPE.put(Float.class, "FLOAT");
        JAVA_TO_SQL_TYPE.put(Date.class, "DATETIME");
        JAVA_TO_SQL_TYPE.put(LocalDate.class, "DATE");
        JAVA_TO_SQL_TYPE.put(LocalDateTime.class, "TIMESTAMP");
    }

    /**
     * 映射字段的 Java 类型到 SQL 类型
     *
     * @param field Java 字段
     * @return SQL 类型
     */
    public static String mapJavaTypeToSQLType(Field field) {
        if (field.isAnnotationPresent(TextType.class)) {
            return "LONGTEXT";
        }

        Class<?> fieldType = field.getType();
        String sqlType = JAVA_TO_SQL_TYPE.get(fieldType);

        if (sqlType == null) {
            throw new UnsupportedOperationException("Unsupported Java field type: " + fieldType.getName());
        }

        // 动态处理 String 类型的长度
        if (fieldType == String.class && field.isAnnotationPresent(ColumnLength.class)) {
            int length = field.getAnnotation(ColumnLength.class).value();
            return length > 512 ? "TEXT" : "VARCHAR(" + length + ")";
        }

        return sqlType;
    }

    /**
     * 映射自定义字段类型 (ftype) 到 SQL 类型
     * 使用 TypeConverter 将 ftype 转换为包装类，然后基于 JAVA_TO_SQL_TYPE 映射到 SQL 类型
     *
     * @param ftype 自定义字段类型 (如 "string", "int", "LocalDateTime")
     * @return SQL 类型
     */
    public static String mapFTypeToSQLType(String ftype) {
        // 使用 TypeConverter 将 ftype 转换为包装类
        Class<?> javaType = TypeConverter.toDatabaseFriendlyType(ftype);
        if (javaType == null) {
            throw new UnsupportedOperationException("Unsupported ftype: " + ftype);
        }

        // 根据包装类映射到 SQL 类型
        String sqlType = JAVA_TO_SQL_TYPE.get(javaType);
        if (sqlType == null) {
            throw new UnsupportedOperationException("Unsupported Java type for ftype: " + javaType.getName());
        }

        return sqlType;
    }
}