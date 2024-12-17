package com.tml.otowbackend.engine.generator.template.meta;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * MetaAnnotation 用于表示 Java 类的注解及其参数信息。
 *
 * <p>该类封装了注解的类名（`clazz`）以及注解的参数（`params`）。它支持通过构造方法创建不同类型的注解，并且能够处理注解参数的不同数据类型。</p>
 * <p>该类继承自 {@link ImportChecker}，并允许管理导入相关的类。</p>
 *
 * @see ImportChecker
 */
@Data
public class MetaAnnotation extends ImportChecker {

    /**
     * 注解的参数，存储为键值对，其中键是参数名，值是参数值。
     */
    private Map<String, String> params = new HashMap<>();

    /**
     * 注解的类名。
     */
    private String clazz;

    /**
     * 构造一个带有单一参数的 MetaAnnotation。
     *
     * @param clazz 注解类
     * @param param 注解参数
     */
    public MetaAnnotation(Class<?> clazz, String param) {
        this.clazz = clazz.getSimpleName();
        putParam("", param);
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个带有键值对参数的 MetaAnnotation。
     *
     * @param clazz 注解类
     * @param key 参数键
     * @param param 参数值
     */
    public MetaAnnotation(Class<?> clazz, String key, String param) {
        this.clazz = clazz.getSimpleName();
        putParam(key, param);
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个带有键值对参数且支持非字符串类型参数的 MetaAnnotation。
     *
     * @param clazz 注解类
     * @param key 参数键
     * @param param 参数值
     * @param paramClazz 参数值的类型类
     */
    public MetaAnnotation(Class<?> clazz, String key, String param, Class<?> paramClazz) {
        this.clazz = clazz.getSimpleName();
        putParam(key, param, paramClazz);
        this.addImportClazz(clazz);
    }

    /**
     * 构造一个不带参数的 MetaAnnotation。
     *
     * @param clazz 注解类
     */
    public MetaAnnotation(Class<?> clazz) {
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }

    /**
     * 将注解的参数添加到 `params` 中。该参数会被格式化为字符串。
     *
     * @param key 参数名
     * @param value 参数值
     */
    public void putParam(String key, String value) {
        this.params.put(key, String.format("\"%s\"", value));
    }

    /**
     * 将非字符串类型的注解参数添加到 `params` 中，并将其类加入导入类中。
     *
     * @param key 参数名
     * @param value 参数值
     * @param clazz 参数值的类型类
     */
    public void putParam(String key, String value, Class<?> clazz) {
        this.addImportClazz(clazz);
        this.params.put(key, value);
    }

    /**
     * 重写 `equals` 方法，通过 `clazz` 属性比较两个 MetaAnnotation 对象是否相等。
     *
     * @param o 另一个对象
     * @return 如果对象相等则返回 `true`，否则返回 `false`
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaAnnotation that = (MetaAnnotation) o;
        return clazz.equals(that.clazz);
    }

    /**
     * 重写 `hashCode` 方法，使用 `clazz` 属性生成哈希码。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    /**
     * 将一个类列表转换为 MetaAnnotation 对象列表。
     *
     * <p>此方法将每个类转换为一个对应的 {@link MetaAnnotation} 对象，并返回一个 {@link List}。</p>
     *
     * @param classes 类列表
     * @return 转换后的 {@link MetaAnnotation} 对象列表
     */
    public static List<MetaAnnotation> convertByClazz(List<Class<?>> classes) {
        return classes.stream().map(MetaAnnotation::new).collect(Collectors.toList());
    }
}
