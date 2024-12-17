package com.tml.otowbackend.engine.generator.engine;


import com.tml.otowbackend.engine.generator.template.OTOWTemplate;

/**
 * Engine 是一个抽象类，用于生成基于模板的输出。
 *
 * <p>该类是一个通用模板生成引擎的抽象类，定义了一个泛型方法 {@link #generate(OTOWTemplate)} 来处理模板并生成相应的结果。</p>
 * <p>子类需要实现 {@link #generate(OTOWTemplate)} 方法，提供具体的生成逻辑。</p>
 *
 * @param <T> 模板类型，必须是 {@link OTOWTemplate} 或其子类
 * @param <R> 生成结果的类型
 *
 * @see OTOWTemplate
 */
public abstract class Engine<T extends OTOWTemplate, R> {

    /**
     * 生成结果的方法，子类需要实现具体的生成逻辑。
     *
     * <p>该方法接收一个模板对象，并根据模板生成相应的结果。</p>
     *
     * @param template 用于生成结果的模板对象
     * @return 生成的结果，可以是任何类型（具体由子类定义）
     *
     * @throws Exception 如果生成过程中发生错误，子类应当抛出异常
     */
    public abstract R generate(T template) throws Exception;
}

