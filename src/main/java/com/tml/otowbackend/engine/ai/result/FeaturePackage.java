package com.tml.otowbackend.engine.ai.result;

import lombok.Data;

/**
 * 描述: 功能包定义类
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class FeaturePackage {
    private String id;          // 功能包ID
    private String description; // 功能包描述

    public FeaturePackage(String id, String description) {
        this.id = id;
        this.description = description;
    }
}