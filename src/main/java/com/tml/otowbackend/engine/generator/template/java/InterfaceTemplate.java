package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.template.meta.ImportChecker;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.util.ImportUtil;
import org.apache.velocity.VelocityContext;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tml.otowbackend.constants.TemplateConstant.INTERFACE_TEMPLATE_PATH;

public abstract class InterfaceTemplate extends JavaOTOWTemplate{
    // 类需要导入的类
    protected final Set<String> imports = new HashSet<>();

    // 类上所加的注解
    protected final Set<MetaAnnotation> annotations = new HashSet<>();

    protected final Set<MetaMethod> methods = new HashSet<>();

    // 类所在的包文件夹
    protected String packagePath;

    protected final String className;

    // 继承的父类
    protected String fatherClazz = "";

    public InterfaceTemplate(String packagePath, String className) {
        super(INTERFACE_TEMPLATE_PATH);
        initImports();
        initAnnotations();
        this.packagePath = packagePath;
        this.className = className;
    }

    // 获取当前类包路径
    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getAllPackagePath() {
        return packagePath+"."+className;
    }

    // 获取当前类名
    public String getClassName() {
        return className;
    }

    // 类引用包
    public Set<String> getImports() {
        return imports;
    }

    public void addImportClazz(List<Class<?>> importClazz) {
        this.imports.addAll(importClazz.stream()
                .filter(clazz-> ImportUtil.needsImport(clazz, packagePath))
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    public void addImportClazz(Class<?> clazz) {
        if(ImportUtil.needsImport(clazz, packagePath)){
            this.imports.add(clazz.getName());
        }
    }

    public void addImportPaths(List<String> importPath) {
        this.imports.addAll(importPath);
    }

    public void addImportPath(String path) {
        this.imports.add(path);
    }

    // 类继承与实现
    public String getFatherClazz() {
        return fatherClazz;
    }

    public void setFatherClazz(Class<?> fatherClazz) {
        addImportClazz(fatherClazz);
        this.fatherClazz = fatherClazz.getSimpleName();
    }

    public void setFatherClazz(String fatherClazz, String clazzPath) {
        addImportPath(clazzPath);
        this.fatherClazz = fatherClazz;
    }

    // 类注解
    public void addAnnotations(List<MetaAnnotation> annotations){
        addImportAndCheck(annotations);
        this.annotations.addAll(annotations);
    }

    public void addAnnotation(MetaAnnotation annotation){
        addImportAndCheck(annotation);
        this.annotations.add(annotation);
    }

    // 类方法
    public void addMethods(List<MetaMethod> metaMethods){
        this.methods.addAll(metaMethods);
    }

    public void addMethod(MetaMethod metaMethod){
        this.methods.add(metaMethod);
    }

    public void addMethod(String methodName, List<MetaMethodParam> params){
//        System.out.println(params);
        MetaMethod metaMethod = new MetaMethod(methodName, params, "");
//        System.out.println(metaMethod);
        addMethod(metaMethod);
    }


    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("package", packagePath);
        context.put("imports", imports);
        context.put("className", className);
        context.put("fatherClazz", fatherClazz);
        context.put("annotations", annotations);
        context.put("methods", methods);
//        System.out.println(methods);
        return context;
    }

    public void addImportAndCheck(ImportChecker importChecker){
        importChecker.getImports().forEach(importClazz->{
            if (ImportUtil.needsImport(importClazz, this.packagePath)) {
                this.imports.add(importClazz);
            }
        });
    }

    public void addImportAndCheck(List<? extends ImportChecker> importCheckers){
        for (ImportChecker importChecker : importCheckers) {
            addImportAndCheck(importChecker);
        }
    }

    public void initImports(){

    };

    public void initAnnotations(){

    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterfaceTemplate that = (InterfaceTemplate) o;
        return getAllPackagePath().equals(that.getAllPackagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAllPackagePath());
    }
}
