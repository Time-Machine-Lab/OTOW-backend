package com.tml.otowbackend;

import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.generator.template.java.InitConfigTemplate;
import com.tml.otowbackend.engine.generator.template.java.InitTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
//@WebAppConfiguration
@Slf4j
public class GenerateTest {

    @Resource
    FuncPackManager funcPackManager;

    @Test
    public void generateInitTemplate(){
        String className = "User";
        String tableName = "user";

        List<MetalField> fields = new ArrayList<>();
        fields.add(new MetalField("id", String.class));
        fields.add(new MetalField("uid", String.class));
        fields.add(new MetalField("uid", String.class));
        fields.add(new MetalField("avatar", String.class));
        fields.add(new MetalField("cid", int.class));

        Map<String, Object> applicationConfig = new HashMap<>();
        applicationConfig.put("port",8080);
        applicationConfig.put("mysql",Map.of(
                "enable", true,
                "url", "jdbc:mysql://127.0.0.1:3306/otow?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC",
                "username", "root",
                "password", "root"
        ));
        applicationConfig.put("applicationName","otow");

        Map<String, Object> pomConfigDependency = new HashMap<>();
        pomConfigDependency.put("groupId","org.springframework.boot");
        pomConfigDependency.put("artifactId","spring-boot-starter-test");
        pomConfigDependency.put("version","${boot.version}");

        Map<String, Object> pomConfigProperty = new HashMap<>();
        pomConfigProperty.put("name", "boot.version");
        pomConfigProperty.put("value", "2.3.9.RELEASE");

        String systemName = "otow";
        InitConfigTemplate initConfigTemplate = new InitConfigTemplate(systemName, applicationConfig, pomConfigDependency, pomConfigProperty);
        String generateApplication = initConfigTemplate.getGenerateApplication();
        System.out.println(generateApplication);
        System.out.println("==================================================================================");
        String generateApplicationConfig = initConfigTemplate.getGenerateApplicationConfig();
//        System.out.println(generateApplicationConfig);
        System.out.println("====================================================================================");
        String generatePomConfig = initConfigTemplate.getGeneratePomConfig();
//        System.out.println(generatePomConfig);
        System.out.println("================================================================================");
        InitTemplate initTemplate = new InitTemplate(funcPackManager, className,tableName,fields,List.of("1001", "1002", "1003", "1004"));
        initTemplate.initTemplate();

        System.out.println(initTemplate.generateEntity());
        System.out.println("================================================================================");
        System.out.println(initTemplate.generateMapper());
        System.out.println("================================================================================");
        System.out.println(initTemplate.generateService());
        System.out.println("================================================================================");
        System.out.println(initTemplate.generateServiceImpl());
        System.out.println("================================================================================");
        System.out.println(initTemplate.generateController());
    }
}
