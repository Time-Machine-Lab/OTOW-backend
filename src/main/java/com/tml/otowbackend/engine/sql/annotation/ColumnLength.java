package com.tml.otowbackend.engine.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述: 指定字段的长度
 * @author suifeng
 * 日期: 2024/12/12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnLength {
    int value() default 255; // 默认长度为 255
}