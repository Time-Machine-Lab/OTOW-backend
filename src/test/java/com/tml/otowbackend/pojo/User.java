package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.otowbackend.engine.sql.annotation.DefaultValue;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    @DefaultValue("Genius666")
    private String username;

    @TableField(value = "password")
    @DefaultValue("123456")
    private String password;

    @TableField(value = "email")
    @DefaultValue("969025903@qq.com")
    private String email;

    @TableField(value = "phone_number")
    @DefaultValue("1649168749")
    private String phoneNumber;

    @TableField(value = "status")
    private Integer status; // 用户状态：0-禁用，1-启用

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}