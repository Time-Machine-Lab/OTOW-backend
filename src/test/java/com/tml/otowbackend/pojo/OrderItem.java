package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_items")
public class OrderItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "product_id")
    private Long productId;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "price")
    private Double price;
}