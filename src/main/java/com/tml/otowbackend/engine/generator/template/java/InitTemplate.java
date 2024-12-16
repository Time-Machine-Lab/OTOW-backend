package com.tml.otowbackend.engine.generator.template.java;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.generator.engine.VelocityCodeEngine;
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

import java.util.Date;
import java.util.List;

import static com.tml.otowbackend.constants.TemplateConstant.Path_Variable;
import static com.tml.otowbackend.constants.TemplateConstant.REQUEST_BODY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitTemplate {

    private static final String entityPackagePath = "io.github.geniusay.velocity.generate";
    private static final String servicePackagePath = "io.github.geniusay.velocity.generate.service";
    private static final String reqPackagePath = "io.github.geniusay.velocity.generate.pojo.req";
    private static final String mapperPackagePath = "io.github.geniusay.velocity.generate.mapper";
    private static final String serviceImplPackagePath = "io.github.geniusay.velocity.generate.service.impl";

    private static final String deleteTemplateFilePath = "service.method.delete.java.vm";
    private static final String saveTemplateFilePath = "service.method.save.java.vm";
    private static final String selectTemplateFilePath = "service.method.select.java.vm";
    private static final String updateTemplateFilePath = "service.method.update.java.vm";

    private static final String addServiceMethod = "insert";
    private static final String updateServiceMethod = "update";
    private static final String deleteServiceMethod = "delete";
    private static final String selectServiceMethod = "select";

    private static VelocityCodeEngine engine = VelocityCodeEngine.getCodeEngine();

    private String className;
    private String tableName;
    private List<MetalField> fields;
    private Boolean add;
    private Boolean update;
    private Boolean delete;
    private Boolean select;


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
    private  EntityTemplate getEntityTemplate() {
        EntityTemplate entityTemplate = new EntityTemplate(entityPackagePath, className, tableName);

        fields = judgeMetalField(fields);
        entityTemplate.addModelFields(fields);
        return entityTemplate;
    }

    // 生成实体类模板代码,返回
    public String getGenerateEntityTemplate() {
        EntityTemplate entityTemplate = getEntityTemplate();
        return engine.generate(entityTemplate);
    }

    // 生成 ServiceTemplate
    private ServiceTemplate getServiceTemplate() {
        ServiceTemplate userService = new ServiceTemplate(servicePackagePath, className);
//        EntityTemplate entityTemplate = getEntityTemplate();
//        String className = entityTemplate.getClassName();

        MetaMethodParam metaMethodParam = new MetaMethodParam(className, userService.getAllPackagePath(), className.toLowerCase());

        if (add){
            System.out.println("add============"+metaMethodParam);
            userService.addMethod(addServiceMethod,List.of(metaMethodParam));
        }
        if (update){
            System.out.println("update============"+metaMethodParam);
//            userService.addMethod(updateServiceMethod,List.of(metaMethodParam));
            userService.addMethod(updateServiceMethod,List.of(metaMethodParam));
        }
        if (delete){
            metaMethodParam = new MetaMethodParam("Integer", userService.getAllPackagePath(), "id");
            System.out.println("delete============"+metaMethodParam);
            userService.addMethod(deleteServiceMethod,List.of(metaMethodParam));
        }
        if (select){
            metaMethodParam = new MetaMethodParam("Integer", userService.getAllPackagePath(), "id");
            System.out.println("select============"+metaMethodParam);
            userService.addMethod(selectServiceMethod,List.of(metaMethodParam));
        }

        return userService;
    }

    // 生成 ServiceTemplate， 返回
    public String getGenerateService(){
        ServiceTemplate userService = getServiceTemplate();
        String generateService = engine.generate(userService);
        return generateService;
    }

    // controller-post 的 添加
    private MetaMethod getAddMethod(){
        ServiceTemplate serviceTemplate = getServiceTemplate();
        ReqTemplate reqUser = new ReqTemplate(reqPackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), reqUser.getClassName().toLowerCase());
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));

        String body = String.format("%s.%s(%s);", serviceTemplate.getClassName().toLowerCase(), addServiceMethod, metaMethodParam.getName());
        return new MetaMethod(addServiceMethod, List.of(metaMethodParam), body);
    }

    // controller-get 的 查询
    private MetaMethod getGetMethod(){
        ServiceTemplate serviceTemplate = getServiceTemplate();
        ReqTemplate reqUser = new ReqTemplate(reqPackagePath, "id");
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer",reqUser.getAllPackagePath(), "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));

        String body = String.format("%s.%s(%s);", serviceTemplate.getClassName().toLowerCase(), selectServiceMethod, "id");
        return new MetaMethod(selectServiceMethod, List.of(metaMethodParam), body);
    }

    // controller-delete 的 删除
    private MetaMethod getDeleteMethod(){
        ServiceTemplate serviceTemplate = getServiceTemplate();
        ReqTemplate reqUser = new ReqTemplate(reqPackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam("Integer",reqUser.getAllPackagePath(), "id");
        metaMethodParam.addAnnotations(List.of(Path_Variable));

        String body = String.format("%s.%s(%s);", serviceTemplate.getClassName().toLowerCase(), deleteServiceMethod, "id");
        return new MetaMethod(deleteServiceMethod, List.of(metaMethodParam), body);
    }

    // controller-post 的 更新
    private MetaMethod getUpdateMethod(){
        ServiceTemplate serviceTemplate = getServiceTemplate();
        ReqTemplate reqUser = new ReqTemplate(reqPackagePath, className);
        MetaMethodParam metaMethodParam = new MetaMethodParam(reqUser.getClassName(),reqUser.getAllPackagePath(), reqUser.getClassName().toLowerCase());
        metaMethodParam.addAnnotations(List.of(REQUEST_BODY));

        String body = String.format("%s.%s(%s);", serviceTemplate.getClassName().toLowerCase(), updateServiceMethod, metaMethodParam.getName());
        return new MetaMethod(updateServiceMethod, List.of(metaMethodParam), body);
    }

    // 生成controller 判断增删改查是否需要
/*    public void generateController(){
        ControllerTemplate userController = new ControllerTemplate(entityPackagePath, className, "/"+className.toLowerCase());
        ServiceTemplate userService = getServiceTemplate();
        userController.addService(userService);
        userController.addPostMethod(getAddMethod(),"/add/user");
        userController.addPostMethod(getUpdateMethod(),"/update/user");
        userController.addDeleteMethod(getDeleteMethod(),"/delete/{id}");
        userController.addGetMethod(getGetMethod(),"/get/{id}");
        String generateController = engine.generate(userController);
        String generateService = engine.generate(userService);

        System.out.println(generateController);
//        System.out.println(generateService);
    }*/

    // 根据选择生成，返回
    public String getGenerateController(/*Boolean add,Boolean update,Boolean delete,Boolean select*/){
        ControllerTemplate userController = new ControllerTemplate(entityPackagePath, className, "/"+className.toLowerCase());
        ServiceTemplate userService = getServiceTemplate();
        userController.addService(userService);
        if (add){
            userController.addPostMethod(getAddMethod(),"/add/user");
        }
        if (update){
            userController.addPostMethod(getUpdateMethod(),"/update/user");
        }
        if (delete){
            userController.addDeleteMethod(getDeleteMethod(),"/delete/{id}");
        }
        if (select){
            userController.addGetMethod(getGetMethod(),"/get/{id}");
        }
        String generateController = engine.generate(userController);
        return generateController;
    }

    private MapperTemplate getMapperTemplate(){
        MapperTemplate mapperTemplate = new MapperTemplate(mapperPackagePath,className, getEntityTemplate());
        return mapperTemplate;
    }

    private String generateMapper(){
        MapperTemplate mapperTemplate = new MapperTemplate(mapperPackagePath,className, getEntityTemplate());

        String generate = engine.generate(mapperTemplate);
//        System.out.println(generate);
        return generate;
    }

    // 生成mapper模板 返回
    public String getGenerateMapper(){
        return generateMapper();
    }


    private String generateServiceImpl(){
        ServiceImplTemplate serviceImplTemplate = new ServiceImplTemplate(serviceImplPackagePath,className, getServiceTemplate());
        MapperTemplate mapperTemplate = getMapperTemplate();
        serviceImplTemplate.addMapper(mapperTemplate);

        if (add){
            serviceImplTemplate.addMethod(getAddService());
        }
        if (update){
            serviceImplTemplate.addMethod(getUpdateService());
        }
        if (delete){
            serviceImplTemplate.addMethod(getDeleteService());
        }
        if (select){
            serviceImplTemplate.addMethod(getSelectService());
        }

        String generate = engine.generate(serviceImplTemplate);
        System.out.println(generate);

        return generate;
    }

    // 生成ServiceImpl模板 返回
    public String getGenerateServiceImpl(){
        return generateServiceImpl();
    }

    // serviceImpl 的增加方法
    private String generateAddService(){
        AddServiceMethodTemplate addServiceMethodTemplate = new AddServiceMethodTemplate(saveTemplateFilePath,className);

        String generate = engine.generate(addServiceMethodTemplate);
        System.out.println(generate);
        return generate;
    }

    private MetaMethod getAddService(){
        return new MetaMethod(generateAddService());
    }

    // serviceImpl 的删除方法
    private String generateDeleteService(){
        DeleteServiceMethodTemplate deleteServiceMethodTemplate = new DeleteServiceMethodTemplate(deleteTemplateFilePath,className);

        String generate = engine.generate(deleteServiceMethodTemplate);
        System.out.println(generate);
        return generate;
    }

    private MetaMethod getDeleteService() {
        return new MetaMethod(generateDeleteService());
    }

    // serviceImpl的查找方法
    private String generateSelectService(){
        SelectServiceMethodTemplate selectServiceMethodTemplate = new SelectServiceMethodTemplate(selectTemplateFilePath,className);

        String generate = engine.generate(selectServiceMethodTemplate);
        System.out.println(generate);
        return generate;
    }
    private MetaMethod getSelectService() {
        return new MetaMethod(generateSelectService());
    }

    // serviceImpl的更新方法
    private String generateUpdateService(){
        UpdateServiceMethodTemplate updateServiceMethodTemplate = new UpdateServiceMethodTemplate(updateTemplateFilePath,className);

        String generate = engine.generate(updateServiceMethodTemplate);
        System.out.println(generate);
        return generate;
    }
    private MetaMethod getUpdateService() {
        return new MetaMethod(generateUpdateService());
    }
}
/*
    // 加强判断
    public static List<MetalField> judgeMetalField(List<MetalField> fields){
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
//
//        for (String field : fields) {
//            if (field.equals("id")){
//                modelFields.add(new MetalField("id",String.class,tableId));
//            }else if (field.contains("Time")){
//                modelFields.add(new MetalField(field, Date.class, fillAnnotation));
//            }else {
//                modelFields.add(new MetalField(field,String.class));
//            }
//        }
//
        return fields;
    }*/
// 根据给定的实体类名、字段生成实体模板
/*
public  EntityTemplate getEntityTemplate() {
    EntityTemplate entityTemplate = new EntityTemplate(entityPackagePath, className, tableName);

*/
/*        List<MetalField> modelFields = new ArrayList<>();

        MetaAnnotation fillAnnotation = new MetaAnnotation(TableField.class, "fill", "FieldFill.INSERT_UPDATE", FieldFill.class);
        MetaAnnotation tableId = new MetaAnnotation(TableId.class);
        for (String field : fields) {
            if (field.equals("id")){
                modelFields.add(new MetalField("id",String.class,tableId));
            }else if (field.contains("Time")){
                modelFields.add(new MetalField(field, Date.class, fillAnnotation));
            }else {
                modelFields.add(new MetalField(field,String.class));
            }
        }
        //        entityTemplate.addModelFields(modelFields);
        *//*

    fields = judgeMetalField(fields);
    entityTemplate.addModelFields(fields);
    return entityTemplate;
}*/
