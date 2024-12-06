package com.tml.otowbackend.core.generator.template.java;

import com.tml.otowbackend.core.generator.template.meta.ImportChecker;
import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.core.generator.template.meta.MetaMethod;
import com.tml.otowbackend.core.generator.template.meta.MetalField;
import com.tml.otowbackend.util.ImportUtil;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tml.otowbackend.constants.TemplateConstant.CLASS_TEMPLATE_PATH;

/**
 * 类文件生成模板
 */
public abstract class ClassTemplate extends JavaOTOWTemplate {

    // 类需要导入的类
    protected final Set<String> imports = new HashSet<>();

    // 类的成员变量
    protected final Set<MetalField> metalFields = new HashSet<>();

    // 类上所加的注解
    protected final Set<MetaAnnotation> annotations = new HashSet<>();

    // 类的方法
    protected final Set<MetaMethod> methods = new HashSet<>();

    // 继承的父类
    protected String fatherClazz = "";

    // 实现的接口
    protected List<String> interfaces = new ArrayList<>();

    // 类所在的包文件夹
    protected String packagePath;

    protected final String className;

    public ClassTemplate(String packagePath, String className) {
        super(CLASS_TEMPLATE_PATH);
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

    protected void addImportClazz(List<Class<?>> importClazz) {
        this.imports.addAll(importClazz.stream()
                .filter(clazz-> ImportUtil.needsImport(clazz, packagePath))
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    protected void addImportClazz(Class<?> clazz) {
        if(ImportUtil.needsImport(clazz, packagePath)){
            this.imports.add(clazz.getName());
        }
    }

    protected void addImportPaths(Set<String> importPath) {
        this.imports.addAll(importPath);
    }

    protected void addImportPath(String path) {
        this.imports.add(path);
    }

    // 类注解
    protected void addAnnotations(List<MetaAnnotation> annotations){
        addImportAndCheck(annotations);
        this.annotations.addAll(annotations);
    }

    protected void addAnnotation(MetaAnnotation annotation){
        addImportAndCheck(annotation);
        this.annotations.add(annotation);
    }

    // 类继承与实现
    protected String getFatherClazz() {
        return fatherClazz;
    }

    protected void setFatherClazz(Class<?> fatherClazz) {
        addImportClazz(fatherClazz);
        this.fatherClazz = fatherClazz.getSimpleName();
    }

    protected void setFatherClazz(String fatherClazz, String clazzPath) {
        addImportPath(clazzPath);
        this.fatherClazz = fatherClazz;
    }

    protected void addInterfaces(List<Class<?>> interfaces){
        addImportClazz(interfaces);
        this.interfaces.addAll(interfaces.stream().map(Class::getSimpleName).collect(Collectors.toSet()));
    }

    protected void addInterfaces(Class<?> interfaceClazz){
        addImportClazz(interfaceClazz);
        this.interfaces.add(interfaceClazz.getSimpleName());
    }

    protected void addInterfaces(String interfaceName, String clazzPath){
        addImportPath(clazzPath);
        this.interfaces.add(interfaceName);
    }

    // 类属性
    public Set<MetalField> getModelFields() {
        return metalFields;
    }

    public void addModelField(MetalField metalField){
        addImportAndCheck(metalField);
        this.metalFields.add(metalField);
    }

    public void addModelFields(List<MetalField> metalFields){
        addImportAndCheck(metalFields);
        this.metalFields.addAll(metalFields);
    }

    // 类方法
    protected void addMethods(List<MetaMethod> metaMethods){
        addImportAndCheck(metaMethods);
        this.methods.addAll(metaMethods);
    }

    protected void addMethod(MetaMethod metaMethod){
        addImportAndCheck(metaMethod);
        this.methods.add(metaMethod);
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("package", packagePath);
        context.put("imports", imports);
        context.put("className", className);
        context.put("fatherClazz", fatherClazz);
        context.put("implements", interfaces);
        context.put("fields", metalFields);
        context.put("annotations", annotations);
        context.put("methods", methods);
        return context;
    }

    protected void addImportAndCheck(ImportChecker importChecker){
        this.addImportPaths(importChecker.getImports());
    }

    protected void addImportAndCheck(List<? extends ImportChecker> importCheckers){
        for (ImportChecker importChecker : importCheckers) {
            addImportAndCheck(importChecker);
        }
    }

    public void initImports(){

    };

    public void initAnnotations(){

    };

}
