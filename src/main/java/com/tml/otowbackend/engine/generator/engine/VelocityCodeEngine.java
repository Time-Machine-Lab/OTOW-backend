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
/**
 * VelocityCodeEngine 是一个使用 Apache Velocity 模板引擎的代码生成器。
 *
 * <p>此类负责加载 Velocity 模板，并根据给定的上下文数据生成代码。</p>
 * <p>它使用单例模式，确保只有一个 VelocityCodeEngine 实例。</p>
 *
 * <p>此类继承自 Engine，生成 VelocityOTOWTemplate 类型的代码。</p>
 *
 * @see VelocityOTOWTemplate
 * @see Engine
 */
public class VelocityCodeEngine extends Engine<VelocityOTOWTemplate, String> {

    /**
     * 存储 Velocity 引擎的配置属性。
     *
     * <p>这些配置包括模板文件的路径和输入输出编码设置。</p>
     */
    private static Properties props;

    /**
     * 静态代码块，用于初始化 Velocity 配置属性。
     *
     * <p>该代码块会在类加载时执行，设置模板文件的路径和编码格式。</p>
     */
    static {
        props = new Properties();
        // 获取模板文件的资源路径
        String resourcePath = Objects.requireNonNull(VelocityCodeEngine.class.getClassLoader().getResource("template")).getPath().replaceAll("%20", " ");
        props.setProperty("file.resource.loader.path", resourcePath); // 设置模板路径
        props.setProperty("input.encoding", "UTF-8"); // 设置输入文件的编码
        props.setProperty("output.encoding", "UTF-8"); // 设置输出文件的编码
    }

    /**
     * 单例模式下的 VelocityCodeEngine 实例。
     */
    private static VelocityCodeEngine codeEngine = new VelocityCodeEngine();

    /**
     * Velocity 引擎实例，用于处理模板。
     */
    private VelocityEngine engine;

    /**
     * 构造函数，初始化 Velocity 引擎。
     *
     * <p>使用配置属性初始化 Velocity 引擎实例。</p>
     *
     * @throws RuntimeException 如果 Velocity 引擎初始化失败，抛出此异常。
     */
    public VelocityCodeEngine() {
        try {
            this.engine = new VelocityEngine(props);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 VelocityCodeEngine 单例实例。
     *
     * <p>返回 VelocityCodeEngine 的唯一实例。</p>
     *
     * @return VelocityCodeEngine 的单例实例
     */
    public static VelocityCodeEngine getCodeEngine() {
        return codeEngine;
    }

    /**
     * 生成代码的方法。
     *
     * <p>使用给定的 VelocityOTOWTemplate 和其上下文数据，处理 Velocity 模板并生成最终的代码字符串。</p>
     *
     * @param template 用于生成代码的模板对象
     * @return 生成的代码字符串，如果发生异常则返回 null
     */
    @Override
    public String generate(VelocityOTOWTemplate template) {
        try {
            // 获取模板文件
            Template velocityTemplate = engine.getTemplate(template.getTemplateFilePath());
            // 获取模板上下文
            VelocityContext context = template.getContext();
            // 使用 StringWriter 输出生成的代码
            StringWriter writer = new StringWriter();
            velocityTemplate.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}