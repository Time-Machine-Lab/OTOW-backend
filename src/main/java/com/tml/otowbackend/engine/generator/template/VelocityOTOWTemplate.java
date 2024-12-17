package com.tml.otowbackend.engine.generator.template;

import org.apache.velocity.VelocityContext;

/**
 * VelocityOTOWTemplate 是 {@link OTOWTemplate} 的子类，专为 Velocity 模板引擎设计。
 *
 * <p>该类表示一个 Velocity 模板，封装了模板文件路径，并提供了获取模板上下文的抽象方法。</p>
 * <p>子类可以通过实现 {@link #getContext()} 方法来提供具体的模板上下文。</p>
 *
 * @see OTOWTemplate
 */
public abstract class VelocityOTOWTemplate extends OTOWTemplate {

    /**
     * 模板文件的路径，用于指定模板文件的位置。
     */
    protected final String templateFilePath;

    /**
     * 构造函数，初始化模板文件路径。
     *
     * <p>此构造函数通过提供模板文件路径来初始化 VelocityOTOWTemplate 对象。</p>
     *
     * @param templateFilePath 模板文件的路径
     */
    public VelocityOTOWTemplate(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    /**
     * 获取模板文件路径。
     *
     * <p>该方法返回当前模板的文件路径，通常用于传递给 Velocity 引擎。</p>
     *
     * @return 模板文件的路径
     */
    public String getTemplateFilePath() {
        return templateFilePath;
    }

    /**
     * 获取模板上下文对象，子类需要实现该方法来提供具体的上下文。
     *
     * <p>该方法用于获取一个 {@link VelocityContext} 对象，Velocity 引擎会使用该上下文来合并模板和数据。</p>
     *
     * @return VelocityContext 对象，包含模板的上下文数据
     *
     * @see VelocityContext
     */
    public abstract VelocityContext getContext();
}