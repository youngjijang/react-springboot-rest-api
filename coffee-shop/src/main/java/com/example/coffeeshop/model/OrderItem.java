package com.example.coffeeshop.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public record OrderItem(UUID productId, long price, int quantity, LocalDateTime createdAt) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return price == orderItem.price && quantity == orderItem.quantity && Objects.equals(productId, orderItem.productId) && Objects.equals(createdAt, orderItem.createdAt);
    }

}
