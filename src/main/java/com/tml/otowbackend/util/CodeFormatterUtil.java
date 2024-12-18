package com.tml.otowbackend.util;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述: 代码格式化工具类
 * @author suifeng
 * 日期: 2024/12/18
 */
@Slf4j
public class CodeFormatterUtil {

    /**
     * 格式化 Java 代码
     *
     * @param code 未格式化的 Java 代码
     * @return 格式化后的 Java 代码
     */
    public static String formatCode(String code) {
        Formatter formatter = new Formatter();
        try {
            return formatter.formatSource(code);
        } catch (FormatterException e) {
            log.error("代码格式化失败");
            return code;
        }
    }

    /**
     * 将代码字符串切分成行，并保留空行
     *
     * @param code 代码字符串
     * @return 包含代码每一行的 List，空行也作为一个元素
     */
    public static List<String> splitCodeIntoLines(String code) {
        if (code == null || code.isEmpty()) {
            return new ArrayList<>();
        }
        // 使用正则表达式 "\r?\n" 兼容 Windows 和 Unix 的换行符
        return Arrays.asList(code.split("\\r?\\n"));
    }
}