package com.tml.otowbackend.service;


import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.pojo.VO.ProjectDetailsVO;
import com.tml.otowbackend.pojo.VO.UserProjectVO;

import java.util.List;
import java.util.Map;

public interface OTOWProjectService {

    /**
     * 初始化项目或更新项目参数
     *
     * @param projectId 项目ID（可选）
     * @param params    参数Map（支持title、description、language、type）
     * @return 返回项目ID
     */
    R<Long> manageProject(Long projectId, Map<String, Object> params);

    /**
     * 保存项目，将缓存中的数据持久化到数据库
     *
     * @param projectId 项目ID
     */
    void saveProject(Long projectId);

    /**
     * 获取用户项目列表
     *
     * @param userId 用户ID
     * @return 用户项目列表
     */
    R<List<UserProjectVO>> getUserProjects(String userId);

    /**
     * 获取项目详情
     *
     * @param projectId 项目ID
     * @return 项目详情
     */
    R<ProjectDetailsVO> getProjectDetails(Long projectId);

    /**
     * AI 生成项目大纲描述
     *
     * @param projectId 项目ID
     * @return 生成的项目描述
     */
    R<String> generateProjectOutline(Long projectId);
}