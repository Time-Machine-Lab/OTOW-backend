package com.tml.otowbackend.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequestDTO {

    @NotBlank(message = "项目名称不能为空")
    private String name;
    @NotBlank(message = "项目描述不能为空")
    private String description;
    @NotBlank(message = "项目介绍不能为空")
    private String introduce;
    @Min(value = 0, message = "项目价格必须大于0")
    @Max(value = 1000, message = "项目价格不能高于1000")
    private Integer price;
    @NotBlank(message = "项目语言不能为空")
    private String codeLanguage;
    @NotBlank(message = "项目封面不能为空")
    private String cover;
    @NotBlank(message = "项目下载链接不能为空")
    private String downloadUrl;
}
