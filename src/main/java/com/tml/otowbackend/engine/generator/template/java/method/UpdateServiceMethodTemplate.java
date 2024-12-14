package com.tml.otowbackend.engine.generator.template.java.method;

import com.tml.otowbackend.engine.generator.template.java.MethodTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import org.apache.velocity.VelocityContext;

//TODO serviceImpl更新实体方法模板完善
public class UpdateServiceMethodTemplate extends MethodTemplate {

    private String entityClassName;

    protected UpdateServiceMethodTemplate(String templateFilePath) {
        super(templateFilePath);
    }

    public UpdateServiceMethodTemplate(String templateFilePath, String entityClassName) {
        super(templateFilePath);
        this.entityClassName = entityClassName;
    }

    @Override
    public MetaMethod generateMethod() {
        MetaMethod metaMethod = MetaMethod.justStringReinder(generateMethodBody());
        return metaMethod;
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("entityClassName",entityClassName);
        context.put("entityClassNameLower",entityClassName.toLowerCase());
        return context;
    }
}
