package com.tml.otowbackend.core.generator.template.java;

import com.alibaba.fastjson.JSONObject;
import com.tml.otowbackend.core.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.core.generator.template.java.service.ApplicationTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class InitConfigTemplate {

    private static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    private static final String entityPackagePath = "io.github.geniusay.velocity.generate";
    private static final String servicePackagePath = "io.github.geniusay.velocity.generate.service";
    private static final String reqPackagePath = "io.github.geniusay.velocity.generate.pojo.req";
    private static final String mapperPackagePath = "io.github.geniusay.velocity.generate.mapper";
    private static final String serviceImplPackagePath = "io.github.geniusay.velocity.generate.service.impl";

    private String systemName;
    private Map<String, Object> applicationConfig;
    private Map<String, Object> pomConfigDependency;
    private Map<String, Object> pomConfigProperty;

    public InitConfigTemplate( String systemName,Map<String, Object> applicationConfig,Map<String, Object> pomConfigDependency,Map<String, Object> pomConfigProperty){
        this.systemName = systemName;
        this.applicationConfig = applicationConfig;
        this.pomConfigDependency = pomConfigDependency;
        this.pomConfigProperty = pomConfigProperty;
    }

    private String generateApplication(){
        ApplicationTemplate applicationTemplate = new ApplicationTemplate(entityPackagePath,systemName);

        String generate = engine.generate(applicationTemplate);
        return generate;
    }

    public String getGenerateApplication(){
        return generateApplication();
    }

    private String generateApplicationConfig(Map<String, Object> config){
        String s = JSONObject.toJSONString(config);
        ApplicationConfigTemplate configTemplate = new ApplicationConfigTemplate(JSONObject.parseObject(s));
        String generate = engine.generate(configTemplate);
        return generate;
    }
    public String getGenerateApplicationConfig(){
        return generateApplicationConfig(applicationConfig);
    }

    private String generatePomConfig(Map<String, Object> dependency,Map<String, Object> property){
        PomConfigTemplate configTemplate = new PomConfigTemplate();
        configTemplate.addDependency(dependency);
        configTemplate.addProperties(property);
        String generate = engine.generate(configTemplate);
        return generate;
    }

    public String getGeneratePomConfig(){
        return generatePomConfig(pomConfigDependency,pomConfigProperty);
    }
}
