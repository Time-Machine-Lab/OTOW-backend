package com.tml.otowbackend.engine.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标记字段不需要映射到数据库
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TransientField {
}