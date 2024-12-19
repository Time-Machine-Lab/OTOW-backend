package com.tml.otowbackend.controller.generate;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import com.tml.otowbackend.engine.otow.SupportedLanguages;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.service.FeaturePackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述: 语言支持控制器
 * 负责获取支持的语言及其项目类型
 */
@RequiredArgsConstructor
@RequestMapping("/otow/support")
@RestController
@Validated
public class SupportedController {

    private final FeaturePackageService featurePackageService;

    /**
     * 获取支持的语言及其项目类型
     *
     * @return 返回支持的语言及类型
     */
    @GetMapping("/language")
    public R<Map<String, List<String>>> getSupportedLanguages() {
        return R.success("获取支持的语言和项目类型成功", SupportedLanguages.LANGUAGE_TO_TYPES);
    }

    /**
     * 获取支持的功能包列表
     *
     * @return 功能包列表
     */
    @GetMapping("/feature")
    @TokenRequire
    public R<List<FeaturePackage>> getSupportedFeaturePackages() {
        return R.success("获取支持的功能包列表成功", featurePackageService.getSupportedFeaturePackages());
    }
}