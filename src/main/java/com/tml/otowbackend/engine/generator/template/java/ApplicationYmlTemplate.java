package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import org.apache.velocity.VelocityContext;

import java.util.HashMap;
import java.util.Map;

public class ApplicationYmlTemplate extends VelocityOTOWTemplate {

    private final Map<String, Object> applicationYml;

    public ApplicationYmlTemplate(Map<String, Object> applicationYml) {
        super("application.yml.vm");
        this.applicationYml = applicationYml;
    }

    public ApplicationYmlTemplate() {
        super("application.yml.vm");
        this.applicationYml = new HashMap<String, Object>();
    }

    public void addYml(String key, Object value){
        applicationYml.put(key, value);
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("config", applicationYml);
        return context;
    }
}
