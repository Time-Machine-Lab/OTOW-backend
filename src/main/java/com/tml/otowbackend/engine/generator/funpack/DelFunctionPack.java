package com.tml.otowbackend.engine.generator.funpack;


import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.core.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.DeleteServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.engine.generator.utils.MetalUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.Path_Variable;
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.engine;

@Component
public class DelFunctionPack extends AbstrateFunctionPack {

    public static final String deleteServiceMethod = "delete";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1003", "删除实体类");
    }

    // controller的删除方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        MetaMethodParam metaMethodParam = new MetaMethodParam(Integer.class, "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));
        String body = String.format("%s.%s(%s);", getParam("serviceName"), deleteServiceMethod, "id");
        MetaMethod metaMethod = new MetaMethod(deleteServiceMethod, List.of(metaMethodParam), body);
        metaMethod.addAnnotation(MetalUtils.getSwaggerOperation("删除" + getParam("classDesc")));
        controller.addDeleteMethod(metaMethod, "/delete/{id}");
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", "id");
        service.addMethod(deleteServiceMethod, metaMethodParam);
    }

    // serviceImpl 的删除方法
    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImpl) {
        DeleteServiceMethodTemplate deleteServiceMethodTemplate = new DeleteServiceMethodTemplate(getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer",null, "id");
        MetaMethod metaMethod = new MetaMethod(deleteServiceMethod, List.of(metaMethodParam), engine.generate(deleteServiceMethodTemplate));
        serviceImpl.addMethod(metaMethod);
    }
}
