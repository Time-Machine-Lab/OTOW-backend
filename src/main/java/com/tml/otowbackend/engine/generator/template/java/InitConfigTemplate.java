package com.tml.otowbackend.engine.generator.template.java;

import com.alibaba.fastjson.JSONObject;
import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.engine.generator.template.java.service.ApplicationTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class InitConfigTemplate {

    private static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    private String prefix;
    private String systemName;
    private Map<String, Object> applicationConfig;
    private Map<String, Object> pomConfigDependency;
    private Map<String, Object> pomConfigProperty;

    public InitConfigTemplate(String prefix, String systemName, Map<String, Object> applicationConfig,Map<String, Object> pomConfigDependency,Map<String, Object> pomConfigProperty){
        this.prefix = prefix;
        this.systemName = systemName;
        this.applicationConfig = applicationConfig;
        this.pomConfigDependency = pomConfigDependency;
        this.pomConfigProperty = pomConfigProperty;
    }

    private String generateApplication(){
        ApplicationTemplate applicationTemplate = new ApplicationTemplate(prefix, systemName);
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
