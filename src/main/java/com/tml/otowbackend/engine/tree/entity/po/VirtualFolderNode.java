package com.tml.otowbackend.engine.tree.entity.po;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class VirtualFolderNode {

    private String id; // 节点唯一标识
    private String name; // 文件夹名称
    private String parentId; // 父节点ID，根节点为null
    private List<VirtualFolderNode> children; // 子文件夹节点
    private List<VirtualFileNode> files; // 文件节点列表

    public VirtualFolderNode(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    /**
     * 添加子文件夹
     */
    public void addChild(VirtualFolderNode child) {
        this.children.add(child);
    }

    /**
     * 添加文件
     */
    public void addFile(VirtualFileNode file) {
        this.files.add(file);
    }

    /**
     * 移除子文件夹
     */
    public void removeChild(VirtualFolderNode child) {
        this.children.remove(child);
    }

    /**
     * 移除文件
     */
    public void removeFile(VirtualFileNode file) {
        this.files.remove(file);
    }
}