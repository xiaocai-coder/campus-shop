package com.campus.backend.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//订单实体类
@Data
public class Order {
    private Long id;
    private Long userId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createTime;
}