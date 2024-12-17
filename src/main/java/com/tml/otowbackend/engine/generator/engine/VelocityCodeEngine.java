package com.tml.otowbackend.engine.generator.engine;

import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Objects;
import java.util.Properties;

/**
 * velocity工厂
 */
public class VelocityCodeEngine extends Engine<VelocityOTOWTemplate, String> {

    private static Properties props;

    static{
        props = new Properties();
        String resourcePath = Objects.requireNonNull(VelocityCodeEngine.class.getClassLoader().getResource("template")).getPath().replaceAll("%20", " ");
        props.setProperty("file.resource.loader.path", resourcePath); // 设置模板路径
        props.setProperty("input.encoding", "UTF-8"); // 设置输入文件的编码
        props.setProperty("output.encoding", "UTF-8"); // 设置输出文件的编码
    }

    private static VelocityCodeEngine codeEngine = new VelocityCodeEngine();

    private VelocityEngine engine;

    public VelocityCodeEngine() {
        try {
            this.engine = new VelocityEngine(props);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static VelocityCodeEngine getCodeEngine(){
        return codeEngine;
    }

    @Override
    public String generate(VelocityOTOWTemplate template) {
        try {
            Template velocityTemplate = engine.getTemplate(template.getTemplateFilePath());
            VelocityContext context = template.getContext();
            StringWriter writer = new StringWriter();
            velocityTemplate.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
