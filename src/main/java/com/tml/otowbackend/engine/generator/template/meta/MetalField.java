package com.tml.otowbackend.engine.generator.template.meta;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 生成实体类的成员变量描述
 */
/**
 * MetalField 表示实体类中的一个成员变量。
 *
 * <p>该类封装了成员变量的名称、类型、描述信息以及与该成员变量相关的注解。它提供了多个构造方法，支持通过不同的方式为成员变量设置注解。</p>
 * <p>此外，`MetalField` 继承自 {@link ImportChecker}，可以自动管理与该成员变量相关的导入类。</p>
 *
 * @see ImportChecker
 * @see MetaAnnotation
 */
@Data
public class MetalField extends ImportChecker {

    /**
     * 成员变量的名称。
     */
    private String name;

    /**
     * 成员变量的类型，通常为类的名称。
     */
    private String clazz;

    /**
     * 成员变量的描述信息。
     */
    private String description;

    /**
     * 与成员变量相关的注解列表。
     */
    private List<MetaAnnotation> annotations = new ArrayList<>();

    /**
     * 构造一个 `MetalField` 对象，设置名称、类型和注解。
     *
     * @param name 成员变量名称
     * @param clazz 成员变量类型
     * @param annotations 与成员变量相关的注解列表
     */
    public MetalField(String name, Class<?> clazz, List<MetaAnnotation> annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(annotations);
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetalField` 对象，设置名称、类型和单个注解。
     *
     * @param name 成员变量名称
     * @param clazz 成员变量类型
     * @param annotations 与成员变量相关的单个注解
     */
    public MetalField(String name, Class<?> clazz, MetaAnnotation annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(List.of(annotations));
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetalField` 对象，设置名称和类型（没有注解）。
     *
     * @param name 成员变量名称
     * @param clazz 成员变量类型
     */
    public MetalField(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个 `MetalField` 对象，设置名称和类型（类型是字符串形式）。
     *
     * @param name 成员变量名称
     * @param clazz 成员变量类型（字符串形式）
     */
    public MetalField(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 为成员变量添加注解列表。
     *
     * @param annotations 注解列表
     */
    public void addAnnotations(List<MetaAnnotation> annotations) {
        addImportCheckers(annotations);
        this.annotations.addAll(annotations);
    }

    /**
     * 重写 `equals` 方法，通过成员变量名称比较两个 `MetalField` 对象是否相等。
     *
     * @param o 另一个对象
     * @return 如果对象相等则返回 `true`，否则返回 `false`
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetalField that = (MetalField) o;
        return name.equals(that.name);
    }

    /**
     * 重写 `hashCode` 方法，使用成员变量名称生成哈希码。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
