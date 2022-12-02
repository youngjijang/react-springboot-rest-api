package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, List<OrderItem> orderItems) {
}
