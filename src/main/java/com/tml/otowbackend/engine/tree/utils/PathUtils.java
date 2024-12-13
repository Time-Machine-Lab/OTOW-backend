package com.tml.otowbackend.engine.tree.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tml.otowbackend.constants.TreeConstant.*;

/**
 * 描述: 路径功能类
 * @author suifeng
 * 日期: 2024/12/12
 */
public class PathUtils {

    /**
     * 获取springboot项目路径
     */
    public static String getSpringbootPath() {
        return getFramePath(SPRINGBOOT_FRAME);
    }

    public static String getFramePath(String frameType) {
        Path basePath = Paths.get(System.getProperty("user.dir"));
        Path targetPath = Paths.get(basePath.toString(), FRAME_PATH + frameType + START_PATH);
        return targetPath.toAbsolutePath().toString();
    }

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
        systemPath = extractEffectivePath(systemPath, START_PATH);
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
        return removeFirstPathSegment(unixPath);
    }

    /**
     * 去除路径中的第一个部分
     */
    public static String removeFirstPathSegment(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("路径不能为空");
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("路径必须以 '/' 开头");
        }
        String[] segments = path.substring(1).split("/");
        if (segments.length <= 1) {
            return "/";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < segments.length; i++) {
            result.append("/").append(segments[i]);
        }

        return result.toString();
    }

    /**
     * 从完整路径中提取有效部分路径
     *
     * @param fullPath 完整路径（如：D:\HandRub\Tomcat\easy-tomcat\src\main\java\com\tomcat）
     * @param startSegment 有效路径的起始部分（如：src）
     * @return 提取后的有效路径（如：\src\main\java\com\tomcat）
     */
    public static String extractEffectivePath(String fullPath, String startSegment) {
        if (fullPath == null || fullPath.isEmpty() || startSegment == null || startSegment.isEmpty()) {
            throw new IllegalArgumentException("完整路径和起始部分不能为空");
        }
        // 统一路径分隔符为正斜杠，便于处理
        String normalizedPath = fullPath.replace("\\", "/");
        String normalizedStartSegment = startSegment.replace("\\", "/");
        // 查找起始部分的位置
        int startIndex = normalizedPath.indexOf("/" + normalizedStartSegment);
        if (startIndex == -1) {
            System.out.println(fullPath);
            throw new IllegalArgumentException("起始部分未在完整路径中找到");
        }
        // 提取有效路径部分
        String effectivePath = normalizedPath.substring(startIndex);
        // 将路径分隔符还原为系统默认的分隔符
        return effectivePath.replace("/", System.getProperty("file.separator"));
    }
}