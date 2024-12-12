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

        // 获取目标层级
        int targetDepth = getTargetDepth();

        // 从根节点开始逐层往下获取目标层级的文件夹节点
        List<NodeVO> currentNodes = virtualFileService.getChildrenNodes(treeId, null);
        for (int depth = 1; depth <= targetDepth; depth++) {
            if (currentNodes == null || currentNodes.isEmpty()) {
                throw new IllegalStateException("无法找到目标层级的文件夹，请检查虚拟文件树结构。");
            }
            if (depth == targetDepth) {
                break;
            }
            List<NodeVO> nextLevelNodes = new java.util.ArrayList<>();
            for (NodeVO node : currentNodes) {
                if ("folder".equals(node.getType())) {
                    nextLevelNodes.addAll(virtualFileService.getChildrenNodes(treeId, node.getId()));
                }
            }
            currentNodes = nextLevelNodes;
        }

        buildFolderMappings(currentNodes);
    }

    /**
     * 获取文件夹ID
     */
    public String getFolderId(String folderName) {
        return folderMappings.get(folderName);
    }

    /**
     * 获取目标层级
     */
    public abstract int getTargetDepth();

    /**
     * 建立文件夹名称到ID的映射关系
     */
    public abstract void buildFolderMappings(List<NodeVO> rootNodes);
}