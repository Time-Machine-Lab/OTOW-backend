package com.tml.otowbackend.engine.tree.utils;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class PathUtils {

    // 自定义的统一路径分隔符
    private static final String CUSTOM_SEPARATOR = "/";

    /**
     * 将系统路径转换为自定义路径（以 `|` 为分隔符）
     */
    public static String toCustomPath(String systemPath) {
        if (systemPath == null || systemPath.isEmpty()) {
            return "";
        }
        return systemPath.replace("\\", CUSTOM_SEPARATOR).replace("/", CUSTOM_SEPARATOR);
    }

    /**
     * 将自定义路径（以 `|` 为分隔符）转换为系统路径

     */
    public static String toSystemPath(String customPath) {
        if (customPath == null || customPath.isEmpty()) {
            return "";
        }
        return customPath.replace(CUSTOM_SEPARATOR, System.getProperty("file.separator"));
    }

    /**
     * 解析项目 ID 和相对路径
     *
     * @param projectIdAndPath 格式为：项目ID|相对路径
     * @return [项目ID, 相对路径]
     */
    public static String[] parseProjectIdAndPath(String projectIdAndPath) {
        projectIdAndPath = projectIdAndPath.substring(1);
        int separatorIndex = projectIdAndPath.indexOf(CUSTOM_SEPARATOR);
        if (separatorIndex == -1) {
            return new String[]{projectIdAndPath, ""};
        }
        String projectId = projectIdAndPath.substring(0, separatorIndex);
        String relativePath = projectIdAndPath.substring(separatorIndex + 1);
        return new String[]{projectId, relativePath};
    }
}