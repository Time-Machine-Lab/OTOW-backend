package com.tml.otowbackend.service.Impl;

import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.service.FeaturePackageService;
import com.tml.otowbackend.engine.tree.common.R;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
@Service
public class IFeaturePackageService implements FeaturePackageService {

    // 静态支持的功能包列表
    private static final List<FeaturePackage> SUPPORTED_FEATURE_PACKAGES = List.of(
        new FeaturePackage("1001", "增加实体类"),
        new FeaturePackage("1002", "修改实体类"),
        new FeaturePackage("1003", "删除实体类"),
        new FeaturePackage("1004", "查询实体类")
    );

    @Override
    public R<List<FeaturePackage>> getSupportedFeaturePackages() {
        return R.success("获取支持的功能包列表成功", SUPPORTED_FEATURE_PACKAGES);
    }

    @Override
    public void validateFeaturePackages(List<String> featureIds) {
        if (featureIds == null || featureIds.isEmpty()) {
            return; // 如果没有功能包，不需要校验
        }

        // 获取支持的功能包 ID 列表
        List<String> supportedFeatureIds = SUPPORTED_FEATURE_PACKAGES.stream()
            .map(FeaturePackage::getId)
            .collect(Collectors.toList());

        // 校验每个功能包 ID 是否在支持的列表中
        for (String featureId : featureIds) {
            if (!supportedFeatureIds.contains(featureId)) {
                throw new ServeException("功能包不合法：" + featureId + "，请从支持的功能包列表中选择");
            }
        }
    }
}