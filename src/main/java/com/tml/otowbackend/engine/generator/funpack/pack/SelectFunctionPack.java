package com.tml.otowbackend.engine.generator.funpack.pack;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.SelectServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.EntityTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.VOTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.Path_Variable;
import static com.tml.otowbackend.engine.generator.template.java.InitTemplate.*;

@Component
public class SelectFunctionPack extends AbstrateFunctionPack {
    public static final String selectTemplateFilePath = "service.method.select.java.vm";

    public static final String selectServiceMethod = "select";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1004", "查询实体类");
    }

    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        controller.addGetMethod(getSelectMethod(), "/get/{id}");
    }

    // controller-get 的 查询
    private MetaMethod getSelectMethod() {
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", null, "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));
        String body = "return " + String.format("%s.%s(%s);", getParamString("classLower"), selectServiceMethod, "id");
        MetaMethod metaMethod = new MetaMethod(selectServiceMethod, List.of(metaMethodParam), body);
        metaMethod.setReturnRes(new VOTemplate(getParamString("prefix") + voPackagePath, getParamString("className")));
        return metaMethod;
    }


    @Override
    protected void addMethodToService(ServiceTemplate service) {
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", "id");
        MetaMethod metaMethod = new MetaMethod(selectServiceMethod, List.of(metaMethodParam));
        metaMethod.setReturnRes(new VOTemplate(getParamString("prefix") + voPackagePath, getParamString("className")));
        service.addMethods(metaMethod);
    }

    // serviceImpl的查找方法
    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
        SelectServiceMethodTemplate selectServiceMethodTemplate = new SelectServiceMethodTemplate(selectTemplateFilePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer", null, "id");
        MetaMethod metaMethod = new MetaMethod(selectServiceMethod, List.of(metaMethodParam), engine.generate(selectServiceMethodTemplate));
        metaMethod.setReturnRes(new VOTemplate(getParamString("prefix") + voPackagePath, getParamString("className")));
        serviceImplTemplate.addMethod(metaMethod);
        serviceImplTemplate.addClassImport(new EntityTemplate(getParamString("prefix") + entityPackagePath, getParamString("className")));
        serviceImplTemplate.addImportClazz(BeanUtil.class);
    }
}
