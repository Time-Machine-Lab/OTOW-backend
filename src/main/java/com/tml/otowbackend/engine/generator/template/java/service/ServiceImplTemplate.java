package com.tml.otowbackend.engine.generator.template.java.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceImplTemplate extends ClassTemplate {

    private ServiceTemplate serviceTemplate;
    private Set<MapperTemplate> mappers = new HashSet<>();
    private List<MetaMethod> methods = new ArrayList<>();


    public ServiceImplTemplate(String packagePath, String className) {
        super(packagePath, className+"ServiceImpl");
    }

    public ServiceImplTemplate(String packagePath, String className, ServiceTemplate serviceTemplate) {
        super(packagePath, className+"ServiceImpl");
        setServiceTemplate(serviceTemplate);
    }

    public void setServiceTemplate(ServiceTemplate serviceTemplate){
        this.serviceTemplate = serviceTemplate;
        this.addInterfaces(serviceTemplate.getClassName(), serviceTemplate.getAllPackagePath());
    }

    public void addMapper(MapperTemplate mapperTemplate){
        this.mappers.add(mapperTemplate);
        String mapperClass = mapperTemplate.getClassName();
        String mapperPackage = mapperTemplate.getAllPackagePath();
        String mapperName = StringUtils.firstToLowerCase(mapperClass);
        MetalField mapper = new MetalField(mapperName, mapperClass);
        mapper.addAnnotations(List.of(new MetaAnnotation(Resource.class)));
        this.addModelField(mapper);
        this.addImportPath(mapperPackage);
    }

    public void addMethod(MetaMethod method){
        this.methods.add(method);
        super.addMethod(method);
    }

    @Override
    public void initAnnotations() {
        addAnnotation(new MetaAnnotation(Service.class));
    }
}
