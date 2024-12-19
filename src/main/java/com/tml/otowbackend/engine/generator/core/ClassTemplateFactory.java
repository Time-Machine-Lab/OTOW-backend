package com.tml.otowbackend.engine.generator.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
public class ClassTemplateFactory {

    public static String entityPackagePath = "model.entity";
    public static String controllerPackagePath = "controller";
    public static String mapperPackagePath = "mapper";
    public static String serviceImplPackagePath = "service.impl";
    public static String servicePackagePath = "service";
    public static String reqPackagePath = "model.req";
    public static String voPackagePath = "model.vo";
    public static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    public String prefix;
    public FunctionPackManager functionPackManager;
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

    public ClassTemplateFactory(String prefix, FunctionPackManager functionPackManager, String className, String tableName, String describe, LinkedList<MetalField> fields, List<String> featureIds) {
        this.prefix = prefix + ".";
        this.functionPackManager = functionPackManager;
        this.className = className;
        this.tableName = tableName;
        this.describe = describe;
        this.fields = fields;
        this.featureIds = featureIds;
    }

    public void initTemplate() {
        for (MetalField field : fields) {
            MetalField clone = field.getClone();
            fieldsReq.add(clone);
            fieldsVO.add(clone);
        }
        enhanceMetalField(fields);
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

    public String generateEntityReq() {
        return generateStringWithTemplate(entityReqTemplate);
    }

    public String generateEntityVO() {
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

    private EntityTemplate getEntityTemplate() {
        EntityTemplate entityTemplate = new EntityTemplate(prefix + entityPackagePath, className, tableName, describe);
        entityTemplate.addModelFields(fields);
        return entityTemplate;
    }

    private ReqTemplate getEntityReqTemplate() {
        ReqTemplate reqTemplate = new ReqTemplate(prefix + reqPackagePath, className);
        reqTemplate.addModelFields(fieldsReq);
        return reqTemplate;
    }

    private VOTemplate getEntityVOTemplate() {
        VOTemplate voTemplate = new VOTemplate(prefix + voPackagePath, className);
        voTemplate.addModelFields(fieldsVO);
        return voTemplate;
    }

    private MapperTemplate getMapperTemplate() {
        return new MapperTemplate(prefix + mapperPackagePath, className, entityTemplate);
    }

    private ServiceTemplate getServiceTemplate() {
        ServiceTemplate userService = new ServiceTemplate(prefix + servicePackagePath, className);
        featureIds.forEach(featureId -> {
            AbstrateFunctionPack pack = functionPackManager.getFunctionPackById(featureId);
            pack.addParams("prefix", prefix);
            pack.addParams("className", className);
            pack.handleService(userService);
        });
        return userService;
    }

    private ServiceImplTemplate getServiceImplTemplate() {
        ServiceImplTemplate serviceImplTemplate = new ServiceImplTemplate(prefix + serviceImplPackagePath, className, serviceTemplate);
        serviceImplTemplate.addMapper(mapperTemplate);
        featureIds.forEach(featureId -> {
            AbstrateFunctionPack pack = functionPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
            pack.addParams("prefix", prefix);
            pack.handleServiceImpl(serviceImplTemplate);
        });
        return serviceImplTemplate;
    }

    private ControllerTemplate getControllerTemplate() {
        ControllerTemplate userController = new ControllerTemplate(prefix + controllerPackagePath, className, "/" + StringUtils.firstToLowerCase(className));
        userController.addService(serviceTemplate);
        String serviceName = StringUtils.firstToLowerCase(serviceTemplate.getClassName());
        featureIds.forEach(featureId -> {
            AbstrateFunctionPack pack = functionPackManager.getFunctionPackById(featureId);
            pack.addParams("prefix", prefix);
            pack.addParams("className", className);
            pack.addParams("serviceName", serviceName);
            pack.handleController(userController);
        });
        return userController;
    }

    // 加强判断
    private void enhanceMetalField(LinkedList<MetalField> fields) {
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