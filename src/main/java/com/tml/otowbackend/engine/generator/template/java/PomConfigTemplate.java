package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO pom配置文件模板
public class PomConfigTemplate extends VelocityOTOWTemplate {

    protected List<Map<String, Object>> dependencies = new ArrayList<>();

    protected List<Map<String, Object>> defaultDependencies = new ArrayList<>();

    // 不确定是否需要
    protected List<Map<String, Object>> resources = new ArrayList<>();

    protected List<Map<String, Object>> properties = new ArrayList<>();

    public PomConfigTemplate() {
        super("pom.xml.vm");
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("dependencies", this.dependencies);
        context.put("defaultDependencies", this.defaultDependencies);
        context.put("resources", this.resources);
        context.put("properties", this.properties);
        return context;
    }

    public void addDependency(Map<String, Object> dependency) {
        this.dependencies.add(dependency);
    }

    public void addDependencies(List<Map<String, Object>> dependencies) {
        this.dependencies.addAll(dependencies);
    }

    public void addDefaultDependency(Map<String, Object> dependency) {
        this.defaultDependencies.add(dependency);
    }

    public void addDefaultDependencies(List<Map<String, Object>> dependencies) {
        this.defaultDependencies.addAll(dependencies);
    }

    public void addResource(Map<String, Object> resource) {
        this.resources.add(resource);
    }

    public void addResources(List<Map<String, Object>> resources) {
        this.resources.addAll(resources);
    }

    public void addProperties(Map<String, Object> properties) {
        this.properties.add(properties);
    }

    public void addProperties(List<Map<String, Object>> properties) {
        this.properties.addAll(properties);
    }
}
