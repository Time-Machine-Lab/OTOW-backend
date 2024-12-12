package com.tml.otowbackend.engine.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述: 标记字段不需要映射到数据库
 * @author suifeng
 * 日期: 2024/12/12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TransientField {
}