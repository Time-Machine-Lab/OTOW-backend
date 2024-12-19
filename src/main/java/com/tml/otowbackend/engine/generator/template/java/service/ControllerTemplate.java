package com.tml.otowbackend.engine.generator.template.java.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import lombok.Getter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tml.otowbackend.constants.TemplateConstant.CONTROLLER_ANNOTATION;

public class ControllerTemplate extends ClassTemplate {

    @Getter
    private String urlPath;

    private Set<ServiceTemplate> services = new HashSet<>();

    private List<MetaMethod> apiMethod = new ArrayList<>();

    public ControllerTemplate(String packagePath, String className, String urlPath) {
        super(packagePath, getControllerClassName(className));
        this.urlPath = urlPath;
        addAnnotation(new MetaAnnotation(RequestMapping.class, urlPath));
    }

    public static String getControllerClassName(String className) {
        return className + "Controller";
    }

    public void setCommonResult(String commonResult, Class<?> clazz) {
        addImportClazz(clazz);
    }

    public void addService(ServiceTemplate serviceTemplate) {
        this.services.add(serviceTemplate);
        String serviceClass = serviceTemplate.getClassName();
        String servicePackage = serviceTemplate.getAllPackagePath();
        String serviceName = StringUtils.firstToLowerCase(serviceClass);
        MetalField service = new MetalField(serviceName, serviceClass);
        service.addAnnotations(List.of(new MetaAnnotation(Resource.class)));
        this.addModelField(service);
        this.addImportPath(servicePackage);
    }

    public void addPostMethod(MetaMethod method, String path) {
        method.addAnnotations(List.of(new MetaAnnotation(PostMapping.class, path)));
        this.addMethod(method);
    }

    public void addGetMethod(MetaMethod method, String path) {
        method.addAnnotations(List.of(new MetaAnnotation(GetMapping.class, path)));
        this.addMethod(method);
    }

    public void addDeleteMethod(MetaMethod method, String path) {
        method.addAnnotations(List.of(new MetaAnnotation(DeleteMapping.class, path)));
        this.addMethod(method);
    }

    public void addMethod(MetaMethod method) {
        this.apiMethod.add(method);
        super.addMethod(method);
    }

    @Override
    public void initAnnotations() {
        addAnnotations(MetaAnnotation.convertByClazz(CONTROLLER_ANNOTATION));
    }
}
