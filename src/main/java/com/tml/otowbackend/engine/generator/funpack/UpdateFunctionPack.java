package com.tml.otowbackend.engine.generator.funpack;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.core.AbstrateFunctionPack;
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
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.*;
import static com.tml.otowbackend.engine.generator.core.ClassTemplateFactory.engine;

@Component
public class UpdateFunctionPack extends AbstrateFunctionPack {

    public static final String updateServiceMethod = "update";

    @Override
    protected FeaturePackage getFeaturePackage() {
        return new FeaturePackage("1002", "更新实体类");
    }

    // controller的添加方法
    @Override
    protected void addMethodToController(ControllerTemplate controller) {
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));
        String body = String.format("%s.%s(%s);", getParam("serviceName"), updateServiceMethod, metaMethodParam.getName());
        MetaMethod metaMethod = new MetaMethod(updateServiceMethod, List.of(metaMethodParam), body);
        controller.addPostMethod(metaMethod, "/update"+ getParam("className"));
    }

    @Override
    protected void addMethodToService(ServiceTemplate service) {
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        service.addMethods(updateServiceMethod, List.of(metaMethodParam));
    }

    @Override
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImpl) {
        ReqTemplate reqUser = new ReqTemplate(getParam("prefix") + reqPackagePath, getParam("className"));
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), StringUtils.firstToLowerCase(reqUser.getClassName()));
        UpdateServiceMethodTemplate updateServiceMethodTemplate = new UpdateServiceMethodTemplate(getParam("className"));
        MetaMethod metaMethod = new MetaMethod(updateServiceMethod, List.of(metaMethodParam), engine.generate(updateServiceMethodTemplate));
        serviceImpl.addMethod(metaMethod);
        serviceImpl.addImportClazz(BeanUtil.class);
    }
}
