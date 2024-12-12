package com.tml.otowbackend.core.generator.template.java.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.tml.otowbackend.core.generator.template.java.InterfaceTemplate;
import com.tml.otowbackend.core.generator.template.java.model.EntityTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;

public class MapperTemplate extends InterfaceTemplate {

    private EntityTemplate entityTemplate;

    public MapperTemplate(String packagePath, String className) {
        super(packagePath, className+"Mapper");
    }

    public MapperTemplate(String packagePath, String className, EntityTemplate entityTemplate) {
        super(packagePath, className+"Mapper");
        setEntityTemplate(entityTemplate);
    }

    public void setEntityTemplate(EntityTemplate entityTemplate) {
        this.entityTemplate = entityTemplate;
        String serviceClass = entityTemplate.getClassName();
        String servicePackage = entityTemplate.getAllPackagePath();
        this.fatherClazz = String.format("BaseMapper<%s>", serviceClass);
        this.addImportClazz(BaseMapper.class);
        this.addImportPath(servicePackage);
        this.addAnnotation(new MetaAnnotation(Mapper.class));
    }
}
