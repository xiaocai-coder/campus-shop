package com.campus.backend.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String icon;
    private String description;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}