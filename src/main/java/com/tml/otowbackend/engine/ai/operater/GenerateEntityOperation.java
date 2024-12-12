package com.tml.otowbackend.engine.ai.operater;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.ai.core.AIOperation;
import com.tml.otowbackend.engine.ai.core.ParseResult;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.tml.otowbackend.engine.ai.core.ValidDataExtractor.extractValidData;

/**
 * 描述: AI生成实体类操作
 * @author suifeng
 * 日期: 2024/12/12
 */
@Component
public class GenerateEntityOperation implements AIOperation<List<EntityClassDefinition>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generatePrompt(Map<String, Object> projectOutline) {
        String title = (String) projectOutline.getOrDefault("title", "未命名项目");
        String description = (String) projectOutline.getOrDefault("description", "无描述");
        String complexity = (String) projectOutline.getOrDefault("complexity", "一般");

        // 在提示词中明确规定有效信息的范围，用 ### 包裹
        return String.format(
                "请根据以下项目大纲生成多个对应的实体类，请严格遵循实例格式，保证实体类能够正确运行，并且能够用ObjectMapper正确解析，并将结果放在 ### 和 ### 之间：\n" +
                        "项目标题：%s\n" +
                        "项目描述：%s\n" +
                        "项目复杂程度：%s\n" +
                        "返回格式如下：\n" +
                        "###\n" +
                        "[\n" +
                        "  {\n" +
                        "    \"className\": \"实体类名\",\n" +
                        "    \"fields\": [\n" +
                        "      {\"fname\": \"字段名\", \"ftype\": \"字段类型\", \"fdesc\": \"字段中文翻译\"}\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]\n" +
                        "###",
                title, description, complexity
        );
    }

    @Override
    public ParseResult<List<EntityClassDefinition>> parseResponse(String aiResponse) {
        try {
            // 截取有效信息
            String validData = extractValidData(aiResponse);
            List<EntityClassDefinition> entities = objectMapper.readValue(validData, new TypeReference<List<EntityClassDefinition>>() {});
            return ParseResult.success(entities);
        } catch (Exception e) {
            // 如果解析失败，返回失败结果并记录错误信息
            return ParseResult.failure("解析失败：" + e.getMessage());
        }
    }
}