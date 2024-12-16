package com.tml.otowbackend.engine.otow;

import com.tml.otowbackend.constants.ProjectConstants;
import com.tml.otowbackend.engine.tree.common.ServeException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述: 项目参数校验器
 * @author suifeng
 * 日期: 2024/12/16
 */
@SuppressWarnings("all")
public class ProjectValidator {

    // 允许的参数键集合
    private static final Set<String> ALLOWED_KEYS = Set.of(
            ProjectConstants.TITLE,
            ProjectConstants.DESCRIPTION,
            ProjectConstants.LANGUAGE,
            ProjectConstants.TYPE,
            ProjectConstants.COMPLEXITY
    );

    /**
     * 校验参数的合法性
     *
     * @param params 参数Map
     */
    public static void validateParams(Map<String, Object> params) {
        for (String key : params.keySet()) {
            if (!ALLOWED_KEYS.contains(key)) {
                throw new ServeException("非法参数键: " + key);
            }
            Object value = params.get(key);
            if (value instanceof String) {
                String strValue = (String) value;

                // 校验标题长度
                if (ProjectConstants.TITLE.equals(key) && strValue.length() > ProjectConstants.MAX_TITLE_LENGTH) {
                    throw new ServeException("项目标题长度不得超过 " + ProjectConstants.MAX_TITLE_LENGTH + " 字");
                }

                // 校验描述长度
                if (ProjectConstants.DESCRIPTION.equals(key) && strValue.length() > ProjectConstants.MAX_DESCRIPTION_LENGTH) {
                    throw new ServeException("项目描述长度不得超过 " + ProjectConstants.MAX_DESCRIPTION_LENGTH + " 字");
                }

                // 校验复杂度值是否合法
                if (ProjectConstants.COMPLEXITY.equals(key) && !ProjectConstants.VALID_COMPLEXITY_VALUES.contains(strValue)) {
                    throw new ServeException("非法复杂度值: " + strValue + "，合法值为: " + ProjectConstants.VALID_COMPLEXITY_VALUES);
                }
            }
        }

        // 校验语言和类型的匹配关系
        validateLanguageAndType(params);
    }


    /**
     * 校验语言和项目类型的匹配关系
     *
     * @param params 参数Map
     */
    private static void validateLanguageAndType(Map<String, Object> params) {
        String language = (String) params.get(ProjectConstants.LANGUAGE);
        String type = (String) params.get(ProjectConstants.TYPE);

        // 如果语言为空或类型为空，不进行校验
        if (language == null || type == null) {
            return;
        }

        // 校验语言是否支持
        if (!SupportedLanguages.SUPPORTED_LANGUAGES.contains(language)) {
            throw new ServeException("不支持的语言: " + language);
        }

        // 校验类型是否支持
        List<String> supportedTypes = SupportedLanguages.LANGUAGE_TO_TYPES.get(language);
        if (supportedTypes == null || !supportedTypes.contains(type)) {
            throw new ServeException("语言 " + language + " 不支持项目类型: " + type);
        }
    }
}