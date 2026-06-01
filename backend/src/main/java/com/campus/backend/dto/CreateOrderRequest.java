package com.campus.backend.dto;

import lombok.Data;

import java.util.List;

//订单表
@Data
public class CreateOrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}