package com.tml.otowbackend.engine.tree.entity.vo;

import lombok.Data;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class NodeVO {
    private String id;
    private String name;
    private String type; // "folder" 或 "file"
    private String fileType; // 仅用于文件
    private long size; // 仅用于文件

    // 静态工厂方法用于创建文件夹VO
    public static NodeVO folder(String id, String name) {
        NodeVO vo = new NodeVO();
        vo.setId(id);
        vo.setName(name);
        vo.setType("folder");
        return vo;
    }

    // 静态工厂方法用于创建文件VO
    public static NodeVO file(String id, String name, String fileType, long size) {
        NodeVO vo = new NodeVO();
        vo.setId(id);
        vo.setName(name);
        vo.setType("file");
        vo.setFileType(fileType);
        vo.setSize(size);
        return vo;
    }
}