package com.tml.otowbackend.controller.generate;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/otow/ai")
@RestController
@Validated
public class AIController {

    private final AIService aiService;

    /**
     * AI 根据项目标题生成项目大纲描述
     *
     * @param projectId 项目ID
     * @return 项目描述大纲
     */
    @PostMapping("/generateOutline")
    @TokenRequire
    public R<String> generateProjectOutline(@RequestParam Long projectId) {
        return aiService.generateProjectOutline(projectId);
    }
}