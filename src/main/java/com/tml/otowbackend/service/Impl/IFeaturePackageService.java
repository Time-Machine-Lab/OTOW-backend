package com.tml.otowbackend.service.Impl;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.service.FeaturePackageService;
import com.tml.otowbackend.engine.tree.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuppressWarnings("all")
@Service
public class IFeaturePackageService implements FeaturePackageService {

    private final FuncPackManager funcPackManager;

    /**
     * 获取支持的功能包列表
     * @return 动态支持的功能包列表
     */
    @Override
    public List<FeaturePackage> getSupportedFeaturePackages() {
        return funcPackManager.getSupportedFeaturePackages();
    }

    /**
     * 校验功能包 ID 是否合法
     * @param featureIds 待校验的功能包 ID 列表
     */
    @Override
    public void validateFeaturePackages(List<String> featureIds) {
        if (featureIds == null || featureIds.isEmpty()) {
            return; // 如果没有功能包，不需要校验
        }

        // 获取功能包 ID 的集合
        Set<String> supportedFeatureIds = funcPackManager.getSupportedFeaturePackages().stream().map(FeaturePackage::getId).collect(Collectors.toSet());

        // 校验每个功能包 ID 是否在支持的集合中
        for (String featureId : featureIds) {
            if (!supportedFeatureIds.contains(featureId)) {
                throw new ServeException("功能包不合法：" + featureId + "，请从支持的功能包列表中选择");
            }
        }
    }
}