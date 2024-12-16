package com.tml.otowbackend.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

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
}
