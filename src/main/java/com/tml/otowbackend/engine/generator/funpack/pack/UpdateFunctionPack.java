package com.tml.otowbackend.engine.generator.funpack.pack;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.UpdateServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.REQUEST_BODY;
import static com.tml.otowbackend.engine.generator.template.java.InitTemplate.*;
import static com.tml.otowbackend.engine.generator.template.java.InitTemplate.engine;

@Component
public class UpdateFunctionPack extends AbstrateFunctionPack {

    public static final String updateTemplateFilePath = "service.method.update.java.vm";
    public static final String updateServiceMethod = "update";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1002", "更新实体类");
    }

    // controller的添加方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        controller.addPostMethod(getUpdateMethod(), "/update"+getParamString("className"));
    }
    // controller-post 的 更新
    private MetaMethod getUpdateMethod(){
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));
        String body = String.format("%s.%s(%s);", getParamString("classLower"), updateServiceMethod, metaMethodParam.getName());
        return new MetaMethod(updateServiceMethod, List.of(metaMethodParam), body);
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        service.addMethods(updateServiceMethod, List.of(metaMethodParam));
    }

    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        UpdateServiceMethodTemplate updateServiceMethodTemplate = new UpdateServiceMethodTemplate(updateTemplateFilePath, getParamString("className"));
        MetaMethod metaMethod = new MetaMethod(updateServiceMethod, List.of(metaMethodParam), engine.generate(updateServiceMethodTemplate));
        serviceImplTemplate.addMethod(metaMethod);
        serviceImplTemplate.addImportClazz(BeanUtil.class);
    }
}
