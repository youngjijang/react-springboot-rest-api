package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order createOrder(Order order);

    Optional<Order> findById(UUID orderId);

    Optional<Order> findByEmail(Email email);

    Optional<Order> findByEmailAndAddress(Email email, String address);

    void insertOrderItem(UUID orderId, OrderItem orderItem);

    void updateOrderItem(UUID orderId, OrderItem orderItem);

    void updateAddress(Order order);

    void deleteOrder(Order order);

}
