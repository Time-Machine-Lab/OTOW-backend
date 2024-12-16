package com.tml.otowbackend.engine.otow;

import java.util.List;
import java.util.Map;

/**
 * 平台支持的语言和项目类型常量
 */
public class SupportedLanguages {

    // 平台支持的语言及其对应的项目类型
    public static final Map<String, List<String>> LANGUAGE_TO_TYPES = Map.of(
            "Java", List.of("SpringBoot", "Maven", "Gradle"),
            "Go", List.of("Gin", "Beego"),
            "Python", List.of("Django", "Flask"),
            "C++", List.of("CMake", "Qt"),
            "JavaScript", List.of("NodeJS", "React", "Vue")
    );

    // 平台支持的所有语言
    public static final List<String> SUPPORTED_LANGUAGES = List.copyOf(LANGUAGE_TO_TYPES.keySet());
}