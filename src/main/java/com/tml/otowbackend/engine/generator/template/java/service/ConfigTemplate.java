package com.tml.otowbackend.engine.generator.template.java.service;

import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public class ConfigTemplate extends ClassTemplate {

    public ConfigTemplate(String packagePath, String className) {
        super(packagePath, getConfigClassName(className));
        addAnnotation(new MetaAnnotation(Configuration.class));
    }

    public static String getConfigClassName(String className) {
        return className + "Config";
    }

    public void addBeanMethod(MetaMethod method) {
        method.addAnnotations(List.of(new MetaAnnotation(Bean.class)));
        super.addMethod(method);
    }
}
