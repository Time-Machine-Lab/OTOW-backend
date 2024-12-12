package com.tml.otowbackend.engine.tree.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class UpdateFileDTO {

    private String fileId;

    /**
     * 文件内容（按行分割）
     */
    @NotNull(message = "文件内容不能为空")
    private List<String> content;
}