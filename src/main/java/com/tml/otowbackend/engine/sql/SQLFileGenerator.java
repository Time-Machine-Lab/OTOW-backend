package com.tml.otowbackend.engine.sql;

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
     * 根据实体信息列表生成完整的 SQL 脚本
     * @param entities 实体信息列表
     * @param dbName 数据库名称
     * @return 完整的 SQL 脚本
     */
    public static String generateDatabaseSQL(List<EntityInfo> entities, String dbName) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE IF NOT EXISTS ").append(NameConverter.escapeName(dbName)).append(";\n");
        sql.append("USE ").append(NameConverter.escapeName(dbName)).append(";\n\n");

        for (EntityInfo entity : entities) {
            sql.append(generateCreateTableSQL(entity)).append("\n\n");
        }

        return sql.toString();
    }

    /**
     * 根据单个实体信息生成建表语句
     * @param entity 实体信息
     * @return 建表 SQL
     */
    public static String generateCreateTableSQL(EntityInfo entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(NameConverter.escapeName(entity.getTableName())).append(" (\n");

        for (FieldInfo field : entity.getFields()) {
            sql.append("  ").append(NameConverter.escapeName(field.getColumnName()))
                    .append(" ").append(field.getColumnType());

            if (field.getDefaultValue() != null) {
                sql.append(" DEFAULT '").append(field.getDefaultValue()).append("'");
            }

            if (field.isPrimaryKey()) {
                if (field.isAutoIncrement()) {
                    sql.append(" AUTO_INCREMENT");
                }
                sql.append(" PRIMARY KEY");
            }

            if (field.isNotNull()) {
                sql.append(" NOT NULL");
            }

            if (field.getComment() != null) {
                sql.append(" COMMENT '").append(field.getComment()).append("'");
            }

            sql.append(",\n");
        }

        // 去掉最后一个逗号
        sql.setLength(sql.length() - 2);
        sql.append("\n)");

        // 添加表注释
        if (entity.getTableComment() != null && !entity.getTableComment().isEmpty()) {
            sql.append(" COMMENT='").append(entity.getTableComment()).append("表").append("'");
        }

        sql.append(";");

        return sql.toString();
    }
}