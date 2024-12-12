package com.tml.otowbackend.engine.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述: 标记字段为长文本类型
 * @author suifeng
 * 日期: 2024/12/12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();
}