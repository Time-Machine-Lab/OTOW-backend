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
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.VOTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.MapperTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.tml.otowbackend.engine.generator.utils.MetalUtils.getDescribe;

@NoArgsConstructor
public class InitTemplate {

    public static String entityPackagePath = "model.entity";
    public static String controllerPackagePath = "controller";
    public static String servicePackagePath = "service";
    public static String reqPackagePath = "model.req";
    public static String voPackagePath = "model.vo";
    public static String mapperPackagePath = "mapper";
    public static String serviceImplPackagePath = "service.impl";

    public static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();


    public String prefix;
    public FuncPackManager funcPackManager;
    public String className;
    public String tableName;
    public String describe;
    public LinkedList<MetalField> fields = new LinkedList<>();
    public LinkedList<MetalField> fieldsVO = new LinkedList<>();
    public LinkedList<MetalField> fieldsReq = new LinkedList<>();
    public List<String> featureIds;

    private ServiceTemplate serviceTemplate;
    private ControllerTemplate controllerTemplate;
    private ServiceImplTemplate serviceImplTemplate;
    private MapperTemplate mapperTemplate;
    private EntityTemplate entityTemplate;
    private ReqTemplate entityReqTemplate;
    private VOTemplate entityVOTemplate;

    public InitTemplate(String prefix, FuncPackManager funcPackManager, String className, String tableName, String describe, LinkedList<MetalField> fields, List<String> featureIds) {
        this.prefix = prefix;
        this.entityPackagePath = prefix + entityPackagePath;
        this.servicePackagePath = prefix + servicePackagePath;
        this.reqPackagePath = prefix + reqPackagePath;
        this.voPackagePath = prefix + voPackagePath;
        this.mapperPackagePath = prefix + mapperPackagePath;
        this.serviceImplPackagePath = prefix + serviceImplPackagePath;
        this.controllerPackagePath = prefix + controllerPackagePath;
        this.funcPackManager = funcPackManager;
        this.className = className;
        this.tableName = tableName;
        this.describe = describe;
        this.fields = fields;
        this.featureIds = featureIds;
    }

    public void initTemplate() {
        //  将属性添加到新集合中 给req、VO使用
        for (MetalField field : fields) {
            MetalField clone = field.getClone();
            fieldsReq.add(clone);
            fieldsVO.add(clone);
        }
        judgeMetalField();
        entityTemplate = getEntityTemplate();
        entityReqTemplate = getEntityReqTemplate();
        entityVOTemplate = getEntityVOTemplate();
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
    public String generateEntityReq(){
        return generateStringWithTemplate(entityReqTemplate);
    }
    public String generateEntityVO(){
        return generateStringWithTemplate(entityVOTemplate);
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

    // 根据给定的实体类名、字段生成实体Req模板
    private ReqTemplate getEntityReqTemplate() {
        ReqTemplate reqTemplate = new ReqTemplate(reqPackagePath,className);
        reqTemplate.addModelFields(fieldsReq);
        return reqTemplate;
    }

    // 根据给定的实体类名、字段生成实体VO模板
    private VOTemplate getEntityVOTemplate() {
        VOTemplate voTemplate = new VOTemplate(voPackagePath,className);
        voTemplate.addModelFields(fieldsVO);
        return voTemplate;
    }

    private MapperTemplate getMapperTemplate() {
        return new MapperTemplate(mapperPackagePath, className, entityTemplate);
    }

    private ServiceTemplate getServiceTemplate() {
        ServiceTemplate userService = new ServiceTemplate(servicePackagePath, className);
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
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
        ControllerTemplate userController = new ControllerTemplate(controllerPackagePath, className, "/" + className.toLowerCase());
        userController.addService(serviceTemplate);
        String classLower = StringUtils.firstToLowerCase(serviceTemplate.getClassName());
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