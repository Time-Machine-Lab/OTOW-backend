package com.tml.otowbackend.engine.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SQLExecutor {

    /**
     * 执行指定 SQL 文件
     *
     * @param dbUrl      数据库连接 URL
     * @param username   数据库用户名
     * @param password   数据库密码
     * @param sqlFilePath SQL 文件路径
     */
    public static void executeSQLFile(String dbUrl, String username, String password, String sqlFilePath) {
        try {
            // 显式加载 MySQL 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
                connection.setAutoCommit(false); // 开启事务

                try (Statement statement = connection.createStatement()) {
                    // 加载并执行 SQL 文件
                    String sqlScript = loadSQLFile(sqlFilePath);
                    String[] sqlStatements = sqlScript.split(";");
                    for (String sql : sqlStatements) {
                        if (sql.trim().isEmpty()) {
                            continue; // 跳过空语句
                        }
                        statement.execute(sql.trim());
                        System.out.println("执行成功: " + sql.trim());
                    }

                    connection.commit(); // 提交事务
                    System.out.println("SQL 文件执行完成，事务已提交。");

                } catch (SQLException e) {
                    connection.rollback(); // 回滚事务
                    throw new RuntimeException("SQL 执行失败，事务已回滚。原因: " + e.getMessage(), e);
                }

            } catch (SQLException e) {
                throw new RuntimeException("数据库连接失败: " + e.getMessage(), e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC 驱动未找到，请检查依赖配置。", e);
        }
    }

    /**
     * 加载 SQL 文件内容
     *
     * @param sqlFilePath SQL 文件路径
     * @return SQL 文件内容
     */
    private static String loadSQLFile(String sqlFilePath) {
        StringBuilder sqlBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("加载 SQL 文件失败: " + e.getMessage(), e);
        }
        return sqlBuilder.toString();
    }
}