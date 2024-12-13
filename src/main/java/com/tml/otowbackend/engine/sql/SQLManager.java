package com.tml.otowbackend.engine.sql;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SQLManager {

    /**
     * 生成 SQL 文件并导入到数据库
     * @param packageName    实体类所在的包名
     * @param dbName         数据库名称
     * @param dbUrl          数据库连接 URL
     * @param username       数据库用户名
     * @param password       数据库密码
     * @param outputFilePath 输出的 SQL 文件路径
     */
    public static void generateAndImportSQL(String packageName, String dbName, String dbUrl, String username, String password, String outputFilePath) {
        try {
            // 1. 生成 SQL 文件
            System.out.println("开始生成 SQL 文件...");
            String sqlScript = SQLFileGenerator.generateDatabaseSQL(packageName, dbName);
            SQLFileGenerator.writeSQLToFile(sqlScript, outputFilePath);
            System.out.println("SQL 文件已生成: " + outputFilePath);

            // 2. 导入 SQL 文件到数据库
            System.out.println("开始导入 SQL 文件到数据库...");
            SQLExecutor.executeSQLFile(dbUrl, username, password, outputFilePath);
            System.out.println("SQL 文件已成功导入到数据库！");
        } catch (Exception e) {
            System.err.println("生成或导入 SQL 文件失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}