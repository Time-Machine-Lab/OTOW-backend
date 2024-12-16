package com.tml.otowbackend.engine.generator.template.java.model;

import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;

import static com.tml.otowbackend.constants.TemplateConstant.ENTITY_ANNOTATION;

public class VOTemplate extends ClassTemplate {

    public VOTemplate(String packagePath, String className) {
        super(packagePath, className+"VO");
    }

    @Override
    public void initAnnotations() {
        addAnnotations(MetaAnnotation.convertByClazz(ENTITY_ANNOTATION));
    }
}
