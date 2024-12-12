package com.tml.otowbackend.engine.tree.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class AddFolderDTO {
    
    /**
     * 父节点ID（可为空表示根节点）
     */
    private String parentId;

    /**
     * 文件夹名称
     */
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;
}