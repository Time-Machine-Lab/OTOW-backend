package com.tml.otowbackend.pojo.VO;

import com.tml.otowbackend.pojo.DO.OTOWProject;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户项目列表 VO
 */
@Data
public class UserProjectVO {

    private Long id;          // 项目ID
    private String title;     // 项目标题
    private String language;  // 项目语言
    private String type;      // 项目类型

    /**
     * 从实体类构造 VO
     *
     * @param project 项目实体类
     * @return 用户项目 VO
     */
    public static UserProjectVO fromEntity(OTOWProject project) {
        UserProjectVO vo = new UserProjectVO();
        vo.setId(project.getId());
        vo.setTitle(project.getTitle());
        vo.setLanguage(project.getLanguage());
        vo.setType(project.getType());
        return vo;
    }

    /**
     * 将实体类列表转换为 VO 列表
     *
     * @param projects 项目实体类列表
     * @return 用户项目 VO 列表
     */
    public static List<UserProjectVO> fromEntityList(List<OTOWProject> projects) {
        return projects.stream().map(UserProjectVO::fromEntity).collect(Collectors.toList());
    }
}