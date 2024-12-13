package com.tml.otowbackend.engine.tree.utils;

import java.io.File;

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

    /**
     * 将系统特定格式的路径转换为通用的Unix风格路径
     *
     * @param systemPath 系统特定格式的路径（如Windows路径：D:\\Assistant\\.git）
     * @return 转换后的Unix风格路径（如：/Assistant/.git）
     */
    public static String toUnixStylePath(String systemPath) {
        if (systemPath == null || systemPath.isEmpty()) {
            throw new IllegalArgumentException("路径不能为空");
        }

        // 将反斜杠替换为正斜杠
        String unixPath = systemPath.replace("\\", "/");

        // 如果路径是Windows绝对路径（例如 D:/ 或 C:/），去掉盘符并确保以 / 开头
        if (unixPath.matches("^[a-zA-Z]:/.*")) {
            unixPath = unixPath.substring(2); // 去掉盘符（例如 D: -> 空）
        }

        // 确保路径以单个斜杠开头
        if (!unixPath.startsWith("/")) {
            unixPath = "/" + unixPath;
        }

        return unixPath;
    }

    /**
     * 将通用的Unix风格路径转换为系统特定格式的路径
     *
     * @param unixPath 通用的Unix风格路径（如：/Assistant/.git）
     * @return 转换后的系统特定格式路径（如Windows路径：D:\\Assistant\\.git）
     */
    public static String toSystemStylePath(String unixPath) {
        if (unixPath == null || unixPath.isEmpty()) {
            throw new IllegalArgumentException("路径不能为空");
        }

        // 将正斜杠替换为系统默认的文件分隔符
        String systemPath = unixPath.replace("/", File.separator);

        // 如果是绝对路径（以 / 开头），根据操作系统处理
        if (systemPath.startsWith(File.separator)) {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                // Windows系统：假设默认盘符为C:，可以根据实际需求调整
                systemPath = "C:" + systemPath;
            }
        }

        return systemPath;
    }
}