package com.tml.otowbackend.engine.generator.funpack;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FuncPackManager {

    private final Map<String, AbstrateFunctionPack> functionPackMap = new HashMap<>();
    private final List<FeaturePackage> featurePackageList;

    @Autowired
    public FuncPackManager(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(AbstrateFunctionPack.class).forEach((name, bean) -> {
            functionPackMap.put(bean.getFeaturePackage().getId(), bean);
        });
        featurePackageList = functionPackMap.values().stream().map(AbstrateFunctionPack::getFeaturePackage).collect(Collectors.toList());
    }

    public AbstrateFunctionPack getFunctionPackById(String id) {
        return functionPackMap.getOrDefault(id, null);
    }

    public List<FeaturePackage> getSupportedFeaturePackages() {
        return featurePackageList;
    }
}
