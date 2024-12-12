package com.tml.otowbackend.engine.tree.core;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SupportedLanguages {

    // 可展示的文件扩展名（按分类管理）
    private static final Set<String> PROGRAMMING_LANGUAGES = new HashSet<>();
    private static final Set<String> CONFIG_FILES = new HashSet<>();
    private static final Set<String> SCRIPT_FILES = new HashSet<>();
    private static final Set<String> DOCUMENT_FILES = new HashSet<>();

    // 合并所有可展示的文件类型
    private static final Set<String> DISPLAYABLE_FILE_TYPES = new HashSet<>();

    // 不可展示文件的提示信息
    public static final String UNSUPPORTED_FILE_MESSAGE = "文件内容过大或不可展示，请下载后查看。";

    static {
        // 编程语言文件
        PROGRAMMING_LANGUAGES.add("java");
        PROGRAMMING_LANGUAGES.add("py");
        PROGRAMMING_LANGUAGES.add("js");
        PROGRAMMING_LANGUAGES.add("ts");
        PROGRAMMING_LANGUAGES.add("tsx");
        PROGRAMMING_LANGUAGES.add("c");
        PROGRAMMING_LANGUAGES.add("cpp");
        PROGRAMMING_LANGUAGES.add("h");
        PROGRAMMING_LANGUAGES.add("cs");
        PROGRAMMING_LANGUAGES.add("go");
        PROGRAMMING_LANGUAGES.add("kt");
        PROGRAMMING_LANGUAGES.add("swift");
        PROGRAMMING_LANGUAGES.add("php");
        PROGRAMMING_LANGUAGES.add("rb");
        PROGRAMMING_LANGUAGES.add("rs");
        PROGRAMMING_LANGUAGES.add("dart");
        PROGRAMMING_LANGUAGES.add("scala");
        PROGRAMMING_LANGUAGES.add("pl");
        PROGRAMMING_LANGUAGES.add("r");
        PROGRAMMING_LANGUAGES.add("jl");

        // 配置文件
        CONFIG_FILES.add("yaml");
        CONFIG_FILES.add("yml");
        CONFIG_FILES.add("properties");
        CONFIG_FILES.add("ini");
        CONFIG_FILES.add("json");
        CONFIG_FILES.add("xml");
        CONFIG_FILES.add("toml");
        CONFIG_FILES.add("conf");
        CONFIG_FILES.add("env");
        CONFIG_FILES.add("pom");
        CONFIG_FILES.add("gradle");
        CONFIG_FILES.add("sql");

        // 脚本文件
        SCRIPT_FILES.add("sh");
        SCRIPT_FILES.add("bat");
        SCRIPT_FILES.add("cmd");
        SCRIPT_FILES.add("ps1");
        SCRIPT_FILES.add("ksh");

        // 文档文件
        DOCUMENT_FILES.add("md");
        DOCUMENT_FILES.add("txt");
        DOCUMENT_FILES.add("rst");
        DOCUMENT_FILES.add("log");

        // 容器化相关文件
        CONFIG_FILES.add("dockerfile");

        DISPLAYABLE_FILE_TYPES.addAll(PROGRAMMING_LANGUAGES);
        DISPLAYABLE_FILE_TYPES.addAll(CONFIG_FILES);
        DISPLAYABLE_FILE_TYPES.addAll(SCRIPT_FILES);
        DISPLAYABLE_FILE_TYPES.addAll(DOCUMENT_FILES);
    }

    /**
     * 判断文件是否为可展示类型
     */
    public static boolean isDisplayableFile(String fileExtension) {
        return DISPLAYABLE_FILE_TYPES.contains(fileExtension.toLowerCase());
    }
}