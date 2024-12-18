package com.tml.otowbackend.engine.sql.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述: 描述，标注
 * @author suifeng
 * 日期: 2024/12/18
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Describe {
    String value() default "";
}
