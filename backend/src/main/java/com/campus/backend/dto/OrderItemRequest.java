package com.campus.backend.dto;

import lombok.Data;

//商品项
@Data
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}