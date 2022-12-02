package com.example.coffeeshop.model;

import java.util.Objects;
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

    public int reduceTotalAmount(int count){
        if(totalAmount<count) throw new IllegalArgumentException("해당 상품은 재고가 부족합니다. total amount : " +totalAmount);
        totalAmount -= count;
        return totalAmount;
    }

    public int increaseTotalAmount(int count){
        totalAmount += count;
        return totalAmount;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && totalAmount == product.totalAmount && Objects.equals(productId, product.productId) && Objects.equals(category, product.category) && Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, category, name, price, totalAmount, description);
    }}
