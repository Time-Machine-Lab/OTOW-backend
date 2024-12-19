package com.tml.otowbackend.engine.generator.funpack;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.core.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.engine.generator.utils.MetalUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.REQUEST_BODY;
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.*;

@Component
public class AddFunctionPack extends AbstrateFunctionPack {

    public static final String addServiceMethod = "save";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1001", "添加实体类");
    }

    // controller的添加方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        String className = getParam("className");
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));
        String body = String.format("%s.%s(%s);", getParam("serviceName"), addServiceMethod, metaMethodParam.getName());
        MetaMethod metaMethod = new MetaMethod(addServiceMethod, List.of(metaMethodParam), body);
        metaMethod.addAnnotation(MetalUtils.getSwaggerOperation("添加新" + getParam("classDesc")));
        controller.addPostMethod(metaMethod, "/add" + className);
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        service.addMethods(addServiceMethod, List.of(metaMethodParam));
    }

    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImpl) {
        AddServiceMethodTemplate addServiceMethodTemplate = new AddServiceMethodTemplate(getParam("className"));
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(), reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        MetaMethod metaMethod = new MetaMethod(addServiceMethod, List.of(metaMethodParam), engine.generate(addServiceMethodTemplate));
        serviceImpl.addMethod(metaMethod);
        serviceImpl.addImportClazz(BeanUtil.class);
    }
}
