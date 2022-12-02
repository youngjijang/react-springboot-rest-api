package com.example.coffeeshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final Email email;
    private String address;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Order(UUID orderId, Email email, String address, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.address = address;
        createdAt = LocalDateTime.now();
        orderStatus = OrderStatus.ACCEPTED;
    }

    public Order(UUID orderId, Email email, String address, ArrayList<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime createAt, LocalDateTime updateAt) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.address = address;
        this.createdAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public List<OrderItem> addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        return orderItems;
    }

    public void changeAddress(String address){
        this.address = address;
    }
}
