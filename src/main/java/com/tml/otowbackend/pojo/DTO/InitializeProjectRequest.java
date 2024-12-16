package com.tml.otowbackend.pojo.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Data
public class InitializeProjectRequest {

    @Length(max = 10, message = "项目标题不得超过50字")
    @NotBlank(message = "项目标题不能为空")
    private String title;

    @Length(max = 10, message = "项目描述不得超过500字")
    @NotBlank(message = "项目描述不能为空")
    private String description;
}