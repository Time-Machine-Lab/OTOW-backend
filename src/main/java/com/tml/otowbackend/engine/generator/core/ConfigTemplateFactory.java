package com.tml.otowbackend.engine.generator.core;

import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.engine.generator.template.java.ApplicationYmlTemplate;
import com.tml.otowbackend.engine.generator.template.java.PomConfigTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ApplicationTemplate;
import lombok.Data;

import java.util.Map;

@Data
public class ConfigTemplateFactory {

    private static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    private ApplicationYmlTemplate ymlTemplate;
    private PomConfigTemplate pomConfigTemplate;

    public ConfigTemplateFactory(){
        this.ymlTemplate = new ApplicationYmlTemplate();
        this.pomConfigTemplate = new PomConfigTemplate();
    }

    public String generateApplication(String prefix, String systemName){
        ApplicationTemplate applicationTemplate = new ApplicationTemplate(prefix, systemName);
        return engine.generate(applicationTemplate);
    }

    public String generateApplicationYml(){
        return engine.generate(ymlTemplate);
    }

    public void addYml(String key, Object value){
        ymlTemplate.addYml(key, value);
    }

    public String generatePomConfig(){
        return engine.generate(pomConfigTemplate);
    }

    public void addPomDependency(Map<String, Object> pomConfigDependency){
        pomConfigTemplate.addDependency(pomConfigDependency);
    }

    public void addPomProperties(Map<String, Object> pomConfigProperty){
        pomConfigTemplate.addDependency(pomConfigProperty);
    }
}
