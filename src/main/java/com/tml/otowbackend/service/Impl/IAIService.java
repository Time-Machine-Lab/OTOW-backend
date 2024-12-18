package com.tml.otowbackend.service.Impl;

import com.tml.otowbackend.engine.ai.core.AIModel;
import com.tml.otowbackend.engine.ai.core.AIOperation;
import com.tml.otowbackend.engine.ai.core.AIOperationFactory;
import com.tml.otowbackend.engine.ai.core.ParseResult;
import com.tml.otowbackend.engine.otow.OTOWCacheService;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.service.AIService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.tml.otowbackend.constants.AIConstant.GENERATE_DESC;
import static com.tml.otowbackend.constants.ProjectConstants.*;

@Service
public class IAIService implements AIService {

    @Resource
    private OTOWCacheService cacheService;

    @Resource
    private AIModel aiModel;

    @Override
    public R<String> generateProjectOutline(Long projectId) {
        Map<String, Object> cachedParams = cacheService.getAll(projectId);
        if (cachedParams.isEmpty()) {
            throw new ServeException("项目不存在或未初始化，ID: " + projectId);
        }
        String title = (String) cachedParams.get(TITLE);
        if (title == null || title.isBlank()) {
            throw new ServeException("项目标题不存在，无法生成项目大纲描述");
        }
        String complexity = (String) cachedParams.getOrDefault(COMPLEXITY, "一般");
        Map<String, Object> projectOutline = new HashMap<>();
        projectOutline.put(TITLE, title);
        projectOutline.put(COMPLEXITY, complexity);

        AIOperation<String> operation = (AIOperation<String>) AIOperationFactory.getOperation(GENERATE_DESC);
        String prompt = operation.generatePrompt(projectOutline);
        String generatedDescription = aiModel.generate(prompt);
        ParseResult<String> parseResult = operation.parseResponse(generatedDescription);
        String description = parseResult.getData();

        if (description == null || description.isBlank()) {
            throw new ServeException("AI 生成项目描述失败，请稍后重试");
        }
        cacheService.put(projectId, DESCRIPTION, description);
        return R.success("生成项目描述成功", description);
    }
}