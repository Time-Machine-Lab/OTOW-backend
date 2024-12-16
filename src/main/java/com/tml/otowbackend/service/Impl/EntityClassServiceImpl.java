package com.tml.otowbackend.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.otow.OTOWCacheService;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.service.EntityClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.tml.otowbackend.constants.ProjectConstants.ENTITY_CLASSES;

@Service
public class EntityClassServiceImpl implements EntityClassService {

    @Resource
    private OTOWCacheService cacheService;

    @Override
    public R<Void> addEntityClass(Long projectId, EntityClassDefinition entity) {
        List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);
        if (entityClasses.stream().anyMatch(e -> e.getClassName().equals(entity.getClassName()))) {
            throw new ServeException("实体类名已存在：" + entity.getClassName());
        }
        entityClasses.add(entity);
        updateCachedEntityClasses(projectId, entityClasses);
        return R.success("实体类添加成功");
    }

    @Override
    public R<Void> updateEntityClass(Long projectId, String className, EntityClassDefinition entity) {
        List<EntityClassDefinition> entityClasses = getCachedEntityClasses(projectId);
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
}