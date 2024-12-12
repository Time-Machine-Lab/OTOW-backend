package com.tml.otowbackend.engine.sql;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class NameConverter {

    /**
     * 获取表名
     *
     * @param clazz 实体类
     * @return 表名
     */
    public static String getTableName(Class<?> clazz) {
        TableName tableName = clazz.getAnnotation(TableName.class);
        return tableName != null ? tableName.value() : camelToSnake(clazz.getSimpleName());
    }

    /**
     * 将驼峰命名转换为小写下划线
     *
     * @param camelCaseName 驼峰命名
     * @return 小写下划线命名
     */
    public static String camelToSnake(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        for (char c : camelCaseName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.charAt(0) == '_' ? result.substring(1) : result.toString();
    }

    /**
     * 为表名或字段名添加反引号
     *
     * @param name 表名或字段名
     * @return 带反引号的名称
     */
    public static String escapeName(String name) {
        return "`" + name + "`";
    }
}