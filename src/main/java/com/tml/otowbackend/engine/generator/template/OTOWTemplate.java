package com.tml.otowbackend.engine.generator.template;

/**
 * 模板类
 */
/**
 * OTOWTemplate 是一个抽象模板类，表示生成代码或其他输出的模板。
 *
 * <p>该类定义了模板的基本结构，具体的模板内容和生成逻辑需要由子类实现。</p>
 * <p>子类可以根据不同的需求，扩展此模板类并提供相应的模板数据和上下文。</p>
 *
 */
public abstract class OTOWTemplate {

    /**
     * 默认构造函数，创建一个新的模板对象。
     *
     * <p>可以在子类中进行初始化，提供模板数据或上下文。</p>
     */
    public OTOWTemplate() {
        // 可根据需要在子类中实现初始化逻辑
    }
}

