package com.tml.otowbackend.engine.generator.template.java;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.funpack.AbstrateFunctionPack;
import com.tml.otowbackend.engine.generator.funpack.AddFunctionPack;
import com.tml.otowbackend.engine.generator.funpack.FuncPackManager;
import com.tml.otowbackend.engine.generator.funpack.FunctionPack;
import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
import com.tml.otowbackend.engine.generator.template.VelocityOTOWTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.AddServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.DeleteServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.SelectServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.method.UpdateServiceMethodTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.EntityTemplate;
import com.tml.otowbackend.engine.generator.template.java.model.ReqTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ControllerTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.MapperTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceImplTemplate;
import com.tml.otowbackend.engine.generator.template.java.service.ServiceTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaAnnotation;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethodParam;
import com.tml.otowbackend.engine.generator.template.meta.MetalField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.Path_Variable;
import static com.tml.otowbackend.constants.TemplateConstant.REQUEST_BODY;

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
    public List<MetalField> fields;
    public List<String> featureIds;

    private ServiceTemplate serviceTemplate;
    private ControllerTemplate controllerTemplate;
    private ServiceImplTemplate serviceImplTemplate;
    private MapperTemplate mapperTemplate;
    private EntityTemplate entityTemplate;

    public InitTemplate(FuncPackManager funcPackManager, String className, String tableName, List<MetalField> fields, List<String> featureIds) {
        this.funcPackManager = funcPackManager;
        this.className = className;
        this.tableName = tableName;
        this.fields = fields;
        this.featureIds = featureIds;
    }

    public void initTemplate() {
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

    // 加强判断
    private static List<MetalField> judgeMetalField(List<MetalField> fields){
        MetaAnnotation fillAnnotation = new MetaAnnotation(TableField.class, "fill", "FieldFill.INSERT_UPDATE", FieldFill.class);
        MetaAnnotation tableId = new MetaAnnotation(TableId.class);
        // 判断id 加入时间 逻辑删除
        for (MetalField metalField : fields) {
            // 判断是否是 id 字段，若是则添加 @TableId 注解 和 类型
            if (metalField.getName().equals("id")) {
                metalField.addAnnotations(java.util.List.of(tableId));  // 添加 TableId 注解
                metalField.setClazz("String");
            }
            // 判断是否有 类型 ，没有就添加为String
            if (StringUtils.isBlank(metalField.getClazz())){
                metalField.setClazz("String");
            }
        }
        // 添加 createTime、updateTime、逻辑删除字段（假设为 "deleted"）
        MetalField createTime = new MetalField("createTime", Date.class, fillAnnotation);
        MetalField updateTime = new MetalField("updateTime", Date.class, fillAnnotation);
        MetalField deleted = new MetalField("deleted",String.class);

        fields.add(createTime);
        fields.add(updateTime);
        fields.add(deleted);

        return fields;
    }

    // 根据给定的实体类名、字段生成实体模板
    private EntityTemplate getEntityTemplate() {
        EntityTemplate entityTemplate = new EntityTemplate(entityPackagePath, className, tableName);

        fields = judgeMetalField(fields);
        entityTemplate.addModelFields(fields);
        return entityTemplate;
    }

    // 生成 ServiceTemplate
    private ServiceTemplate getServiceTemplate() {
        ServiceTemplate userService = new ServiceTemplate(servicePackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam(className, userService.getAllPackagePath(), className.toLowerCase());

        // 添加功能包
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("metaMethodParam", metaMethodParam);
            pack.generateService(userService);
        }

        return userService;
    }

    // 根据选择生成，返回
    private ControllerTemplate getControllerTemplate(){
        ControllerTemplate userController = new ControllerTemplate(entityPackagePath, className, "/"+className.toLowerCase());
        userController.addService(serviceTemplate);
        String classLower = serviceTemplate.getClassName().toLowerCase();

        // 功能包
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
            pack.addParams("classLower", classLower);
            pack.generateController(userController);
        }
        return userController;
    }


    private MapperTemplate getMapperTemplate(){
        MapperTemplate mapperTemplate = new MapperTemplate(mapperPackagePath,className, entityTemplate);
        return mapperTemplate;
    }

    private ServiceImplTemplate getServiceImplTemplate(){
        ServiceImplTemplate serviceImplTemplate = new ServiceImplTemplate(serviceImplPackagePath, className, serviceTemplate);
        serviceImplTemplate.addMapper(mapperTemplate);

        // 添加功能包
        for (String featureId : featureIds) {
            AbstrateFunctionPack pack = funcPackManager.getFunctionPackById(featureId);
            pack.addParams("className", className);
            pack.generateServiceImpl(serviceImplTemplate);
        }
        return serviceImplTemplate;
    }
}