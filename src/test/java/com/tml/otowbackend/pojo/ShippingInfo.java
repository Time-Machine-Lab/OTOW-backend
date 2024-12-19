package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("shipping_info")
public class ShippingInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "receiver_name")
    private String receiverName;

    @TableField(value = "receiver_phone")
    private String receiverPhone;

    @TableField(value = "address")
    private String address;

    @TableField(value = "status")
    private Integer status; // 物流状态：0-未发货，1-已发货，2-已签收
}