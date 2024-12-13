package com.tml.otowbackend.engine.tree.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@AllArgsConstructor
@Data
public class AddFileDTO {
    
    /**
     * 父节点ID（可为空表示根节点）
     */
    private String parentId;

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    /**
     * 文件内容（按行分割）
     */
    @NotNull(message = "文件内容不能为空")
    private List<String> content;
}