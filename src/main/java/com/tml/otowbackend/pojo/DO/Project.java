package com.tml.otowbackend.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tml.otowbackend.constants.CodeLanguage;
import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private Integer id;
    private String name;
    private String cover;
    private String description;
    private String introduce;
    private String shareUid;
    private String url;
    private Integer codeLanguage;
    private Integer downloadNums;
    private Integer viewNums;
    private Integer price;
    @TableField(fill = FieldFill.INSERT)
    private LocalDate createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDate updateTime;

    public static Project convert(CreateProjectRequestDTO dto){
        Integer language = CodeLanguage.queryCodeByLanguage(dto.getCodeLanguage());
        Project project = Project.builder().codeLanguage(language).build();
        BeanUtils.copyProperties(dto,project);
        return project;
    }
}
