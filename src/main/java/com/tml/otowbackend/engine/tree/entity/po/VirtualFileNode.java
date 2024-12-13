package com.tml.otowbackend.engine.tree.entity.po;

import lombok.Data;

import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class VirtualFileNode {

    private String id; // 文件唯一标识
    private String name; // 文件名称
    private String type; // 文件类型（扩展名）
    private long size; // 文件大小（字节）
    private List<String> rawLines; // 文件内容（按行分割）

    public VirtualFileNode(String id, String name, String type, long size, List<String> rawLines) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.rawLines = rawLines;
    }
}