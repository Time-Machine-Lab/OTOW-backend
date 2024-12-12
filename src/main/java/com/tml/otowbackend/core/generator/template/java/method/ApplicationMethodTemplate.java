package com.tml.otowbackend.core.generator.template.java.method;

import com.tml.otowbackend.core.generator.template.java.MethodTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaMethod;
import org.apache.velocity.VelocityContext;
import org.springframework.boot.SpringApplication;

public class ApplicationMethodTemplate extends MethodTemplate {

    private final String applicationClazz;

    public ApplicationMethodTemplate(String applicationClazz) {
        super("application.method.java.vm");
        this.applicationClazz = applicationClazz;
    }

    @Override
    public MetaMethod generateMethod() {
        MetaMethod metaMethod = MetaMethod.justStringReinder(generateMethodBody());
        metaMethod.addImportClazz(SpringApplication.class);
        return metaMethod;
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("application", applicationClazz);
        return context;
    }
}
