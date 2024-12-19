package com.tml.otowbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("coupons")
public class Coupon {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "discount")
    private Double discount;

    @TableField(value = "min_purchase")
    private Double minPurchase;

    @TableField(value = "start_date")
    private Date startDate;

    @TableField(value = "end_date")
    private Date endDate;

    @TableField(value = "status")
    private Integer status; // 优惠券状态：0-无效，1-有效
}