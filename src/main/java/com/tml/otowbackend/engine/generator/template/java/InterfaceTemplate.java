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

/**
 * 接口文件生成模板
 *
 * <p>此抽象类用于生成 Java 接口文件的模板，支持设置接口的包路径、接口名、导入的类、接口的注解、方法等。</p>
 * <p>子类可以通过继承该类并实现相关的模板方法来自定义接口的生成逻辑。</p>
 *
 * @see MetaAnnotation
 * @see MetaMethod
 * @see MetaMethodParam
 */
public abstract class InterfaceTemplate extends JavaOTOWTemplate {

    /**
     * 接口需要导入的类名集合。
     */
    protected final Set<String> imports = new HashSet<>();

    /**
     * 接口上添加的注解集合。
     */
    protected final Set<MetaAnnotation> annotations = new HashSet<>();

    /**
     * 接口的方法集合。
     */
    protected final Set<MetaMethod> methods = new HashSet<>();

    /**
     * 接口所在的包路径。
     */
    protected String packagePath;

    /**
     * 接口名。
     */
    protected final String className;

    /**
     * 接口继承的父类。
     */
    protected String fatherClazz = "";

    /**
     * 构造方法，初始化接口的包路径和接口名。
     *
     * @param packagePath 接口所在的包路径
     * @param className 接口名
     */
    public InterfaceTemplate(String packagePath, String className) {
        super(INTERFACE_TEMPLATE_PATH);
        initImports();
        initAnnotations();
        this.packagePath = packagePath;
        this.className = className;
    }

    /**
     * 获取当前接口的包路径。
     *
     * @return 当前接口的包路径
     */
    public String getPackagePath() {
        return packagePath;
    }

    /**
     * 设置当前接口的包路径。
     *
     * @param packagePath 包路径
     */
    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    /**
     * 获取当前接口的完整包路径（包括接口名）。
     *
     * @return 完整的包路径
     */
    public String getAllPackagePath() {
        return packagePath + "." + className;
    }

    /**
     * 获取当前接口的接口名。
     *
     * @return 接口名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 获取当前接口所需导入的类名集合。
     *
     * @return 导入的类名集合
     */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * 添加多个导入的类。
     *
     * @param importClazz 需要导入的类集合
     */
    public void addImportClazz(List<Class<?>> importClazz) {
        this.imports.addAll(importClazz.stream()
                .filter(clazz -> ImportUtil.needsImport(clazz, packagePath))
                .map(Class::getName)
                .collect(Collectors.toSet()));
    }

    /**
     * 添加单个导入类。
     *
     * @param clazz 需要导入的类
     */
    public void addImportClazz(Class<?> clazz) {
        if (ImportUtil.needsImport(clazz, packagePath)) {
            this.imports.add(clazz.getName());
        }
    }

    /**
     * 添加多个类路径。
     *
     * @param importPath 类路径集合
     */
    public void addImportPaths(List<String> importPath) {
        this.imports.addAll(importPath);
    }

    /**
     * 添加单个类路径。
     *
     * @param path 类路径
     */
    public void addImportPath(String path) {
        this.imports.add(path);
    }

    /**
     * 获取接口的父类。
     *
     * @return 接口的父类
     */
    public String getFatherClazz() {
        return fatherClazz;
    }

    /**
     * 设置接口的父类。
     *
     * @param fatherClazz 父类类型
     */
    public void setFatherClazz(Class<?> fatherClazz) {
        addImportClazz(fatherClazz);
        this.fatherClazz = fatherClazz.getSimpleName();
    }

    /**
     * 设置接口的父类，并指定类路径。
     *
     * @param fatherClazz 父类名称
     * @param clazzPath 类路径
     */
    public void setFatherClazz(String fatherClazz, String clazzPath) {
        addImportPath(clazzPath);
        this.fatherClazz = fatherClazz;
    }

    /**
     * 添加接口的注解集合。
     *
     * @param annotations 接口注解集合
     */
    public void addAnnotations(List<MetaAnnotation> annotations) {
        addImportAndCheck(annotations);
        this.annotations.addAll(annotations);
    }

    /**
     * 添加单个接口注解。
     *
     * @param annotation 接口注解
     */
    public void addAnnotation(MetaAnnotation annotation) {
        addImportAndCheck(annotation);
        this.annotations.add(annotation);
    }

    /**
     * 添加接口的方法集合。
     *
     * @param metaMethods 方法集合
     */
    public void addMethods(List<MetaMethod> metaMethods) {
        this.methods.addAll(metaMethods);
    }

    /**
     * 添加单个接口方法。
     *
     * @param metaMethod 接口方法
     */
    public void addMethod(MetaMethod metaMethod) {
        this.methods.add(metaMethod);
    }

    /**
     * 添加接口方法并指定方法参数。
     *
     * @param methodName 方法名
     * @param params 方法参数集合
     */
    public void addMethod(String methodName, List<MetaMethodParam> params) {
        MetaMethod metaMethod = new MetaMethod(methodName, params, "");
        addMethod(metaMethod);
    }

    /**
     * 获取接口模板的上下文数据。
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
        context.put("annotations", annotations);
        context.put("methods", methods);
        return context;
    }

    /**
     * 添加导入类的检查方法。
     *
     * @param importChecker 导入类检查器
     */
    public void addImportAndCheck(ImportChecker importChecker) {
        importChecker.getImports().forEach(importClazz -> {
            if (ImportUtil.needsImport(importClazz, this.packagePath)) {
                this.imports.add(importClazz);
            }
        });
    }

    /**
     * 添加多个导入类的检查方法。
     *
     * @param importCheckers 导入类检查器列表
     */
    public void addImportAndCheck(List<? extends ImportChecker> importCheckers) {
        for (ImportChecker importChecker : importCheckers) {
            addImportAndCheck(importChecker);
        }
    }

    /**
     * 初始化接口的导入类集合。子类可以重写此方法以进行自定义初始化。
     */
    public void initImports() {
    }

    /**
     * 初始化接口的注解集合。子类可以重写此方法以进行自定义初始化。
     */
    public void initAnnotations() {
    }

    /**
     * 重写 `equals` 方法，判断两个接口模板是否相等。
     *
     * @param o 比较对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterfaceTemplate that = (InterfaceTemplate) o;
        return getAllPackagePath().equals(that.getAllPackagePath());
    }

    /**
     * 重写 `hashCode` 方法，计算接口模板的哈希值。
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(getAllPackagePath());
    }
}
