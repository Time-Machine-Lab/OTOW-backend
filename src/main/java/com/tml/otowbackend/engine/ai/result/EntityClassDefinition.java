package com.tml.otowbackend.engine.ai.result;

import lombok.Data;

import java.util.List;

/**
 * 描述: 实体类
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class EntityClassDefinition {

    private String className;
    private List<FieldDefinition> fields;

    @Data
    public static class FieldDefinition {
        private String fname;
        private String ftype;
        private String fdesc;
    }
}