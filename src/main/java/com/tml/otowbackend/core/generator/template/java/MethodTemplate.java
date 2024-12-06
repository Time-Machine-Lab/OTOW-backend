package com.tml.otowbackend.core.generator.template.java;

import com.tml.otowbackend.core.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.core.generator.template.VelocityOTOWTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaMethod;

public abstract class MethodTemplate extends VelocityOTOWTemplate {

    protected MethodTemplate(String templateFilePath) {
        super(templateFilePath);
    }

    public abstract MetaMethod generateMethod();

    public String generateMethodBody(){
        return VelocityCodeEngine.getCodeEngine().generate(this);
    }
}
