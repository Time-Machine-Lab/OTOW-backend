package com.tml.otowbackend.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.otow.OTOWCacheService;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.service.EntityClassService;
import com.tml.otowbackend.service.FeaturePackageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.tml.otowbackend.constants.ProjectConstants.ENTITY_CLASSES;

@Service
public class IEntityClassService implements EntityClassService {

    @Resource
    private OTOWCacheService cacheService;

    @Resource
    private FeaturePackageService featurePackageService;

    @Override
    public R<Void> addEntityClass(Long projectId, EntityClassDefinition entity) {
        // 校验实体类
        validateEntityClass(projectId, entity, false);

        // 获取项目的实体类列表
        List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);

        // 添加实体类到列表
        entityClasses.add(entity);

        // 更新缓存
        updateCachedEntityClasses(projectId, entityClasses);
        return R.success("实体类添加成功");
    }

    @Override
    public R<Void> updateEntityClass(Long projectId, String className, EntityClassDefinition entity) {
        // 校验实体类
        validateEntityClass(projectId, entity, true);

        // 获取项目的实体类列表
        List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);

        // 查找并更新实体类
        boolean updated = false;
        for (int i = 0; i < entityClasses.size(); i++) {
            if (entityClasses.get(i).getClassName().equals(className)) {
                entityClasses.set(i, entity);
                updated = true;
                break;
            }
        }
        if (!updated) {
            throw new ServeException("实体类不存在：" + className);
        }

        // 更新缓存
        updateCachedEntityClasses(projectId, entityClasses);

        return R.success("实体类更新成功");
    }

    @Override
    public R<Void> deleteEntityClass(Long projectId, String className) {
        List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);
        if (!entityClasses.removeIf(e -> e.getClassName().equals(className))) {
            throw new ServeException("实体类不存在：" + className);
        }
        updateCachedEntityClasses(projectId, entityClasses);
        return R.success("实体类删除成功");
    }

    @Override
    public R<List<EntityClassDefinition>> getEntityClasses(Long projectId) {
        return R.success("获取实体类列表成功", getCachedEntityClasses(projectId));
    }

    private List<EntityClassDefinition> getCachedEntityClasses(Long projectId) {
        String entityClassesJson = (String) cacheService.get(projectId, ENTITY_CLASSES);
        if (entityClassesJson == null || entityClassesJson.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return new ObjectMapper().readValue(entityClassesJson, new TypeReference<List<EntityClassDefinition>>() {});
        } catch (Exception e) {
            throw new ServeException("实体类列表解析失败：" + e.getMessage());
        }
    }

    private void updateCachedEntityClasses(Long projectId, List<EntityClassDefinition> entityClasses) {
        try {
            String entityClassesJson = new ObjectMapper().writeValueAsString(entityClasses);
            cacheService.put(projectId, ENTITY_CLASSES, entityClassesJson);
        } catch (Exception e) {
            throw new ServeException("实体类列表更新失败：" + e.getMessage());
        }
    }

    private void validateEntityClass(Long projectId, EntityClassDefinition entity, boolean isUpdate) {
        // 校验实体类名是否符合 Java 标识符规范
        if (!entity.getClassName().matches("^[a-zA-Z_$][a-zA-Z\\d_$]*$")) {
            throw new ServeException("实体类名不合法：" + entity.getClassName() + "，必须符合 Java 标识符规范");
        }

        // 校验项目中是否存在重复实体类名（仅在添加时校验）
        if (!isUpdate) {
            List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);
            if (entityClasses.stream().anyMatch(e -> e.getClassName().equals(entity.getClassName()))) {
                throw new ServeException("实体类名已存在：" + entity.getClassName());
            }
        }

        // 校验字段列表
        if (entity.getFields() == null || entity.getFields().isEmpty()) {
            throw new ServeException("实体类必须包含至少一个字段：" + entity.getClassName());
        }

        for (EntityClassDefinition.FieldDefinition field : entity.getFields()) {
            // 校验字段名是否符合 Java 标识符规范
            if (!field.getFname().matches("^[a-zA-Z_$][a-zA-Z\\d_$]*$")) {
                throw new ServeException("字段名不合法：" + field.getFname() + "，必须符合 Java 标识符规范");
            }

            // 校验字段类型是否合法
            if (!isValidJavaType(field.getFtype())) {
                throw new ServeException("字段类型不合法：" + field.getFtype() + "，必须是支持的 Java 类型");
            }

            // 校验字段描述是否为空
            if (field.getFdesc() == null || field.getFdesc().trim().isEmpty()) {
                throw new ServeException("字段描述不能为空，字段：" + field.getFname());
            }
        }

        // 校验功能包列表是否合法
        featurePackageService.validateFeaturePackages(entity.getFeatureIds());
    }

    /**
     * 校验字段类型是否是合法的 Java 类型
     *
     * @param fieldType 字段类型
     * @return 是否合法
     */
    private boolean isValidJavaType(String fieldType) {
        // 支持的字段类型
        List<String> validTypes = List.of(
                "String", "Integer", "Long", "Double", "Float", "Boolean", "Date"
        );
        return validTypes.contains(fieldType);
    }
}