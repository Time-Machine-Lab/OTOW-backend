package com.tml.otowbackend.engine.generator.core;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class AbstrateFunctionPack implements FunctionPack {

    // 用于传递数据到功能包里面
    protected Map<String, String> dataMap = new HashMap<>();
    public void addParams(String key, String value) {
        dataMap.put(key, value);
    }
    public String getParam(String key) {
        return dataMap.get(key);
    }

    protected abstract FeaturePackage getFeaturePackage();

    @Override
    public void handleController(Object object) {
        ControllerTemplate controller = (ControllerTemplate) object;
        addMethodToController(controller);
    }
    protected void addMethodToController(ControllerTemplate controller) {}

    @Override
    public void handleService(Object object) {
        ServiceTemplate service = (ServiceTemplate) object;
        addMethodToService(service);
    }
    protected void addMethodToService(ServiceTemplate service) {}

    @Override
    public void handleServiceImpl(Object object) {
        ServiceImplTemplate serviceImpl = (ServiceImplTemplate) object;
        addMethodToServiceImpl(serviceImpl);
    }
    protected void addMethodToServiceImpl(ServiceImplTemplate serviceImpl) {
    }

    @Override
    public void handleMapper(Object object) {
    }

    @Override
    public void handleApplicationConfig(Object object) {
    }

    @Override
    public void handlePomConfig(Object object) {
    }
}
