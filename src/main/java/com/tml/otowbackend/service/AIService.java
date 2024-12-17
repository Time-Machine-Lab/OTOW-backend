package com.tml.otowbackend.service;

import com.tml.otowbackend.engine.tree.common.R;

public interface AIService {

    /**
     * AI 生成项目大纲描述
     *
     * @param projectId 项目ID
     * @return 生成的项目描述
     */
    R<String> generateProjectOutline(Long projectId);
}