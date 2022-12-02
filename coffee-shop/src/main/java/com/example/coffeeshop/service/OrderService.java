package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder( Email email, String address, List<OrderItem> orderItems);

    List<Order> findOrderByEmail(Email email);

    Optional<Order> findOrderById(UUID orderId);

    void updateOrderAddress(Order order);

    void deleteOrder(Order order);
}
