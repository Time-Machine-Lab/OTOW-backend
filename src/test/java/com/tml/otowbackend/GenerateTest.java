package com.tml.otowbackend;

import cn.hutool.core.bean.BeanUtil;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.generator.template.java.InitConfigTemplate;
import com.tml.otowbackend.engine.generator.template.java.InitTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import com.tml.otowbackend.engine.generator.utils.TypeConverter;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;
import com.tml.otowbackend.engine.tree.template.SpringBootTreeTemplate;
import com.tml.otowbackend.engine.tree.utils.PathUtils;
import com.tml.otowbackend.util.CodeFormatterUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static com.tml.otowbackend.engine.generator.utils.MetalUtils.getDescribe;
import static com.tml.otowbackend.util.CodeFormatterUtil.formatCodeList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@Slf4j
public class GenerateTest {

//        return BeanUtil.copyProperties(newUser, StudentInfoVO.class);

    @Resource
    FuncPackManager funcPackManager;

    String filePath = "entity_class_definitions.json";

    @Resource
    private IVirtualFileService virtualFileService;


    @Test
    public void testGenerate() throws Exception {
        String prefix = "com.example";
        String treeId = virtualFileService.initializeVirtualTree(PathUtils.getSpringbootPath(), null);
        SpringBootTreeTemplate template = new SpringBootTreeTemplate(virtualFileService, treeId);
        template.initializeTemplate();

        Map<String, Object> applicationConfig = new HashMap<>();
        applicationConfig.put("port", 8080);
        applicationConfig.put("mysql", Map.of(
                "enable", true,
                "url", "jdbc:mysql://127.0.0.1:3306/otow?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC",
                "username", "root",
                "password", "twj369202865"
        ));
        applicationConfig.put("applicationName", "otow");

        Map<String, Object> pomConfigDependency = new HashMap<>();
        pomConfigDependency.put("groupId", "org.springframework.boot");
        pomConfigDependency.put("artifactId", "spring-boot-starter-test");
        pomConfigDependency.put("version", "${boot.version}");

        Map<String, Object> pomConfigProperty = new HashMap<>();
        pomConfigProperty.put("name", "boot.version");
        pomConfigProperty.put("value", "2.6.6");

        String systemName = "OTOW";
        InitConfigTemplate initConfigTemplate = new InitConfigTemplate(prefix, systemName, applicationConfig, pomConfigDependency, pomConfigProperty);

        String generateApplication = initConfigTemplate.getGenerateApplication();
        template.example("OTOWApplication.java", formatCodeList(generateApplication));

        String generateApplicationConfig = initConfigTemplate.getGenerateApplicationConfig();
        template.resources("application.yml", formatCodeList(generateApplicationConfig));

        String generatePomConfig = initConfigTemplate.getGeneratePomConfig();
        template.master("pom.xml", formatCodeList(generatePomConfig));

        List<EntityClassDefinition> entityClassDefinitions = AIGenerateTest.readEntityClassDefinitionsFromFile(filePath);
        if (entityClassDefinitions != null) {
            for (EntityClassDefinition definition : entityClassDefinitions) {
                String className = definition.getClassName();
                String tableName = className.toLowerCase();
                String describe = definition.getCdesc();

                LinkedList<MetalField> fields = new LinkedList<>();
                for (EntityClassDefinition.FieldDefinition field : definition.getFields()) {
                    System.out.println(field);
                    fields.add(new MetalField(field.getFname(), TypeConverter.toDatabaseFriendlyType(field.getFtype()), getDescribe(field.getFdesc())));
                }
                InitTemplate initTemplate = new InitTemplate(prefix, funcPackManager, className, tableName, describe, fields, definition.getFeatureIds());
                initTemplate.initTemplate();

                template.entity(className + "Entity.java", formatCodeList(initTemplate.generateEntity()));

                template.mapper(className + "Mapper.java", formatCodeList(initTemplate.generateMapper()));

                template.service(className + "Service.java", formatCodeList(initTemplate.generateService()));

                template.serviceImpl(className + "ServiceImpl.java", formatCodeList(initTemplate.generateServiceImpl()));

                template.controller(className + "Controller.java", formatCodeList(initTemplate.generateController()));

                template.req(className + "Req.java", formatCodeList(initTemplate.generateEntityReq()));

                template.vo(className + "VO.java", formatCodeList(initTemplate.generateEntityVO()));
            }
        }

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
        InitTemplate initTemplate = new InitTemplate(prefix, funcPackManager, className, tableName, describe, fields, List.of("1001", "1002", "1003", "1004"));
        initTemplate.initTemplate();

        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateEntity()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateEntityReq()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateEntityVO()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateMapper()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateService()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateServiceImpl()));
        System.out.println("================================================================================");

        System.out.println("没格式化：" );
        System.out.println(initTemplate.generateController());
        System.out.println();
        System.out.println("格式化：" );
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateController()));
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
        InitConfigTemplate initConfigTemplate = new InitConfigTemplate("", systemName, applicationConfig, pomConfigDependency, pomConfigProperty);
        String generateApplication = initConfigTemplate.getGenerateApplication();
        System.out.println(generateApplication);
        System.out.println("==================================================================================");
        String generateApplicationConfig = initConfigTemplate.getGenerateApplicationConfig();
        System.out.println(generateApplicationConfig);
        System.out.println("====================================================================================");
        String generatePomConfig = initConfigTemplate.getGeneratePomConfig();
        System.out.println(generatePomConfig);
    }
}
