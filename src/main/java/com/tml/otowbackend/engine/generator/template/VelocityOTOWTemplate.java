package com.tml.otowbackend.engine.generator.template;

import org.apache.velocity.VelocityContext;

public abstract class VelocityOTOWTemplate extends OTOWTemplate {

    // 模板文件路径
    protected final String templateFilePath;

    public VelocityOTOWTemplate(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    /**
     * 对于模板生成引擎的容器操作
     * @return
     */
    public abstract VelocityContext getContext();
}
