package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;
import com.example.coffeeshop.model.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder( Email email, String address, List<OrderItem> orderItems);

    List<Order> findOrderByEmail(Email email);

    List<Order> findAllOrder();

    Optional<Order> findOrderById(UUID orderId);

    void updateOrderAddress(UUID orderId, String address);

    void updateOrderStatus(UUID orderId, OrderStatus status);

    void deleteOrder(UUID orderId);
}
