package com.tml.otowbackend.engine.ai.core;

import java.util.Map;

/**
 * 描述: AI操作接口，包含提示词生成和响应解析
 * @author suifeng
 * 日期: 2024/12/12
 */
public interface AIOperation<T> {

    /**
     * 生成提示词
     *
     * @param projectOutline 项目大纲
     * @return 提示词
     */
    String generatePrompt(Map<String, Object> projectOutline);

    /**
     * 解析 AI 响应
     *
     * @param aiResponse AI 返回的 JSON 字符串
     * @return 解析结果包装类
     */
    ParseResult<T> parseResponse(String aiResponse);
}