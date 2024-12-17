package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;

/**
 * JavaOTOWTemplate 是 {@link VelocityOTOWTemplate} 的子类，专为 Java 代码生成而设计。
 *
 * <p>该类继承自 {@link VelocityOTOWTemplate}，并通过构造函数传递模板文件路径。</p>
 * <p>具体的模板上下文和生成逻辑可以通过实现 {@link VelocityOTOWTemplate#getContext()} 方法来提供。</p>
 *
 * @see VelocityOTOWTemplate
 */
public abstract class JavaOTOWTemplate extends VelocityOTOWTemplate {

    /**
     * 构造函数，初始化模板文件路径。
     *
     * <p>通过调用父类构造函数 {@link VelocityOTOWTemplate#VelocityOTOWTemplate(String)} 来传递模板文件路径。</p>
     *
     * @param templateFilePath Java 代码模板文件的路径
     */
    protected JavaOTOWTemplate(String templateFilePath) {
        super(templateFilePath);
    }
}

