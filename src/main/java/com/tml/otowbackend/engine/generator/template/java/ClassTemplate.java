package com.tml.otowbackend.engine.generator.template.java;

import com.tml.otowbackend.engine.generator.template.meta.ImportChecker;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
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
/**
 * 类文件生成模板
 *
 * <p>此抽象类用于生成 Java 类文件的模板，支持设置类的包路径、类名、导入的类、类的成员变量、类的方法、类的继承关系和实现的接口。</p>
 * <p>子类可以通过继承该类并实现相关的模板方法来自定义类的生成逻辑。</p>
 *
 * @see MetaAnnotation
 * @see MetalField
 * @see MetaMethod
 */
public abstract class ClassTemplate extends JavaOTOWTemplate {

    /**
     * 类需要导入的类名集合。
     */
    protected final Set<String> imports = new HashSet<>();

    /**
     * 类的成员变量集合。
     */
    protected final Set<MetalField> metalFields = new HashSet<>();

    /**
     * 类上添加的注解集合。
     */
    protected final Set<MetaAnnotation> annotations = new HashSet<>();

    /**
     * 类的方法集合。
     */
    protected final Set<MetaMethod> methods = new HashSet<>();

    /**
     * 类继承的父类。
     */
    protected String fatherClazz = "";

    /**
     * 类实现的接口列表。
     */
    protected List<String> interfaces = new ArrayList<>();

    /**
     * 类所在的包路径。
     */
    protected String packagePath;

    /**
     * 类名。
     */
    protected final String className;

    /**
     * 构造方法，初始化类的包路径和类名。
     *
     * @param packagePath 类所在的包路径
     * @param className 类名
     */
    public ClassTemplate(String packagePath, String className) {
        super(CLASS_TEMPLATE_PATH);
        initImports();
        initAnnotations();
        this.packagePath = packagePath;
        this.className = className;
    }

    /**
     * 获取当前类的包路径。
     *
     * @return 当前类的包路径
     */
    public String getPackagePath() {
        return packagePath;
    }

    /**
     * 设置当前类的包路径。
     *
     * @param packagePath 包路径
     */
    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    /**
     * 获取当前类的完整包路径（包括类名）。
     *
     * @return 完整的包路径
     */
    public String getAllPackagePath() {
        return packagePath + "." + className;
    }

    /**
     * 获取当前类的类名。
     *
     * @return 类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 获取当前类所需导入的类名集合。
     *
     * @return 导入的类名集合
     */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * 添加导入类的集合。
     *
     * @param importClazz 需要导入的类集合
     */
    protected void addImportClazz(List<Class<?>> importClazz) {
        this.imports.addAll(importClazz.stream()
                .filter(clazz -> ImportUtil.needsImport(clazz, packagePath))
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    /**
     * 添加单个类到导入类集合。
     *
     * @param clazz 需要导入的类
     */
    protected void addImportClazz(Class<?> clazz) {
        if (ImportUtil.needsImport(clazz, packagePath)) {
            this.imports.add(clazz.getName());
        }
    }

    /**
     * 添加类路径集合。
     *
     * @param importPath 类路径集合
     */
    protected void addImportPaths(Set<String> importPath) {
        this.imports.addAll(importPath);
    }

    /**
     * 添加单个类路径。
     *
     * @param path 类路径
     */
    protected void addImportPath(String path) {
        this.imports.add(path);
    }

    /**
     * 添加类的注解集合。
     *
     * @param annotations 类注解集合
     */
    protected void addAnnotations(List<MetaAnnotation> annotations) {
        addImportAndCheck(annotations);
        this.annotations.addAll(annotations);
    }

    /**
     * 添加单个类注解。
     *
     * @param annotation 类注解
     */
    protected void addAnnotation(MetaAnnotation annotation) {
        addImportAndCheck(annotation);
        this.annotations.add(annotation);
    }

    /**
     * 获取类的父类。
     *
     * @return 类的父类
     */
    protected String getFatherClazz() {
        return fatherClazz;
    }

    /**
     * 设置类的父类。
     *
     * @param fatherClazz 父类类型
     */
    protected void setFatherClazz(Class<?> fatherClazz) {
        addImportClazz(fatherClazz);
        this.fatherClazz = fatherClazz.getSimpleName();
    }

    /**
     * 设置类的父类，并指定类路径。
     *
     * @param fatherClazz 父类名称
     * @param clazzPath 类路径
     */
    protected void setFatherClazz(String fatherClazz, String clazzPath) {
        addImportPath(clazzPath);
        this.fatherClazz = fatherClazz;
    }

    /**
     * 添加类实现的接口集合。
     *
     * @param interfaces 接口类型集合
     */
    protected void addInterfaces(List<Class<?>> interfaces) {
        addImportClazz(interfaces);
        this.interfaces.addAll(interfaces.stream().map(Class::getSimpleName).collect(Collectors.toSet()));
    }

    /**
     * 添加单个接口实现。
     *
     * @param interfaceClazz 接口类型
     */
    protected void addInterfaces(Class<?> interfaceClazz) {
        addImportClazz(interfaceClazz);
        this.interfaces.add(interfaceClazz.getSimpleName());
    }

    /**
     * 添加接口实现，并指定类路径。
     *
     * @param interfaceName 接口名称
     * @param clazzPath 接口的类路径
     */
    protected void addInterfaces(String interfaceName, String clazzPath) {
        addImportPath(clazzPath);
        this.interfaces.add(interfaceName);
    }

    /**
     * 获取类的成员变量集合。
     *
     * @return 类的成员变量集合
     */
    public Set<MetalField> getModelFields() {
        return metalFields;
    }

    /**
     * 添加类的成员变量。
     *
     * @param metalField 类成员变量
     */
    public void addModelField(MetalField metalField) {
        addImportAndCheck(metalField);
        this.metalFields.add(metalField);
    }

    /**
     * 添加类的多个成员变量。
     *
     * @param metalFields 类成员变量集合
     */
    public void addModelFields(List<MetalField> metalFields) {
        addImportAndCheck(metalFields);
        this.metalFields.addAll(metalFields);
    }

    /**
     * 添加类的方法集合。
     *
     * @param metaMethods 类的方法集合
     */
    protected void addMethods(List<MetaMethod> metaMethods) {
        addImportAndCheck(metaMethods);
        this.methods.addAll(metaMethods);
    }

    /**
     * 添加单个方法。
     *
     * @param metaMethod 类方法
     */
    protected void addMethod(MetaMethod metaMethod) {
        addImportAndCheck(metaMethod);
        this.methods.add(metaMethod);
    }

    /**
     * 获取类模板的上下文数据。
     *
     * @return VelocityContext 上下文数据
     */
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

    /**
     * 添加导入类的检查方法。
     *
     * @param importChecker 导入类检查器
     */
    protected void addImportAndCheck(ImportChecker importChecker) {
        this.addImportPaths(importChecker.getImports());
    }

    /**
     * 添加多个导入类的检查方法。
     *
     * @param importCheckers 导入类检查器列表
     */
    protected void addImportAndCheck(List<? extends ImportChecker> importCheckers) {
        for (ImportChecker importChecker : importCheckers) {
            addImportAndCheck(importChecker);
        }
    }

    /**
     * 初始化类的导入类集合。子类可以重写此方法以进行自定义初始化。
     */
    public void initImports() {
    }

    /**
     * 初始化类的注解集合。子类可以重写此方法以进行自定义初始化。
     */
    public void initAnnotations() {
    }
}
