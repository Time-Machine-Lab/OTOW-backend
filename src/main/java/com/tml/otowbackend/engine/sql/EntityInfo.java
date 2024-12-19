package com.tml.otowbackend.engine.sql;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class EntityInfo {
    private String tableName; // 表名
    private String tableComment; // 表注释
    private List<FieldInfo> fields; // 字段信息列表

    public EntityInfo(String tableName, String tableComment, List<FieldInfo> fields) {
        this.tableName = tableName;
        this.tableComment = tableComment;
        this.fields = fields;
    }

    public static List<EntityInfo> buildEntityInfoList(List<EntityClassDefinition> entityClassDefinitions) {
        List<EntityInfo> entityInfos = new ArrayList<>();
        for (EntityClassDefinition definition : entityClassDefinitions) {
            entityInfos.add(EntityInfo.buildByEntityClassDefinition(definition));
        }
        return entityInfos;
    }

    public static EntityInfo buildByEntityClassDefinition(EntityClassDefinition classDefinition) {
        // 表名：将类名转换为下划线命名
        String tableName = StringUtils.camelToUnderline(classDefinition.getClassName());

        // 表注释：使用实体类的中文描述
        String tableComment = classDefinition.getCdesc();

        // 字段信息列表
        List<FieldInfo> fieldInfos = new ArrayList<>();

        for (EntityClassDefinition.FieldDefinition fieldDefinition : classDefinition.getFields()) {
            String fname = fieldDefinition.getFname(); // 字段名
            String ftype = fieldDefinition.getFtype(); // 字段类型
            String fdesc = fieldDefinition.getFdesc(); // 字段描述

            // 判断是否为主键字段（fname 为 "id" 时）
            if ("id".equalsIgnoreCase(fname)) {
                FieldInfo idField = new FieldInfo.Builder(fname, FieldMapper.mapFTypeToSQLType(ftype))
                        .primaryKey(true)
                        .autoIncrement(true) // 主键默认为自增
                        .notNull(true) // 主键不能为空
                        .comment(fdesc != null ? fdesc : "主键") // 字段描述
                        .build();
                fieldInfos.add(idField);
            } else {
                // 普通字段
                FieldInfo field = new FieldInfo.Builder(fname, FieldMapper.mapFTypeToSQLType(ftype))
                        .notNull(false) // 默认允许为空
                        .comment(fdesc) // 字段描述
                        .build();
                fieldInfos.add(field);
            }
        }

        // 返回构建的 EntityInfo
        return new EntityInfo(tableName, tableComment, fieldInfos);
    }
}