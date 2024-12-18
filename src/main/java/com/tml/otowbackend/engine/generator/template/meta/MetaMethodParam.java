package com.tml.otowbackend.engine.generator.template.meta;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaMethodParam 类表示一个方法参数的元数据。
 *
 * <p>该类封装了方法参数的名称、类型、注解等信息，适用于动态生成代码时对方法参数的描述。</p>
 * <p>支持为方法参数添加多个注解，并自动处理类的导入。</p>
 *
 * @see MetaAnnotation
 */
@Data
public class MetaMethodParam extends ImportChecker {

    /**
     * 方法参数的注解列表。
     */
    protected List<MetaAnnotation> annotations = new ArrayList<>();

    /**
     * 方法参数的名称。
     */
    private String name;

    /**
     * 方法参数的类型。
     */
    private String clazz;

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型、名称和注解。
     *
     * @param clazz 参数类型
     * @param name 参数名称
     * @param annotations 参数注解列表
     */
    public MetaMethodParam(Class<?> clazz, String name, List<MetaAnnotation> annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(annotations);
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型、名称和单个注解。
     *
     * @param clazz 参数类型
     * @param name 参数名称
     * @param annotations 参数注解
     */
    public MetaMethodParam(Class<?> clazz, String name, MetaAnnotation annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(List.of(annotations));
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型和名称，且不添加任何注解。
     *
     * @param clazz 参数类型
     * @param name 参数名称
     */
    public MetaMethodParam(Class<?> clazz, String name) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型（字符串形式）、类路径和名称。
     *
     * @param clazz 参数类型（字符串形式）
     * @param classPath 类路径
     * @param name 参数名称
     */
    public MetaMethodParam(String clazz, String classPath, String name) {
        this.clazz = clazz;
        this.addImportPath(classPath);
        this.name = name;
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型（字符串形式）和名称。
     *
     * @param clazz 参数类型（字符串形式）
     * @param name 参数名称
     */
    public MetaMethodParam(String clazz, String name) {
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型为 `clazz` 并使用类名作为名称（小写形式）。
     *
     * @param clazz 参数类型
     * @param annotation 参数注解
     */
    public MetaMethodParam(Class<?> clazz, MetaAnnotation annotation) {
        String className = clazz.getSimpleName();
        this.name = className.toLowerCase();
        this.clazz = className;
        addAnnotations(List.of(annotation));
    }

    /**
     * 构造一个 `MetaMethodParam` 对象，设置参数类型为 `clazz` 并使用类名作为名称（小写形式）。
     *
     * @param clazz 参数类型
     */
    public MetaMethodParam(Class<?> clazz) {
        String className = clazz.getSimpleName();
        this.name = className.toLowerCase();
        this.clazz = className;
    }

    /**
     * 添加方法参数的注解列表。
     *
     * @param annotations 参数注解列表
     */
    public void addAnnotations(List<MetaAnnotation> annotations) {
        addImportCheckers(annotations);
        this.annotations.addAll(annotations);
    }
}
