package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.jpa.OrderEntity;
import com.example.order.jpa.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        OrderEntity orderEntity = orderDto.toEntity();

        orderEntityRepository.save(orderEntity);

        return orderDto;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderEntityRepository.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto(orderEntity.getProductId(), orderEntity.getQty(), orderEntity.getUnitPrice(), orderEntity.getTotalPrice(), orderId, orderEntity.getUserId());
        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getAllOrdersByUserId(String userId) {
        return orderEntityRepository.findByUserId(userId);
    }
}
