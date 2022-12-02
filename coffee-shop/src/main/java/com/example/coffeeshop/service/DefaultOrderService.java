package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;
import com.example.coffeeshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Email email, String address, List<OrderItem> orderItems) {
        var findOrder = findOrderByEmailAndAddress(email, address);
        if(findOrder.isPresent()){
            orderItems.forEach(item->{
                addOrderItem(findOrder.get(),item);
            });
            return findOrder.get();
        }else{
            Order newOrder = new Order(UUID.randomUUID(),email, address, orderItems);
            orderRepository.createOrder(newOrder);
            return newOrder;
        }
    }

    private Order addOrderItem(Order order, OrderItem orderItem) {
        orderRepository.insertOrderItem(order.getOrderId(),orderItem);
        return order;
    }

    private Optional<Order> findOrderByEmailAndAddress(Email email, String address) {
        return orderRepository.findAcceptOrderByEmailAndAddress(email, address);
    }

    @Transactional
    @Override
    public List<Order> findOrderByEmail(Email email) {
        return orderRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public Optional<Order> findOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    @Override
    public void updateOrderAddress(Order order) {
        orderRepository.updateAddress(order);
    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order.getOrderId());
    }

}
