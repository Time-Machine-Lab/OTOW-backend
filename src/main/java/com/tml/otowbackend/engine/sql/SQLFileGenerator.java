package com.tml.otowbackend.engine.sql;
import com.tml.otowbackend.util.EntityScanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SQLFileGenerator {

    /**
     * 将 SQL 脚本写入文件
     *
     * @param sql           SQL 脚本
     * @param outputFilePath 输出的 SQL 文件路径
     */
    public static void writeSQLToFile(String sql, String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(sql);
        } catch (IOException e) {
            throw new RuntimeException("SQL 文件生成失败: " + outputFilePath + ", 原因: " + e.getMessage(), e);
        }
    }

    /**
     * 组合建库语句和建表语句
     *
     * @param packageName 实体类所在的包名
     * @param dbName      数据库名称
     * @return 完整的 SQL 脚本
     */
    public static String generateDatabaseSQL(String packageName, String dbName) {
        List<Class<?>> entityClasses = EntityScanner.scanEntities(packageName);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE IF NOT EXISTS ").append(NameConverter.escapeName(dbName)).append(";\n");
        sql.append("USE ").append(NameConverter.escapeName(dbName)).append(";\n\n");

        for (Class<?> entityClass : entityClasses) {
            sql.append(generateCreateTableSQL(entityClass)).append("\n\n");
        }

        return sql.toString();
    }

    /**
     * 生成单个表的建表 SQL
     *
     * @param clazz 实体类的 Class 对象
     * @return 建表 SQL
     */
    public static String generateCreateTableSQL(Class<?> clazz) {
        String tableName = NameConverter.getTableName(clazz);
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(NameConverter.escapeName(tableName)).append(" (\n");

        EntityProcessor.getFields(clazz).forEach(field -> {
            sql.append("  ").append(NameConverter.escapeName(field.getColumnName()))
                    .append(" ").append(field.getColumnType());

            // 默认值
            if (field.getDefaultValue() != null) {
                sql.append(" DEFAULT '").append(field.getDefaultValue()).append("'");
            }

            // 主键
            if (field.isPrimaryKey()) {
                if (field.isAutoIncrement()) {
                    sql.append(" AUTO_INCREMENT");
                }
                sql.append(" PRIMARY KEY");
            }

            // 非空约束
            if (field.isNotNull()) {
                sql.append(" NOT NULL");
            }

            // 注释
            if (field.getComment() != null) {
                sql.append(" COMMENT '").append(field.getComment()).append("'");
            }

            sql.append(",\n");
        });

        // 去掉最后一个逗号
        sql.setLength(sql.length() - 2);
        sql.append("\n);");

        return sql.toString();
    }
}
