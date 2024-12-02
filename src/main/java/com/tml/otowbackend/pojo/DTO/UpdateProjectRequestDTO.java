package com.tml.otowbackend.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.logging.log4j.message.Message;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectRequestDTO {

    @NotBlank(message = "项目id不能为空")
    private String id;
    private String name;
    private String description;
    private String introduce;
    private Integer price;
    private String codeLanguage;
    private String cover;
    private String url;

}
