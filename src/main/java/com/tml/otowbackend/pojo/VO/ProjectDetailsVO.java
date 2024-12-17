package com.tml.otowbackend.pojo.VO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.pojo.DO.OTOWProject;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProjectDetailsVO {

    private Long id;            // 项目ID
    private String title;       // 项目标题
    private String description; // 项目描述
    private String language;    // 项目语言
    private String type;        // 项目类型
    private String status;      // 项目状态
    private Map<String, Object> metadata; // 动态参数的 Map 格式
    private Date createTime;    // 创建时间
    private Date updateTime;    // 更新时间

    public static ProjectDetailsVO fromEntity(OTOWProject project) {
        ProjectDetailsVO vo = new ProjectDetailsVO();
        vo.setId(project.getId());
        vo.setTitle(project.getTitle());
        vo.setDescription(project.getDescription());
        vo.setLanguage(project.getLanguage());
        vo.setType(project.getType());
        vo.setStatus(project.getStatus());
        vo.setMetadata(deserializeMetadata(project.getMetadata())); // 反序列化 metadata
        vo.setCreateTime(project.getCreateTime());
        vo.setUpdateTime(project.getUpdateTime());
        return vo;
    }

    private static Map<String, Object> deserializeMetadata(String metadataJson) {
        try {
            return new ObjectMapper().readValue(metadataJson, Map.class);
        } catch (Exception e) {
            throw new ServeException("Metadata 反序列化失败: " + e.getMessage());
        }
    }
}