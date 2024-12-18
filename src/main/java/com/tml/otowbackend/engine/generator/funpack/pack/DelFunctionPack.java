package com.tml.otowbackend.engine.generator.funpack.pack;


import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.DeleteServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.SelectServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.Path_Variable;
import static com.tml.otowbackend.engine.generator.template.java.InitTemplate.engine;
import static com.tml.otowbackend.engine.generator.template.java.InitTemplate.reqPackagePath;

@Component
public class DelFunctionPack extends AbstrateFunctionPack {

    public static final String deleteTemplateFilePath = "service.method.delete.java.vm";
    public static final String deleteServiceMethod = "delete";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1003", "删除实体类");
    }

    // controller的删除方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        controller.addDeleteMethod(getDeleteMethod(), "/delete/{id}");
    }

    // controller-delete 的 删除
    private MetaMethod getDeleteMethod(){
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, "id");
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", null, "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));
        String body = String.format("%s.%s(%s);", getParamString("classLower"), deleteServiceMethod, "id");
        return new MetaMethod(deleteServiceMethod, List.of(metaMethodParam), body);
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", "id");
        service.addMethod(deleteServiceMethod, metaMethodParam);
    }

    // serviceImpl 的删除方法
    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
        DeleteServiceMethodTemplate deleteServiceMethodTemplate = new DeleteServiceMethodTemplate(deleteTemplateFilePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer",null, "id");
        MetaMethod metaMethod = new MetaMethod(deleteServiceMethod, List.of(metaMethodParam), engine.generate(deleteServiceMethodTemplate));
        serviceImplTemplate.addMethod(metaMethod);
    }

}
