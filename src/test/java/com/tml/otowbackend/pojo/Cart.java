package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.otowbackend.engine.sql.annotation.ColumnLength;
import com.tml.otowbackend.engine.sql.annotation.DefaultValue;
import com.tml.otowbackend.engine.sql.annotation.TextType;
import com.tml.otowbackend.engine.sql.annotation.TransientField;
import lombok.Data;

import java.util.Date;

@Data
@TableName("carts")
public class Cart {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long productId;

    @TableField(value = "quantity_test")
    private Integer quantity;

    private Date createdAt;

    @TextType
    @TableField(value = "description")
    private String desc;

    private boolean has_delete;

    @ColumnLength(1000)
    private String notes;

    @DefaultValue("辣条非常好吃")
    @ColumnLength(20)
    private String title;

    private String testCamel;

    @TransientField
    private String not;
}