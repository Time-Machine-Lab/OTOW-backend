package com.tml.otowbackend.core.generator.template.meta;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MetaMethodParam extends ImportChecker{

    protected List<MetaAnnotation> annotations = new ArrayList<>();

    // 成员变量名称
    private String name;

    // 成员变量类型
    private String clazz;

    public MetaMethodParam(Class<?> clazz, String name, List<MetaAnnotation> annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(annotations);
        this.addImportClazz(clazz);
    }

    public MetaMethodParam(Class<?> clazz, String name, MetaAnnotation annotations) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        addAnnotations(List.of(annotations));
        this.addImportClazz(clazz);
    }

    public MetaMethodParam(Class<?> clazz, String name) {
        this.name = name;
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }

    public MetaMethodParam(String clazz, String classPath, String name) {
        this.name = name;
        this.clazz = clazz;
        this.addImportPath(classPath);
    }


    public MetaMethodParam(String clazz, String name) {
        this.name = name;
        this.clazz = clazz;
    }

    public MetaMethodParam(Class<?> clazz, MetaAnnotation annotation) {
        String className = clazz.getSimpleName();
        this.name = className.toLowerCase();
        this.clazz = className;
        addAnnotations(List.of(annotation));
    }

    public MetaMethodParam(Class<?> clazz) {
        String className = clazz.getSimpleName();
        this.name = className.toLowerCase();
        this.clazz = className;
    }


    public void addAnnotations(List<MetaAnnotation> annotations){
        addImportCheckers(annotations);
        this.annotations.addAll(annotations);
    }
}
