package com.tml.otowbackend.engine.generator.utils;

import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.sql.annotation.Describe;
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
}
