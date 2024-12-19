package com.tml.otowbackend.engine.sql;

import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SQLManager {

    /**
     * 根据用户自定义的实体信息生成 SQL 脚本
     * @param entities 用户自定义的实体信息列表
     * @param dbName 数据库名称
     * @return 生成的 SQL 脚本
     */
    public static String generateSQLFromEntities(List<EntityInfo> entities, String dbName) {
        return SQLFileGenerator.generateDatabaseSQL(entities, dbName);
    }

    /**
     * 将 SQL 脚本写入文件
     * @param sql SQL 脚本
     * @param outputFilePath 输出的 SQL 文件路径
     */
    public static void writeSQLToFile(String sql, String outputFilePath) {
        SQLFileGenerator.writeSQLToFile(sql, outputFilePath);
    }

    /**
     * 将 SQL 脚本导入到数据库
     * @param dbUrl 数据库连接 URL
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param sql SQL 脚本
     */
    public static void importSQLToDatabase(String dbUrl, String username, String password, String sql) {
        SQLExecutor.executeSQL(dbUrl, username, password, sql);
    }
}