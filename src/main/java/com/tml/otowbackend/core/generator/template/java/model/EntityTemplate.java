package com.tml.otowbackend.core.generator.template.java.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.otowbackend.core.generator.template.java.ClassTemplate;
import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;

import static com.tml.otowbackend.constants.TemplateConstant.ENTITY_ANNOTATION;

/**
 * 数据库实体类模板
 */
public class EntityTemplate extends ClassTemplate {

    // 数据库表名
    protected String tableName;

    public EntityTemplate(String packagePath, String className, String tableName) {
        super(packagePath, getEntityClassName(className));
        this.tableName = tableName;
        addAnnotation(new MetaAnnotation(TableName.class, tableName));
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public static String getEntityClassName(String className){
        return className+"Entity";
    }

    @Override
    public void initImports(){
        addImportPath("com.baomidou.mybatisplus.annotation.*");
    }

    @Override
    public void initAnnotations(){
        addAnnotations(MetaAnnotation.convertByClazz(ENTITY_ANNOTATION));
    }
}
