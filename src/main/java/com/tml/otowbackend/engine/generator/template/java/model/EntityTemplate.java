package com.tml.otowbackend.engine.generator.template.java.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.otowbackend.engine.generator.template.java.ClassTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.utils.MetalUtils;

import static com.tml.otowbackend.constants.TemplateConstant.ENTITY_ANNOTATION;

/**
 * 数据库实体类模板
 */
public class EntityTemplate extends ClassTemplate {

    // 数据库表名
    protected String tableName;

    public EntityTemplate(String packagePath, String className, String tableName, String describe) {
        super(packagePath, getEntityClassName(className));
        this.tableName = tableName;
        addAnnotation(MetalUtils.getDescribe(describe));
        addAnnotation(new MetaAnnotation(TableName.class, tableName));
    }

    public EntityTemplate(String packagePath, String className) {
        super(packagePath, getEntityClassName(className));
        addAnnotation(new MetaAnnotation(TableName.class, tableName));
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public static String getEntityClassName(String className){
        return className+"Entity";
    }

    @Override
    public void initAnnotations(){
        addAnnotations(MetaAnnotation.convertByClazz(ENTITY_ANNOTATION));
    }
}
