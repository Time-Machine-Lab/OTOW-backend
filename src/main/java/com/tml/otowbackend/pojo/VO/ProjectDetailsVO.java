package com.tml.otowbackend.pojo.VO;

import com.tml.otowbackend.pojo.DO.OTOWProject;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 项目详情 VO
 */
@Data
public class ProjectDetailsVO {

    private Long id;            // 项目ID
    private String title;       // 项目标题
    private String description; // 项目描述
    private String language;    // 项目语言
    private String type;        // 项目类型
    private String status;      // 项目状态
    private String metadata;    // 项目元数据
    private Date createTime;    // 创建时间
    private Date updateTime;    // 更新时间

    /**
     * 从实体类构造 VO
     *
     * @param project 项目实体类
     * @return 项目详情 VO
     */
    public static ProjectDetailsVO fromEntity(OTOWProject project) {
        ProjectDetailsVO vo = new ProjectDetailsVO();
        vo.setId(project.getId());
        vo.setTitle(project.getTitle());
        vo.setDescription(project.getDescription());
        vo.setLanguage(project.getLanguage());
        vo.setType(project.getType());
        vo.setStatus(project.getStatus());
        vo.setMetadata(project.getMetadata());
        vo.setCreateTime(project.getCreateTime());
        vo.setUpdateTime(project.getUpdateTime());
        return vo;
    }

    /**
     * 从缓存数据构造 VO
     *
     * @param projectId    项目 ID
     * @param cachedParams 缓存数据
     * @return 项目详情 VO
     */
    public static ProjectDetailsVO fromCache(Long projectId, Map<String, Object> cachedParams) {
        ProjectDetailsVO vo = new ProjectDetailsVO();
        vo.setId(projectId);
        vo.setTitle((String) cachedParams.get("title"));
        vo.setDescription((String) cachedParams.get("description"));
        vo.setLanguage((String) cachedParams.get("language"));
        vo.setType((String) cachedParams.get("type"));
        vo.setStatus((String) cachedParams.get("status"));
        vo.setMetadata((String) cachedParams.get("metadata"));
        vo.setCreateTime((Date) cachedParams.get("createTime"));
        vo.setUpdateTime((Date) cachedParams.get("updateTime"));
        return vo;
    }
}