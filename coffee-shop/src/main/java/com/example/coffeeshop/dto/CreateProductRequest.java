package com.example.coffeeshop.dto;

import com.example.coffeeshop.model.ProductCategory;

import java.util.UUID;

public record CreateProductRequest(String category, String name, long price, int amount, String description) {
    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
