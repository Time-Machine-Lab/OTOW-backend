package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;

/**
 * 方法模板抽象类
 *
 * <p>该类用于生成方法模板，子类可以实现生成具体方法的逻辑。</p>
 * <p>该类依赖于 Velocity 模板引擎生成方法体的代码。</p>
 *
 * @see MetaMethod
 * @see VelocityCodeEngine
 */
public abstract class MethodTemplate extends VelocityOTOWTemplate {

    /**
     * 构造方法，初始化方法模板。
     *
     * @param templateFilePath 方法模板文件的路径
     */
    protected MethodTemplate(String templateFilePath) {
        super(templateFilePath);
    }

    /**
     * 生成方法的元数据。
     * <p>子类需要实现该方法来生成具体的 `MetaMethod` 对象。</p>
     *
     * @return 生成的 `MetaMethod` 对象，表示方法的元数据
     */
    public abstract MetaMethod generateMethod();

    /**
     * 生成方法体的代码。
     * <p>该方法使用 Velocity 模板引擎生成方法体的代码。</p>
     *
     * @return 生成的代码字符串
     */
    public String generateMethodBody() {
        return VelocityCodeEngine.getCodeEngine().generate(this);
    }
}
