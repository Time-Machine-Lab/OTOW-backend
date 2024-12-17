package com.tml.otowbackend.service;

import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.tree.common.R;

import java.util.List;

public interface EntityClassService {

    /**
     * 添加实体类到项目
     *
     * @param projectId 项目 ID
     * @param entity    实体类定义
     * @return 操作结果
     */
    R<Void> addEntityClass(Long projectId, EntityClassDefinition entity);

    /**
     * 修改项目中的实体类
     *
     * @param projectId  项目 ID
     * @param className  实体类名（唯一标识）
     * @param entity     修改后的实体类定义
     * @return 操作结果
     */
    R<Void> updateEntityClass(Long projectId, String className, EntityClassDefinition entity);

    /**
     * 删除项目中的实体类
     *
     * @param projectId 项目 ID
     * @param className 实体类名（唯一标识）
     * @return 操作结果
     */
    R<Void> deleteEntityClass(Long projectId, String className);

    /**
     * 获取项目的实体类列表
     *
     * @param projectId 项目 ID
     * @return 实体类列表
     */
    R<List<EntityClassDefinition>> getEntityClasses(Long projectId);
}