package com.tml.otowbackend.core.generator.template.meta;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class MetaAnnotation extends ImportChecker{

    private Map<String, String> params = new HashMap<>();

    private String clazz;

    public MetaAnnotation(Class<?> clazz, String param) {
        this.clazz = clazz.getSimpleName();
        putParam("", param);
        this.addImportClazz(clazz);
    }

    public MetaAnnotation(Class<?> clazz, String key, String param) {
        this.clazz = clazz.getSimpleName();
        putParam(key, param);
        this.addImportClazz(clazz);
    }

    public MetaAnnotation(Class<?> clazz, String key, String param, Class<?> paramClazz) {
        this.clazz = clazz.getSimpleName();
        putParam(key, param, paramClazz);
        this.addImportClazz(clazz);
    }

    public MetaAnnotation(Class<?> clazz) {
        this.clazz = clazz.getSimpleName();
        this.addImportClazz(clazz);
    }


    public void putParam(String key, String value) {
        this.params.put(key, String.format("\"%s\"", value));
    }

    /**
     * 放入非字符串类型的annotation
     * @param key
     * @param value
     * @param clazz
     */
    public void putParam(String key, String value, Class<?> clazz) {
        this.addImportClazz(clazz);
        this.params.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaAnnotation that = (MetaAnnotation) o;
        return clazz.equals(that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    public static List<MetaAnnotation> convertByClazz(List<Class<?>> classes){
        return classes.stream().map(MetaAnnotation::new).collect(Collectors.toList());
    }
}
