package com.tml.otowbackend.engine.tree.core;

import com.tml.otowbackend.engine.tree.entity.vo.NodeVO;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述: 抽象虚拟文件模板类，提供通用功能
 * @author suifeng
 * 日期: 2024/12/12
 */
@Slf4j
public abstract class AbstractVirtualTreeTemplate implements VirtualTreeTemplate {

    protected final Map<String, String> folderMappings = new HashMap<>(); // 文件夹名称到ID的映射
    protected final IVirtualFileService virtualFileService; // 虚拟文件服务
    protected final String treeId; // 模板对应的虚拟文件树ID

    public AbstractVirtualTreeTemplate(IVirtualFileService virtualFileService, String treeId) {
        this.virtualFileService = virtualFileService;
        this.treeId = treeId;
    }

    @Override
    public void initializeTemplate() {
        log.info("初始化虚拟文件模板...");
        List<NodeVO> rootNodes = virtualFileService.getChildrenNodes(treeId, null);
        buildFolderMappings(rootNodes);
    }

    /**
     * 获取文件夹ID
     */
    protected String getFolderId(String folderName) {
        return folderMappings.get(folderName);
    }

    /**
     * 建立文件夹名称到ID的映射关系
     */
    public abstract void buildFolderMappings(List<NodeVO> rootNodes);
}