package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("payment_records")
public class PaymentRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "payment_method")
    private String paymentMethod;

    @TableField(value = "amount")
    private Double amount;

    @TableField(value = "status")
    private Integer status; // 支付状态：0-失败，1-成功

    @TableField(value = "created_at")
    private Date createdAt;
}