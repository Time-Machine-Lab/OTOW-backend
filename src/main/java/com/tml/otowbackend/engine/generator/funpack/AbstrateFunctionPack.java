package com.tml.otowbackend.engine.generator.funpack;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class AbstrateFunctionPack implements FunctionPack {
    protected Map<String, Object> dataMap = new HashMap<>();

    protected abstract FeaturePackage getFeaturePackage();

    // 生成controller类
    @Override
    public Object generateController(Object object) {
        ControllerTemplate controller = (ControllerTemplate) object;
        // 添加方法
        addMethodToController(controller);
        return null;
    }

    // 添加方法到controller中 可重写
    protected void addMethodToController(ControllerTemplate controller) {}

    public void addParams(String key, Object value) {
        dataMap.put(key, value);
    }

    public Object getParam(String key) {
        return dataMap.get(key);
    }
    public String getParamString(String key) {
        return (String)dataMap.get(key);
    }

    @Override
    public Object generateService(Object object) {
        ServiceTemplate service = (ServiceTemplate) object;
        addMethodToService(service);
        return null;
    }
    protected void addMethodToService(ServiceTemplate service) {}

    @Override
    public Object generateServiceImpl(Object object) {
        ServiceImplTemplate serviceImplTemplate = (ServiceImplTemplate) object;
        addMethodToServiceImpl(serviceImplTemplate);
        return null;
    }

    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImplTemplate) {
    }

    @Override
    public Object generateMapper(Object object) {
        return null;
    }

    @Override
    public Object generateApplication(Object object) {
        return null;
    }

    @Override
    public Object generateApplicationConfig(Object object) {
        return null;
    }

    @Override
    public Object generatePomConfig(Object object) {
        return null;
    }
}
