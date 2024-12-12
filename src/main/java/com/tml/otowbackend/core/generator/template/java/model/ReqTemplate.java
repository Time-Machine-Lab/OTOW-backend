package com.tml.otowbackend.core.generator.template.java.model;


import com.tml.otowbackend.core.generator.template.java.ClassTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;

import static com.tml.otowbackend.constants.TemplateConstant.REQ_ANNOTATION;

public class ReqTemplate extends ClassTemplate {

    public ReqTemplate(String packagePath, String className) {
        super(packagePath, className+"Req");
    }

    @Override
    public void initAnnotations(){
        addAnnotations(MetaAnnotation.convertByClazz(REQ_ANNOTATION));
    }
}
