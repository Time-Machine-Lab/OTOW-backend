package com.tml.otowbackend.engine.generator.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换工具类
 * 支持将传入的字符串类型（如 "String"、"int"、"LocalDateTime" 等）转换为对应的包装类 Class 对象
 */
public class TypeConverter {

    // 定义一个映射关系，将字符串类型名称与对应的包装类 Class 进行映射
    private static final Map<String, Class<?>> TYPE_MAPPING = new HashMap<>();

    static {
        // 基本类型及其对应的包装类型
        TYPE_MAPPING.put("boolean", Boolean.class);
        TYPE_MAPPING.put("byte", Byte.class);
        TYPE_MAPPING.put("char", Character.class);
        TYPE_MAPPING.put("short", Short.class);
        TYPE_MAPPING.put("int", Integer.class);
        TYPE_MAPPING.put("long", Long.class);
        TYPE_MAPPING.put("float", Float.class);
        TYPE_MAPPING.put("double", Double.class);

        // 包装类型（数据库友好类型）
        TYPE_MAPPING.put("Boolean", Boolean.class);
        TYPE_MAPPING.put("Byte", Byte.class);
        TYPE_MAPPING.put("Character", Character.class);
        TYPE_MAPPING.put("Short", Short.class);
        TYPE_MAPPING.put("Integer", Integer.class);
        TYPE_MAPPING.put("Long", Long.class);
        TYPE_MAPPING.put("Float", Float.class);
        TYPE_MAPPING.put("Double", Double.class);

        // 常见类型
        TYPE_MAPPING.put("String", String.class);
        TYPE_MAPPING.put("LocalDateTime", Date.class); // 映射为数据库友好的 Date.class
        TYPE_MAPPING.put("LocalDate", Date.class); // 映射为数据库友好的 Date.class
        TYPE_MAPPING.put("Date", Date.class);
    }

    /**
     * 将字符串类型名称转换为对应的包装类 Class 对象
     *
     * @param typeName 字符串类型名称（如 "int"、"String"、"LocalDateTime"、"java.util.Date" 等）
     * @return 对应的包装类 Class 对象，如果未匹配到则返回 null
     */
    public static Class<?> toDatabaseFriendlyType(String typeName) {
        if (typeName == null || typeName.trim().isEmpty()) {
            throw new IllegalArgumentException("类型名称不能为空");
        }
        String simpleTypeName = extractSimpleName(typeName);
        return TYPE_MAPPING.get(simpleTypeName);
    }

    /**
     * 提取简单类名（去掉包路径），如 "java.util.Date" -> "Date"
     *
     * @param fullTypeName 完整类名或简单类名
     * @return 简单类名
     */
    private static String extractSimpleName(String fullTypeName) {
        if (fullTypeName.contains(".")) {
            return fullTypeName.substring(fullTypeName.lastIndexOf('.') + 1);
        }
        return fullTypeName;
    }
}