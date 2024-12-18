package com.tml.otowbackend.service;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.tree.common.R;

import java.util.List;

public interface FeaturePackageService {

    /**
     * 获取支持的功能包列表
     *
     * @return 支持的功能包列表
     */
    List<FeaturePackage> getSupportedFeaturePackages();

    /**
     * 校验功能包列表是否合法
     *
     * @param featureIds 功能包 ID 列表
     */
    void validateFeaturePackages(List<String> featureIds);
}