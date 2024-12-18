package com.tml.otowbackend.engine.generator.template.meta;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ImportChecker 用于管理类和路径的导入集合。
 *
 * <p>该类提供了添加、获取和管理需要导入的类名或路径的方法。导入的内容将保存在一个 {@link Set} 中，避免重复导入。</p>
 * <p>子类可以使用此类来检查和管理所需的类或路径，并将它们添加到导入集合中。</p>
 */
public abstract class ImportChecker {

    /**
     * 存储导入类的类名或路径的集合。
     */
    protected Set<String> importClazz = new HashSet<>();

    /**
     * 获取当前的导入类名或路径集合。
     *
     * @return 当前所有需要导入的类名或路径
     */
    public Set<String> getImports() {
        return importClazz;
    }

    /**
     * 将多个类添加到导入集合中。
     *
     * <p>每个类的完整类名将被添加到导入集合中。</p>
     *
     * @param importClazz 需要导入的类集合
     */
    public void addImportClazz(List<Class<?>> importClazz) {
        this.importClazz.addAll(importClazz.stream()
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    /**
     * 将单个类添加到导入集合中。
     *
     * <p>类的完整类名将被添加到导入集合中。</p>
     *
     * @param clazz 需要导入的类
     */
    public void addImportClazz(Class<?> clazz) {
        this.importClazz.add(clazz.getName());
    }

    /**
     * 将多个类路径添加到导入集合中。
     *
     * <p>类路径将直接添加到导入集合中。</p>
     *
     * @param importPath 需要导入的类路径集合
     */
    public void addImportPaths(Set<String> importPath) {
        this.importClazz.addAll(importPath);
    }

    /**
     * 将单个类路径添加到导入集合中。
     *
     * <p>类路径将被直接添加到导入集合中。</p>
     *
     * @param path 需要导入的类路径
     */
    protected void addImportPath(String path) {
        if (!StringUtils.isBlank(path)) {
            this.importClazz.add(path);
        }
    }

    /**
     * 将另一个 {@link ImportChecker} 对象的导入集合合并到当前导入集合中。
     *
     * <p>从另一个 {@link ImportChecker} 实例获取其所有导入路径，并将其添加到当前的导入集合。</p>
     *
     * @param importChecker 另一个 {@link ImportChecker} 实例
     */
    public void addImportChecker(ImportChecker importChecker){
        this.addImportPaths(importChecker.getImports());
    }

    /**
     * 将多个 {@link ImportChecker} 对象的导入集合合并到当前导入集合中。
     *
     * <p>从多个 {@link ImportChecker} 实例获取它们的导入路径，并将它们添加到当前的导入集合。</p>
     *
     * @param importCheckers {@link ImportChecker} 对象的集合
     */
    public void addImportCheckers(List<? extends ImportChecker> importCheckers){
        for (ImportChecker param : importCheckers) {
            this.addImportPaths(param.getImports());
        }
    }
    public void addImportCheckers(ImportChecker importChecker){
        this.addImportPaths(importChecker.getImports());
    }
}