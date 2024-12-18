package com.tml.otowbackend.engine.generator.template.java.method;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.template.java.MethodTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import org.apache.velocity.VelocityContext;

//TODO serviceImpl删除实体方法模板完善
public class DeleteServiceMethodTemplate extends MethodTemplate {
    private String entityClassName;

    protected DeleteServiceMethodTemplate(String templateFilePath) {
        super(templateFilePath);
    }

    public DeleteServiceMethodTemplate(String templateFilePath, String entityClassName) {
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
        context.put("entityClassName", StringUtils.firstToLowerCase(entityClassName));
        return context;
    }
}
