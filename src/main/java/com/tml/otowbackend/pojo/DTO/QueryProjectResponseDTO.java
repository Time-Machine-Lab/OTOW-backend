package com.tml.otowbackend.pojo.DTO;

import com.tml.otowbackend.constants.CodeLanguage;
import com.tml.otowbackend.pojo.DO.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryProjectResponseDTO {

    private String id;
    private String name;
    private String codeLanguage;
    private String cover;
    private String description;
    private String introduce;
    private String nickname;
    private Integer downloadNums;
    private Integer viewNums;
    private Integer price;

    public static QueryProjectResponseDTO convert(Project project){
        return QueryProjectResponseDTO.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .codeLanguage(CodeLanguage.queryLanguageByCode(project.getCodeLanguage()))
                .cover(project.getCover())
                .description(project.getDescription())
                .introduce(project.getIntroduce())
                .downloadNums(project.getDownloadNum())
                .viewNums(project.getViewNum())
                .price(project.getPrice())
                .nickname("otow管理员")
                .build();
    }

    public static QueryProjectResponseDTO convertWithoutDetail(Project project){
        return QueryProjectResponseDTO.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .codeLanguage(CodeLanguage.queryLanguageByCode(project.getCodeLanguage()))
                .cover(project.getCover())
                .downloadNums(project.getDownloadNum())
                .viewNums(project.getViewNum())
                .price(project.getPrice())
                .nickname("otow管理员")
                .build();
    }

}
