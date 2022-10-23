package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.jpa.OrderEntity;
import com.example.order.service.OrderService;
import com.example.order.vo.RequestOrder;
import com.example.order.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/hearth_check")
    public String status() {
        return String.format("It's Working in Order Service on Port %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder requestOrder,
                                                     @PathVariable("userId") String userId) {
        OrderDto order = orderService.createOrder(requestOrder.toOrderDto(userId));
        ResponseOrder responseOrder = new ResponseOrder(order.getProductId(), order.getQty(), order.getUnitPrice(), order.getTotalPrice(), order.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> allOrdersByUserId = orderService.getAllOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        allOrdersByUserId.forEach(v -> {
            result.add(new ResponseOrder(v.getProductId(), v.getQty(), v.getUnitPrice(), v.getTotalPrice(), v.getOrderId()));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
