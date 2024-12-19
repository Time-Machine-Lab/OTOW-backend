package com.tml.otowbackend.engine.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tml.otowbackend.engine.sql.annotation.DefaultValue;
import com.tml.otowbackend.engine.sql.annotation.TransientField;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 描述:
 *
 * @author suifeng
 * 日期: 2024/12/12
 */
public class EntityScanner {

    /**
     * 获取实体类的字段信息
     *
     * @param clazz 实体类
     * @return 字段信息列表
     */
    public static List<FieldInfo> getFields(Class<?> clazz) {
        List<FieldInfo> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            // 跳过标记为 @TransientField 的字段
            if (field.isAnnotationPresent(TransientField.class)) {
                continue;
            }

            FieldInfo fieldInfo = new FieldInfo();

            // 设置字段名
            fieldInfo.setColumnName(NameConverter.camelToSnake(field.getName()));

            // 设置字段类型
            fieldInfo.setColumnType(FieldMapper.mapJavaTypeToSQLType(field));

            // 检查是否为主键
            if (field.isAnnotationPresent(TableId.class)) {
                TableId tableId = field.getAnnotation(TableId.class);
                fieldInfo.setPrimaryKey(true);
                if (tableId.type() == IdType.AUTO) {
                    fieldInfo.setAutoIncrement(true);
                }
            }

            // 检查是否有默认值
            if (field.isAnnotationPresent(DefaultValue.class)) {
                fieldInfo.setDefaultValue(field.getAnnotation(DefaultValue.class).value());
            }

            // 检查是否有非空约束
            if (field.isAnnotationPresent(TableField.class)) {
                TableField tableField = field.getAnnotation(TableField.class);
                fieldInfo.setNotNull(!tableField.exist());
            }

            // 检查是否有注释
            if (field.isAnnotationPresent(TableField.class)) {
                TableField tableField = field.getAnnotation(TableField.class);
                fieldInfo.setComment(tableField.value());
            }

            fields.add(fieldInfo);
        }
        return fields;
    }

    /**
     * 根据包名扫描实体类并生成实体信息列表
     *
     * @param packageName 包名
     * @return 实体信息列表
     */
    public static List<EntityInfo> buildFromPackage(String packageName) {
        List<Class<?>> entityClasses = EntityScanner.scanEntities(packageName);
        List<EntityInfo> entities = new ArrayList<>();

        for (Class<?> clazz : entityClasses) {
            String tableName = NameConverter.getTableName(clazz);
            List<FieldInfo> fields = getFields(clazz);
            entities.add(new EntityInfo(tableName, "", fields));
        }

        return entities;
    }

    /**
     * 扫描指定包下的所有类
     *
     * @param packageName 包名
     * @return 类的列表
     */
    public static List<Class<?>> scanEntities(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource == null) {
            throw new RuntimeException("包路径不存在: " + packageName);
        }

        File directory = new File(resource.getFile());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith(".class")) {
                try {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("类加载失败: " + file.getName(), e);
                }
            }
        }

        return classes;
    }
}