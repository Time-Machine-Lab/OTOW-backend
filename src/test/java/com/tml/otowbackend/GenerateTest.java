package com.tml.otowbackend;

import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.generator.template.java.InitConfigTemplate;
import com.tml.otowbackend.engine.generator.template.java.InitTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import com.tml.otowbackend.engine.generator.template.meta.MetalUtils;
import com.tml.otowbackend.util.CodeFormatterUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static com.tml.otowbackend.engine.generator.template.meta.MetalUtils.getDescribe;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@Slf4j
public class GenerateTest {

    @Resource
    FuncPackManager funcPackManager;

    @Test
    public void generateInitTemplate() {
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
        InitTemplate initTemplate = new InitTemplate(funcPackManager, className, tableName, describe, fields, List.of("1001", "1002", "1003", "1004"));
        initTemplate.initTemplate();

        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateEntity()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateMapper()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateService()));
        System.out.println("================================================================================");
        System.out.println(CodeFormatterUtil.formatCode(initTemplate.generateServiceImpl()));
        System.out.println("================================================================================");
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
        InitConfigTemplate initConfigTemplate = new InitConfigTemplate(systemName, applicationConfig, pomConfigDependency, pomConfigProperty);
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
