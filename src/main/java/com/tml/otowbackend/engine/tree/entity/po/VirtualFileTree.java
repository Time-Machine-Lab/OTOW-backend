package com.tml.otowbackend.engine.tree.entity.po;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class VirtualFileTree {

    private String treeId; // 虚拟树ID
    private VirtualFolderNode root; // 根节点

    private Map<String, VirtualFolderNode> folderMap;
    private Map<String, VirtualFileNode> fileMap;

    public VirtualFileTree(String treeId, VirtualFolderNode root) {
        this.treeId = treeId;
        this.root = root;
        this.folderMap = new ConcurrentHashMap<>();
        this.fileMap = new ConcurrentHashMap<>();
        buildMaps(root);
    }

    /**
     * 递归构建ID到节点的映射
     */
    private void buildMaps(VirtualFolderNode node) {
        folderMap.put(node.getId(), node);
        for (VirtualFolderNode child : node.getChildren()) {
            buildMaps(child);
        }
        for (VirtualFileNode file : node.getFiles()) {
            fileMap.put(file.getId(), file);
        }
    }

    /**
     * 添加文件夹到映射
     */
    public void addFolder(VirtualFolderNode node) {
        folderMap.put(node.getId(), node);
    }

    /**
     * 添加文件到映射
     */
    public void addFile(VirtualFileNode file) {
        fileMap.put(file.getId(), file);
    }

    /**
     * 移除文件夹从映射
     */
    public void removeFolder(String folderId) {
        folderMap.remove(folderId);
    }

    /**
     * 移除文件从映射
     */
    public void removeFile(String fileId) {
        fileMap.remove(fileId);
    }
}