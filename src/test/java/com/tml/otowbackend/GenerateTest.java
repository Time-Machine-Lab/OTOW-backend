package com.tml.otowbackend;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.generator.core.FunctionPackManager;
import com.tml.otowbackend.engine.generator.core.ConfigTemplateFactory;
import com.tml.otowbackend.engine.generator.core.ClassTemplateFactory;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.SwaggerMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import com.tml.otowbackend.engine.generator.utils.TypeConverter;
import com.tml.otowbackend.engine.sql.EntityInfo;
import com.tml.otowbackend.engine.sql.SQLManager;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;
import com.tml.otowbackend.engine.tree.template.SpringBootTreeTemplate;
import com.tml.otowbackend.engine.tree.utils.PathUtils;
import com.tml.otowbackend.pojo.DTO.SwaggerInfo;
import com.tml.otowbackend.util.CodeFormatterUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static com.tml.otowbackend.constants.DatabaseConstant.DB_NAME_PREFIX;
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.engine;
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.reqPackagePath;
import static com.tml.otowbackend.engine.generator.utils.MetalUtils.getDescribe;
import static com.tml.otowbackend.util.CodeFormatterUtil.formatCodeList;
import static org.apache.naming.SelectorContext.prefix;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@Slf4j
public class GenerateTest {

    @Resource
    FunctionPackManager functionPackManager;

    String filePath = "entity_class_definitions.json";

    @Resource
    private IVirtualFileService virtualFileService;

    @Test
    public void testGenerateAll() throws Exception {
        String prefix = "com.example";
        String treeId = virtualFileService.initializeVirtualTree(PathUtils.getSpringbootPath(), null);
        SpringBootTreeTemplate template = new SpringBootTreeTemplate(virtualFileService, treeId);
        template.initializeTemplate();

        // 生成主体类
        List<EntityClassDefinition> entityClassDefinitions = AIGenerateTest.readEntityClassDefinitionsFromFile(filePath);
        if (entityClassDefinitions != null) {
            for (EntityClassDefinition definition : entityClassDefinitions) {
                String className = definition.getClassName();
                String tableName = StringUtils.camelToUnderline(className);
                String describe = definition.getCdesc();

                LinkedList<MetalField> fields = new LinkedList<>();
                for (EntityClassDefinition.FieldDefinition field : definition.getFields()) {
                    System.out.println(field);
                    fields.add(new MetalField(field.getFname(), TypeConverter.toDatabaseFriendlyType(field.getFtype()), getDescribe(field.getFdesc())));
                }
                ClassTemplateFactory classTemplateFactory = new ClassTemplateFactory(prefix, functionPackManager, className, tableName, describe, fields, definition.getFeatureIds());
                classTemplateFactory.initTemplate();

                template.entity(className + "Entity.java", formatCodeList(classTemplateFactory.generateEntity()));

                template.mapper(className + "Mapper.java", formatCodeList(classTemplateFactory.generateMapper()));

                template.service(className + "Service.java", formatCodeList(classTemplateFactory.generateService()));

                template.serviceImpl(className + "ServiceImpl.java", formatCodeList(classTemplateFactory.generateServiceImpl()));

                template.controller(className + "Controller.java", formatCodeList(classTemplateFactory.generateController()));

                template.req(className + "Req.java", formatCodeList(classTemplateFactory.generateEntityReq()));

                template.vo(className + "VO.java", formatCodeList(classTemplateFactory.generateEntityVO()));
            }
        }

        // 生成数据库文件
        String dbName = DB_NAME_PREFIX + "test_db"; // 数据库名称
        String ip = "127.0.0.1";
        String importUrl = "jdbc:mysql://" + ip + ":3306/" + "?useSSL=false&serverTimezone=UTC";
        String dbUrl = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        String username = "root"; // 数据库用户名
        String password = "369202865"; // 数据库密码
        assert entityClassDefinitions != null;
        List<EntityInfo> entityInfos = EntityInfo.buildEntityInfoList(entityClassDefinitions);
        String sql = SQLManager.generateSQLFromEntities(entityInfos, dbName);

        template.master(dbName + ".sql", formatCodeList(sql));

        // 是否导入到数据库中
        SQLManager.importSQLToDatabase(importUrl, username, password, sql);

        // 生成配置文件
        ConfigTemplateFactory configTemplateFactory = new ConfigTemplateFactory();
        Map<String, Object> pomConfigDependency = new HashMap<>();
        pomConfigDependency.put("groupId", "org.springframework.boot");
        pomConfigDependency.put("artifactId", "spring-boot-starter-test");
        pomConfigDependency.put("version", "${boot.version}");
        configTemplateFactory.addPomDependency(pomConfigDependency);

        Map<String, Object> pomConfigProperty = new HashMap<>();
        pomConfigProperty.put("name", "boot.version");
        pomConfigProperty.put("value", "2.6.6");
        configTemplateFactory.addPomProperties(pomConfigProperty);

        configTemplateFactory.addYml("port", 8080);
        configTemplateFactory.addYml("mysql", Map.of(
                "enable", true,
                "url", dbUrl,
                "username", username,
                "password", password
        ));
        configTemplateFactory.addYml("applicationName", "otow");

        template.example("OTOWApplication.java", formatCodeList(configTemplateFactory.generateApplication(prefix, "OTOW")));
        template.resources("application.yml", formatCodeList(configTemplateFactory.generateApplicationYml()));
        template.master("pom.xml", formatCodeList(configTemplateFactory.generatePomConfig()));

        SwaggerInfo swaggerInfo = SwaggerInfo.builder().title("社区物业管理系统").version("1.0.0").description("社区物业管理系统用于提升社区物业管理和服务效率")
                .authorName("Genius").authorEmail("969025903@qq.com").authorUrl("https://github.com/Geniusay").build();
        String swagger = configTemplateFactory.generateConfig(prefix, "Swagger", new SwaggerMethodTemplate(swaggerInfo).generateMethod());
        template.config("SwaggerConfig.java", formatCodeList(swagger));

        virtualFileService.exportVirtualTree(treeId, "D:\\test");
    }

