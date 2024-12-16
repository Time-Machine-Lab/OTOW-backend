package com.tml.otowbackend.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.otowbackend.engine.tree.common.ServeException;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 描述: 生成项目实体类
 * @author suifeng
 * 日期: 2024/12/16
 */
@Data
@TableName("otow_project")
public class OTOWProject {

    private Long id;

    private String uid;

    private String title;

    private String description;

    private String language;

    private String type;

    private String status;

    private String metadata;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public static String serializeMetadata(Map<String, Object> metadata) {
        try {
            return new ObjectMapper().writeValueAsString(metadata);
        } catch (Exception e) {
            throw new ServeException("Metadata 序列化失败: " + e.getMessage());
        }
    }

    public static Map<String, Object> deserializeMetadata(String metadataJson) {
        try {
            return new ObjectMapper().readValue(metadataJson, Map.class);
        } catch (Exception e) {
            throw new ServeException("Metadata 反序列化失败: " + e.getMessage());
        }
    }
}
