package com.campus.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String icon;
    private String description;
    private String status;
}