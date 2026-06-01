package com.campus.backend.dto;

import com.campus.backend.entity.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createTime;
    private List<OrderItem> items;
}