    @Test
    public void generateInitTemplate() {
        String prefix = "com.example.";

        String className = "User";
        String tableName = "user";
        String describe = "用户类";

        LinkedList<MetalField> fields = new LinkedList<>();
        fields.add(new MetalField("id", String.class, getDescribe("用户ID")));
        fields.add(new MetalField("uid", String.class, getDescribe("用户UID")));
        fields.add(new MetalField("avatar", String.class, getDescribe("头像")));
        fields.add(new MetalField("cid", Integer.class, getDescribe("用户CID")));
        fields.add(new MetalField("sex", Boolean.class, getDescribe("用户性别")));

        System.out.println("================================================================================");
        ClassTemplateFactory classTemplateFactory = new ClassTemplateFactory(prefix, functionPackManager, className, tableName, describe, fields, List.of("1001", "1002", "1003", "1004"));
        classTemplateFactory.initTemplate();

        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateEntity()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateEntityReq()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateEntityVO()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateMapper()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateService()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateServiceImpl()));
        System.out.println("================================================================================");

        System.out.println("没格式化：" );
        System.out.println(classTemplateFactory.generateController());
        System.out.println();
        System.out.println("格式化：" );
        System.out.println(CodeFormatterUtil.formatCode(classTemplateFactory.generateController()));
    }

    @Test
    public void generateInitConfigTemplate() {
        Map<String, Object> applicationConfig = new HashMap<>();
        applicationConfig.put("port", 8080);
        applicationConfig.put("mysql", Map.of(
                "enable", true,
                "url", "jdbc:mysql://127.0.0.1:3306/otow?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC",
                "username", "root",
                "password", "root"
        ));
        applicationConfig.put("applicationName", "otow");

        Map<String, Object> pomConfigDependency = new HashMap<>();
        pomConfigDependency.put("groupId", "org.springframework.boot");
        pomConfigDependency.put("artifactId", "spring-boot-starter-test");
        pomConfigDependency.put("version", "${boot.version}");

        Map<String, Object> pomConfigProperty = new HashMap<>();
        pomConfigProperty.put("name", "boot.version");
        pomConfigProperty.put("value", "2.3.9.RELEASE");

        String systemName = "otow";
        ConfigTemplateFactory configTemplateFactory = new ConfigTemplateFactory();
        String generateApplication = configTemplateFactory.generateApplication(prefix, "OTOW");
        System.out.println(generateApplication);
        System.out.println("==================================================================================");
        String generateApplicationConfig = configTemplateFactory.generateApplicationYml();
        System.out.println(generateApplicationConfig);
        System.out.println("====================================================================================");
        String generatePomConfig = configTemplateFactory.generatePomConfig();
        System.out.println(generatePomConfig);
    }
}
