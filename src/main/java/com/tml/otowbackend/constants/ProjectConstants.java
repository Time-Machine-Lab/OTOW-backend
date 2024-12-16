package com.tml.otowbackend.constants;

import java.util.Set;

/**
 * 项目参数常量类
 */
public class ProjectConstants {

    public static final String UID = "uid";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LANGUAGE = "language";
    public static final String TYPE = "type";
    public static final String COMPLEXITY = "complexity";

    // 参数最大长度
    public static final int MAX_TITLE_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    // 支持的复杂度值
    public static final Set<String> VALID_COMPLEXITY_VALUES = Set.of("简单", "一般", "很高");
}