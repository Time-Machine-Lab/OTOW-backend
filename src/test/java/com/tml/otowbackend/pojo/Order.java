package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orders")
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "total_price")
    private Double totalPrice;

    @TableField(value = "status")
    private Integer status; // 订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消

    @TableField(value = "payment_method")
    private String paymentMethod;

    @TableField(value = "shipping_info_id")
    private Long shippingInfoId;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}