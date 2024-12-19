package com.tml.otowbackend;

import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.sql.*;
import org.junit.Test;
import java.util.List;

import static com.tml.otowbackend.constants.DatabaseConstant.DB_NAME_PREFIX;

public class SqlGeneratorTest {

    String filePath = "entity_class_definitions.json";

    @Test
    public void testGenerateSql() {
        String dbName = DB_NAME_PREFIX + "test_db"; // 数据库名称
        List<EntityClassDefinition> entityClassDefinitions = AIGenerateTest.readEntityClassDefinitionsFromFile(filePath);
        assert entityClassDefinitions != null;
        List<EntityInfo> entityInfos = EntityInfo.buildEntityInfoList(entityClassDefinitions);
        String sql = SQLManager.generateSQLFromEntities(entityInfos, dbName);
        System.out.println(sql);
    }

    @Test
    public void generateAndImportSql() {
        // 配置参数
        String packageName = "com.tml.otowbackend.pojo"; // 实体类包名
        String dbName = DB_NAME_PREFIX + "test_db"; // 数据库名称
        String dbUrl = "jdbc:mysql://127.0.0.1:3306?useSSL=false&serverTimezone=UTC"; // 数据库连接 URL
        String username = "root"; // 数据库用户名
        String password = "369202865"; // 数据库密码
        String outputFilePath = "D:\\test_db.sql"; // SQL 文件路径

        List<EntityInfo> entities = EntityScanner.buildFromPackage(packageName);
        String sql = SQLManager.generateSQLFromEntities(entities, dbName);
        System.out.println(sql);
    }


    @Test
    public void generateSqlFile() {
        // 定义参数
        String packageName = "com.tml.otowbackend.pojo";
        String dbName = "test_db";
        String outputFilePath = "D:\\test_db.sql";

        // 生成完整的 SQL 脚本
        List<EntityInfo> entities = EntityScanner.buildFromPackage(packageName);
        String sqlScript = SQLFileGenerator.generateDatabaseSQL(entities, dbName);

        // 将 SQL 写入文件
        SQLFileGenerator.writeSQLToFile(sqlScript, outputFilePath);

        System.out.println("SQL 文件已生成: " + outputFilePath);
    }

    @Test
    public void importSqlFile() {
        // 数据库连接信息
        String dbUrl = "jdbc:mysql://127.0.0.1:3306?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "369202865";

        // SQL 文件路径
        String sqlFilePath = "D:\\test_db.sql";

        // 执行 SQL 文件
        try {
            String sql = SQLExecutor.loadSQLFile(sqlFilePath);
            SQLExecutor.executeSQL(dbUrl, username, password, sql);
            System.out.println("SQL 文件已成功执行！");
        } catch (Exception e) {
            System.err.println("执行 SQL 文件失败: " + e.getMessage());
        }
    }
}
