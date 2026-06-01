package com.campus.backend.controller;

import com.campus.backend.dto.CreateOrderRequest;
import com.campus.backend.dto.OrderResponse;
import com.campus.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public Map<String, Long> createOrder(@RequestBody CreateOrderRequest request) {
        Long orderId = orderService.createOrder(request);
        return Map.of("orderId", orderId);
    }

    @GetMapping("/api/orders/user/{userId}")
    public List<OrderResponse> findOrdersByUserId(@PathVariable Long userId) {
        return orderService.findOrdersByUserId(userId);
    }

    @GetMapping("/api/admin/orders")
    public List<OrderResponse> findAllOrders() {
        return orderService.findAllOrders();
    }

    @PutMapping("/api/admin/orders/{id}/complete")
    public Map<String, String> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return Map.of("message", "订单已完成");
    }
}