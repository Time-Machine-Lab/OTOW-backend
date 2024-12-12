package com.tml.otowbackend.engine.ai.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 描述: 实体类
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityClassDefinition {

    private String className; // 实体类名
    private String cdesc; // 实体类中文名
    private List<FieldDefinition> fields; // 字段列表
    private List<String> featureIds; // 功能包ID列表

    @Data
    public static class FieldDefinition {
        private String fname;     // 字段名
        private String ftype;     // 字段类型
        private String fdesc;     // 字段描述
    }
}