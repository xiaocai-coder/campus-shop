package com.campus.backend.service;

import com.campus.backend.dto.CreateOrderRequest;
import com.campus.backend.dto.OrderItemRequest;
import com.campus.backend.dto.OrderResponse;
import com.campus.backend.entity.Order;
import com.campus.backend.entity.OrderItem;
import com.campus.backend.entity.Product;
import com.campus.backend.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Long createOrder(CreateOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("订单商品不能为空");
        }

        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = orderMapper.findProductById(itemRequest.getProductId());

            if (product == null) {
                throw new RuntimeException("商品不存在");
            }

            if (!"上架".equals(product.getStatus())) {
                throw new RuntimeException("商品已下架");
            }

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("库存不足");
            }

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalQuantity += itemRequest.getQuantity();
            totalPrice = totalPrice.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalQuantity(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setStatus("待处理");

        orderMapper.insertOrder(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            orderMapper.insertOrderItem(orderItem);
        }

        return order.getId();
    }

    public List<OrderResponse> findOrdersByUserId(Long userId) {
        List<Order> orders = orderMapper.findOrdersByUserId(userId);
        return buildOrderResponses(orders);
    }

    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderMapper.findAllOrders();
        return buildOrderResponses(orders);
    }

    public void completeOrder(Long id) {
        orderMapper.updateOrderStatus(id, "已完成");
    }

    private List<OrderResponse> buildOrderResponses(List<Order> orders) {
        List<OrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserId(order.getUserId());
            response.setTotalQuantity(order.getTotalQuantity());
            response.setTotalPrice(order.getTotalPrice());
            response.setStatus(order.getStatus());
            response.setCreateTime(order.getCreateTime());
            response.setItems(orderMapper.findItemsByOrderId(order.getId()));

            responses.add(response);
        }

        return responses;
    }
}