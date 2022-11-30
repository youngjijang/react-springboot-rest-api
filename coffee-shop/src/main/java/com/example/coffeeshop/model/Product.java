package com.example.coffeeshop.model;

import java.util.UUID;

public class Product {
    private final UUID productId;
    private ProductCategory category;
    private final String name;
    private long price;
    private int totalAmount;
    private String description;

    public Product(UUID productId, ProductCategory category, String name, long price, int totalAmount, String description) {
        this.productId = productId;
        this.category = category;
        this.name = name;
        this.price = price;
        this.totalAmount = totalAmount;
        this.description = description;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
