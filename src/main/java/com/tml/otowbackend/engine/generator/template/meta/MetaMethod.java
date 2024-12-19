package com.tml.otowbackend.engine.generator.template.meta;

import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaMethod 类表示一个方法的元数据。
 *
 * <p>该类封装了方法的名称、返回类型、参数、方法体、注解以及静态标志等信息，适用于动态生成代码时对方法的描述。</p>
 * <p>支持方法体为字符串渲染的特殊处理，并允许添加多个注解和参数。</p>
 *
 * @see MetaMethodParam
 * @see MetaAnnotation
 */
@Data
public class MetaMethod extends ImportChecker {

    /**
     * 方法的返回类型。
     */
    private String returnRes;

    /**
     * 方法的名称。
     */
    private String methodName;

    /**
     * 方法的参数列表。
     */
    private List<MetaMethodParam> methodParams = new ArrayList<>();

    /**
     * 方法体内容，通常为方法的实现代码。
     */
    private String methodBody;

    /**
     * 方法的注解列表。
     */
    private List<MetaAnnotation> annotations = new ArrayList<>();

    /**
     * 标记方法是否为静态方法。
     */
    private boolean staticFlag = false;

    /**
     * 是否仅仅使用方法体的字符串进行渲染，而不考虑其他内容。
     * 适用于方法体直接作为模板渲染时的情况。
     */
    private boolean justString = false;

    /**
     * 构造一个 `MetaMethod` 对象，设置方法名称、返回类型、参数、方法体和注解。
     *
     * @param methodName  方法名称
     * @param returnRes   返回类型
     * @param params      方法参数
     * @param methodBody  方法体
     * @param annotations 方法的注解
     */
    public MetaMethod(String methodName, Class<?> returnRes, List<MetaMethodParam> params, String methodBody, List<MetaAnnotation> annotations) {
        this.methodName = methodName;
        this.returnRes = returnRes.getSimpleName();
        this.methodBody = methodBody;
        addAnnotations(annotations);
        addParams(params);
    }

    /**
     * 构造一个 `MetaMethod` 对象，设置方法名称、返回类型、参数和方法体。
     *
     * @param methodName 方法名称
     * @param returnRes  返回类型
     * @param params     方法参数
     * @param methodBody 方法体
     */
    public MetaMethod(String methodName, Class<?> returnRes, List<MetaMethodParam> params, String methodBody) {
        this.methodName = methodName;
        this.returnRes = returnRes.getSimpleName();
        this.methodBody = methodBody;
        addParams(params);
    }

    /**
     * 构造一个 `MetaMethod` 对象，设置方法名称、参数和方法体。
     * 默认返回类型为 `void`。
     *
     * @param methodName 方法名称
     * @param params     方法参数
     * @param methodBody 方法体
     */
    public MetaMethod(String methodName, List<MetaMethodParam> params, String methodBody) {
        this.methodName = methodName;
        this.returnRes = "void";
        this.methodBody = methodBody;
        addParams(params);
    }

    public MetaMethod(String methodName, String methodBody) {
        this.methodName = methodName;
        this.returnRes = "void";
        this.methodBody = methodBody;
    }

    public MetaMethod(String methodName, List<MetaMethodParam> params) {
        this.methodName = methodName;
        this.returnRes = "void";
        addParams(params);
    }

    /**
     * 构造一个 `MetaMethod` 对象，仅设置方法体。
     * 适用于直接渲染方法体时使用。
     *
     * @param methodBody 方法体
     */
    public MetaMethod(String methodBody) {
        this.methodBody = methodBody;
        this.justString = true;
    }

    /**
     * 添加方法注解列表。
     *
     * @param annotations 注解列表
     */
    public void addAnnotations(List<MetaAnnotation> annotations) {
        addImportCheckers(annotations);
        this.annotations.addAll(annotations);
    }

    public void addAnnotation(MetaAnnotation annotation) {
        addImportCheckers(annotation);
        this.annotations.add(annotation);
    }

    /**
     * 添加方法参数列表。
     *
     * @param params 方法参数列表
     */
    protected void addParams(List<MetaMethodParam> params) {
        addImportCheckers(params);
        this.methodParams.addAll(params);
    }

    /**
     * 设置方法是否为静态方法。
     *
     * @param isStatic 如果为 `true`，表示方法为静态方法；否则为实例方法。
     */
    public void setIsStatic(boolean isStatic) {
        this.staticFlag = isStatic;
    }

    /**
     * 创建一个仅包含方法体的 `MetaMethod` 对象。
     * 适用于只渲染方法体时的特殊场景。
     *
     * @param methodBody 方法体内容
     * @return `MetaMethod` 对象
     */
    public static MetaMethod justStringReinder(String methodBody) {
        return new MetaMethod(methodBody);
    }

    public void setReturnRes(ClassTemplate classTemplate) {
        addImportPath(classTemplate.getAllPackagePath());
        this.returnRes = classTemplate.getClassName();
    }

    public void setReturnRes(Class<?> clazz) {
        addImportClazz(clazz);
        this.returnRes = clazz.getSimpleName();
    }
}
