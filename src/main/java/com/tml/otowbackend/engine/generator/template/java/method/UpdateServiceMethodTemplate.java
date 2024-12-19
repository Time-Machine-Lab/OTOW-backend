package com.tml.otowbackend.engine.generator.template.java.method;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.template.java.MethodTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import org.apache.velocity.VelocityContext;

//TODO serviceImpl更新实体方法模板完善
public class UpdateServiceMethodTemplate extends MethodTemplate {

    private String entityClassName;

    public UpdateServiceMethodTemplate(String entityClassName) {
        super("service.method.update.java.vm");
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
        context.put("entityClassNameLower", StringUtils.firstToLowerCase(entityClassName));
        return context;
    }
}
