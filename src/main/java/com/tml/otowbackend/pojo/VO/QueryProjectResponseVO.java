package com.tml.otowbackend.pojo.VO;


import com.tml.otowbackend.constants.CodeLanguageEnum;
import com.tml.otowbackend.pojo.DO.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/18 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryProjectResponseVO {

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

    public static QueryProjectResponseVO convert(Project project){
        return QueryProjectResponseVO.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .codeLanguage(CodeLanguageEnum.queryLanguageByCode(project.getCodeLanguage()))
                .cover(project.getCover())
                .description(project.getDescription())
                .introduce(project.getIntroduce())
                .downloadNums(project.getDownloadNum())
                .viewNums(project.getViewNum())
                .price(project.getPrice())
                .nickname("otow管理员")
                .build();
    }

    public static QueryProjectResponseVO convertWithoutDetail(Project project){
        return QueryProjectResponseVO.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .codeLanguage(CodeLanguageEnum.queryLanguageByCode(project.getCodeLanguage()))
                .cover(project.getCover())
                .description("")
                .introduce("")
                .downloadNums(project.getDownloadNum())
                .viewNums(project.getViewNum())
                .price(project.getPrice())
                .nickname("otow管理员")
                .build();
    }

}
