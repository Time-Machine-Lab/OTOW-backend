package com.tml.otowbackend.engine.ai.core;

/**
 * 描述: 通用AI模型接口
 * @author suifeng
 * 日期: 2024/12/12
 */
public interface AIModel {

    /**
     * 调用模型生成内容
     * @param content 请求内容
     */
    String generate(String content);
}