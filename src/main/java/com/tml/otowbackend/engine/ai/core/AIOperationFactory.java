package com.tml.otowbackend.engine.ai.core;

import com.tml.otowbackend.engine.ai.operater.GenerateEntityOperation;
import com.tml.otowbackend.engine.ai.operater.GenerateProjectDescriptionOperation;

import java.util.HashMap;
import java.util.Map;

import static com.tml.otowbackend.constants.AIConstant.GENERATE_DESC;
import static com.tml.otowbackend.constants.AIConstant.GENERATE_ENTITY;

/**
 * 描述: AI操作工厂
 * @author suifeng
 * 日期: 2024/12/12
 */
public class AIOperationFactory {

    private static final Map<String, AIOperation<?>> operations = new HashMap<>();

    static {
        // 注册任务类型对应的操作
        operations.put(GENERATE_ENTITY, new GenerateEntityOperation());
        operations.put(GENERATE_DESC, new GenerateProjectDescriptionOperation());
    }

    /**
     * 获取指定任务类型的操作
     *
     * @param taskType 任务类型
     * @return AI操作
     */
    public static AIOperation<?> getOperation(String taskType) {
        if (!operations.containsKey(taskType)) {
            throw new IllegalArgumentException("未知的任务类型：" + taskType);
        }
        return operations.get(taskType);
    }
}