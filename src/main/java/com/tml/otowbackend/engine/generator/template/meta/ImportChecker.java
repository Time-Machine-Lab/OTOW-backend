package com.tml.otowbackend.engine.generator.template.meta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ImportChecker {

    protected Set<String> importClazz = new HashSet<>();

    public Set<String> getImports() {
        return importClazz;
    }

    public void addImportClazz(List<Class<?>> importClazz) {
        this.importClazz.addAll(importClazz.stream()
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    public void addImportClazz(Class<?> clazz) {
        this.importClazz.add(clazz.getName());
    }

    public void addImportPaths(Set<String> importPath) {
        this.importClazz.addAll(importPath);
    }

    protected void addImportPath(String path) {
        this.importClazz.add(path);
    }

    public void addImportChecker(ImportChecker importChecker){
        this.addImportPaths(importChecker.getImports());
    }

    public void addImportCheckers(List<? extends ImportChecker> importCheckers){
        for (ImportChecker param : importCheckers) {
            this.addImportPaths(param.getImports());
        }
    }
}
