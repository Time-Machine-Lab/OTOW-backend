package com.tml.otowbackend.core.generator.template.java.model;


import com.tml.otowbackend.core.generator.template.java.ClassTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;

import static com.tml.otowbackend.constants.TemplateConstant.ENTITY_ANNOTATION;

public class DTOTemplate extends ClassTemplate {

    public DTOTemplate(String packagePath, String className) {
        super(packagePath, className+"DTO");
    }

    @Override
    public void initAnnotations() {
        addAnnotations(MetaAnnotation.convertByClazz(ENTITY_ANNOTATION));
    }
}
