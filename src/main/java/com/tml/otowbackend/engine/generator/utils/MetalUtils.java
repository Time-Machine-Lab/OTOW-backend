package com.tml.otowbackend.engine.generator.utils;

import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.sql.annotation.Describe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;

/**
 * 描述: 元数据工具类
 * @author suifeng
 * 日期: 2024/12/18
 */
public class MetalUtils {

    public static MetaAnnotation getDescribe(String value) {
        if (StringUtils.isBlank(value)) value = "未知";
        return new MetaAnnotation(Describe.class, "value", "\"" + value + "\"", "com.example.common.annotation.Describe");
    }

    public static MetaAnnotation getSwaggerTag(String className, String description) {
        if (StringUtils.isBlank(className)) className = "XXX";
        if (StringUtils.isBlank(description)) description = "XXX";
        MetaAnnotation annotation = new MetaAnnotation(Tag.class);
        annotation.putParam("name", className + " Controller");
        annotation.putParam("description", "管理 " + description + " 的 API 接口");
        return annotation;
    }

    public static MetaAnnotation getSwaggerOperation(String summary) {
        if (StringUtils.isBlank(summary)) summary = "XXX";
        MetaAnnotation annotation = new MetaAnnotation(Operation.class);
        annotation.putParam("summary", summary);
        return annotation;
    }
}
