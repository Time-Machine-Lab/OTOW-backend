package com.tml.otowbackend.engine.ai.operater;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.ai.core.AIOperation;
import com.tml.otowbackend.engine.ai.core.ParseResult;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.tml.otowbackend.engine.ai.core.ValidDataExtractor.extractValidData;

/**
 * 描述: 根据项目标题生成项目描述的操作类
 * 通过 AI 提示词生成项目描述。
 * 示例返回格式：
 * {
 *   "description": "这是一个基于SpringBoot的电商管理系统，支持用户管理、商品管理和订单管理。"
 * }
 * 
 * @author suifeng
 * 日期: 2024/12/16
 */
@Component
public class GenerateProjectDescriptionOperation implements AIOperation<String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generatePrompt(Map<String, Object> projectOutline) {
        // 获取项目标题和复杂度
        String title = (String) projectOutline.getOrDefault("title", "未命名项目");
        String complexity = (String) projectOutline.getOrDefault("complexity", "一般");

        // 构造优化后的提示词，重点引导 AI 描述项目的功能和业务模块
        return String.format(
                "你是一个专业的AI项目分析师，请根据以下项目标题生成一个详细的项目功能和业务描述。确保能够用ObjectMapper正确解析，并将结果放在 ### 和 ### 之间：\n" +
                        "要求：\n" +
                        "1. 项目描述应基于标题，明确项目的核心功能模块和业务逻辑。\n" +
                        "2. 每个功能模块需简要说明其作用，例如用户管理、订单处理、支付功能等。\n" +
                        "3. 项目复杂度：%s（复杂度仅供参考，可适当调整功能模块的数量和描述的细节）。\n" +
                        "4. 输出格式必须为 JSON，确保格式正确，返回如下结构：\n" +
                        "###\n" +
                        "{\n" +
                        "  \"description\": \"项目功能和业务描述\"\n" +
                        "}\n" +
                        "###\n" +
                        "现在，请根据以下标题生成项目功能和业务描述：\n" +
                        "项目标题：%s\n" +
                        "请严格按照上述要求返回结果。字数不超过200字",
                complexity, title
        );
    }

    @Override
    public ParseResult<String> parseResponse(String aiResponse) {
        try {
            // 提取有效数据
            String validData = extractValidData(aiResponse);

            // 解析为 Map
            Map<String, Object> responseMap = objectMapper.readValue(validData, Map.class);

            // 获取描述字段
            String description = (String) responseMap.get("description");
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("AI 返回的描述为空");
            }

            return ParseResult.success(description);
        } catch (Exception e) {
            // 捕获解析异常并返回失败结果
            return ParseResult.failure("解析失败：" + e.getMessage());
        }
    }
}