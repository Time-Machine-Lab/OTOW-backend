package com.tml.otowbackend.engine.generator.funpack.pack;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
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

@Component
public class AddFunctionPack extends AbstrateFunctionPack {

    public static final String saveTemplateFilePath = "service.method.save.java.vm";

    public static final String addServiceMethod = "save";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1001", "添加实体类");
    }

    // controller的添加方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        controller.addPostMethod(getAddMethod(), "/add" + getParamString("className"));
    }

    // controller-post 的 添加
    private MetaMethod getAddMethod(){
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));
        String body = String.format("%s.%s(%s);", getParamString("classLower"), addServiceMethod, metaMethodParam.getName());
        return new MetaMethod(addServiceMethod, List.of(metaMethodParam), body);
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        service.addMethods(addServiceMethod, List.of(metaMethodParam));
    }

    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
        AddServiceMethodTemplate addServiceMethodTemplate = new AddServiceMethodTemplate(saveTemplateFilePath, getParamString("className"));
        ReqTemplate reqUser = new ReqTemplate(getParamString("prefix") + reqPackagePath, getParamString("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(), reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        MetaMethod metaMethod = new MetaMethod(addServiceMethod, List.of(metaMethodParam), engine.generate(addServiceMethodTemplate));
        serviceImplTemplate.addMethod(metaMethod);
        serviceImplTemplate.addImportClazz(BeanUtil.class);
    }
}
