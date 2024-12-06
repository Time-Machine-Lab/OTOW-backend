package com.tml.otowbackend.core.generator.template.meta;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 生成实体类的成员变量描述
 */
@Data
public class MetalField extends ImportChecker{

    // 成员变量名称
    private String name;

    // 成员变量类型
    private String clazz;

    // 属性注释
    private String description;

    private List<MetaAnnotation> annotations = new ArrayList<>();


    public MetalField(String name, Class<?> clazz, List<MetaAnnotation> annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(annotations);
        this.addImportClazz(clazz);
    }

    public MetalField(String name, Class<?> clazz, MetaAnnotation annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(List.of(annotations));
        this.addImportClazz(clazz);
    }

    public MetalField(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }

    public MetalField(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public void addAnnotations(List<MetaAnnotation> annotations){
        addImportCheckers(annotations);
        this.annotations.addAll(annotations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetalField that = (MetalField) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
