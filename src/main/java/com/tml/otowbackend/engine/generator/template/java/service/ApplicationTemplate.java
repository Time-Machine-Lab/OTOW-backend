package com.tml.otowbackend.engine.generator.template.java.service;

import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.ApplicationMethodTemplate;

import static com.tml.otowbackend.constants.TemplateConstant.SPRING_BOOT_APPLICATION;

public class ApplicationTemplate extends ClassTemplate {

    public ApplicationTemplate(String packagePath, String className) {
        super(packagePath, className + "Application");
        initMethod();
    }

    public void initMethod() {
        ApplicationMethodTemplate methodTemplate = new ApplicationMethodTemplate(className);
        this.addMethod(methodTemplate.generateMethod());
    }

    @Override
    public void initAnnotations() {
        addAnnotation(SPRING_BOOT_APPLICATION);
    }
}
