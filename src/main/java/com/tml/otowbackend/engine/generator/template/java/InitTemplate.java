package com.tml.otowbackend.engine.generator.template.java;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.EntityTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.MapperTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.tml.otowbackend.engine.generator.template.meta.MetalUtils.getDescribe;

@NoArgsConstructor
public class InitTemplate {

    public static final String entityPackagePath = "io.github.geniusay.velocity.generate";
    public static final String servicePackagePath = "io.github.geniusay.velocity.generate.service";
    public static final String reqPackagePath = "io.github.geniusay.velocity.generate.pojo.req";
    public static final String mapperPackagePath = "io.github.geniusay.velocity.generate.mapper";
    public static final String serviceImplPackagePath = "io.github.geniusay.velocity.generate.service.impl";

    public static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    public FuncPackManager funcPackManager;
    public String className;
    public String tableName;
    public String describe;
    public LinkedList<MetalField> fields;
    public List<String> featureIds;

    private ServiceTemplate serviceTemplate;
    private ControllerTemplate controllerTemplate;
    private ServiceImplTemplate serviceImplTemplate;
    private MapperTemplate mapperTemplate;
    private EntityTemplate entityTemplate;

    public InitTemplate(FuncPackManager funcPackManager, String className, String tableName, String describe, LinkedList<MetalField> fields, List<String> featureIds) {
        this.funcPackManager = funcPackManager;
        this.className = className;
        this.tableName = tableName;
        this.describe = describe;
        this.fields = fields;
        this.featureIds = featureIds;
    }

    public void initTemplate() {
        judgeMetalField();
        entityTemplate = getEntityTemplate();
        mapperTemplate = getMapperTemplate();
        serviceTemplate = getServiceTemplate();
        serviceImplTemplate = getServiceImplTemplate();
        controllerTemplate = getControllerTemplate();
    }

    private String generateStringWithTemplate(VelocityOTOWTemplate template) {
        return engine.generate(template);
    }

    public String generateEntity() {
        return generateStringWithTemplate(entityTemplate);
    }

    public String generateMapper() {
        return generateStringWithTemplate(mapperTemplate);
    }

    public String generateService() {
        return generateStringWithTemplate(serviceTemplate);
    }

    public String generateServiceImpl() {
        return generateStringWithTemplate(serviceImplTemplate);
    }

    public String generateController() {
        return generateStringWithTemplate(controllerTemplate);
    }

    // 根据给定的实体类名、字段生成实体模板
    private EntityTemplate getEntityTemplate() {
        EntityTemplate entityTemplate = new EntityTemplate(entityPackagePath, className, tableName, describe);
        entityTemplate.addModelFields(fields);
        return entityTemplate;
    }

    private MapperTemplate getMapperTemplate() {
        return new MapperTemplate(mapperPackagePath, className, entityTemplate);
    }

    private ServiceTemplate getServiceTemplate() {
        ServiceTemplate userService = new ServiceTemplate(servicePackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam(className, userService.getAllPackagePath(), className.toLowerCase());
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("metaMethodParam", metaMethodParam);
            pack.generateService(userService);
        }
        return userService;
    }

    private ServiceImplTemplate getServiceImplTemplate() {
        ServiceImplTemplate serviceImplTemplate = new ServiceImplTemplate(serviceImplPackagePath, className, serviceTemplate);
        serviceImplTemplate.addMapper(mapperTemplate);
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
            pack.generateServiceImpl(serviceImplTemplate);
        }
        return serviceImplTemplate;
    }

    private ControllerTemplate getControllerTemplate() {
        ControllerTemplate userController = new ControllerTemplate(entityPackagePath, className, "/" + className.toLowerCase());
        userController.addService(serviceTemplate);
        String classLower = serviceTemplate.getClassName().toLowerCase();
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
            pack.addParams("classLower", classLower);
            pack.generateController(userController);
        }
        return userController;
    }

    // 加强判断
    private void judgeMetalField() {

        // 判断id 加入时间
        MetaAnnotation tableId = new MetaAnnotation(TableId.class);
        for (MetalField metalField : fields) {
            // 判断是否是 id 字段，若是则添加 @TableId 注解 和 类型
            if (metalField.getName().equals("id")) {
                metalField.addAnnotation(tableId);  // 添加 TableId 注解
                metalField.setClazz("String");
            }
            // 判断是否有类型 ，没有就添加为String
            if (StringUtils.isBlank(metalField.getClazz())) {
                metalField.setClazz("String");
            }
        }

        // 添加 createTime、updateTime、逻辑删除字段（假设为 "deleted"）
        MetaAnnotation createTimeFill = new MetaAnnotation(TableField.class, "fill", "FieldFill.INSERT", FieldFill.class);
        MetaAnnotation updateTimeFill = new MetaAnnotation(TableField.class, "fill", "FieldFill.INSERT_UPDATE", FieldFill.class);

        MetalField createTime = new MetalField("createTime", Date.class, createTimeFill);
        createTime.addAnnotation(getDescribe("创建时间"));
        MetalField updateTime = new MetalField("updateTime", Date.class, updateTimeFill);
        updateTime.addAnnotation(getDescribe("更新时间"));
        MetalField deleted = new MetalField("deleteFlag", Boolean.class);
        deleted.addAnnotation(getDescribe("删除标记"));

        fields.addLast(deleted);
        fields.addLast(createTime);
        fields.addLast(updateTime);
    }
}