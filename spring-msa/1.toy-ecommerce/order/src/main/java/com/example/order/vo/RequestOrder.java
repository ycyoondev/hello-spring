package com.example.order.vo;

import com.example.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;

    public OrderDto toOrderDto(String userId) {
        return new OrderDto(productId, qty, unitPrice, userId);
    }
}
