package com.tml.otowbackend.engine.ai.core;

/**
 * 描述: 有效信息截取工具类
 * @author suifeng
 * 日期: 2024/12/12
 */
public class ValidDataExtractor {

    /**
     * 从 AI 返回的内容中截取有效信息
     *
     * @param aiResponse AI 返回的原始字符串
     * @return 截取的有效数据
     * @throws IllegalArgumentException 如果未找到有效信息
     */
    public static String extractValidData(String aiResponse) throws IllegalArgumentException {
        int startIndex = aiResponse.indexOf("###");
        int endIndex = aiResponse.lastIndexOf("###");

        if (startIndex == -1 || endIndex == -1 || startIndex == endIndex) {
            throw new IllegalArgumentException("未找到有效信息标记（### 和 ###）");
        }

        // 截取 ### 和 ### 之间的内容
        return aiResponse.substring(startIndex + 3, endIndex).trim();
    }
}