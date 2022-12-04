package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order createOrder(Order order);

    Optional<Order> findById(UUID orderId);

    List<Order> findByEmail(Email email);

    List<Order> findAll();

    Optional<Order> findAcceptOrderByEmailAndAddress(Email email, String address);

    OrderItem insertOrderItem(UUID orderId, OrderItem orderItem);

    void updateOrderItem(UUID orderId, OrderItem orderItem);

    void updateAddress(Order order);

    void updateOrderStatus(Order order);

    void deleteOrder(UUID orderId);

}
