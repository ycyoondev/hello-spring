package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getAllOrdersByUserId(String userId);
}
