package com.tml.otowbackend.engine.generator.funpack;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
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
public class SelectFunctionPack extends AbstrateFunctionPack  {
    public static final String selectTemplateFilePath = "service.method.select.java.vm";

    public static final String selectServiceMethod = "select";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1004", "查询实体类");
    }

    // controller的删除方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        controller.addGetMethod(getSelectMethod(), "/get/{id}");
    }

    // controller-get 的 查询
    private MetaMethod getSelectMethod(){
        ReqTemplate reqUser = new ReqTemplate(reqPackagePath, "id");
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer",reqUser.getAllPackagePath(), "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));
        String body = String.format("%s.%s(%s);", getParamString("classLower"), selectServiceMethod, "id");
        return new MetaMethod(selectServiceMethod, List.of(metaMethodParam), body);
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        service.addMethod(selectServiceMethod, List.of((MetaMethodParam) getParam("metaMethodParam")));
    }

    // serviceImpl的查找方法
    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
        SelectServiceMethodTemplate selectServiceMethodTemplate = new SelectServiceMethodTemplate(selectTemplateFilePath,getParamString("className"));
        MetaMethod metaMethod = new MetaMethod(engine.generate(selectServiceMethodTemplate));
        serviceImplTemplate.addMethod(metaMethod);
    }
}
