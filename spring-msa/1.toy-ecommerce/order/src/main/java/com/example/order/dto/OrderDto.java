package com.example.order.dto;

import com.example.order.jpa.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    public OrderEntity toEntity() {
        return new OrderEntity(productId, qty, unitPrice, totalPrice, userId, orderId);
    }

    public OrderDto(String productId, Integer qty, Integer unitPrice, String userId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.userId = userId;
    }
}
