package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;
import com.example.coffeeshop.model.OrderStatus;
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
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Order> findOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    @Override
    public void updateOrderAddress(UUID orderId, String address) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("존재하지 않은 주문입니다."));
        order.changeAddress(address);
        orderRepository.updateAddress(order);
    }

    @Transactional
    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("존재하지 않은 주문입니다."));
        order.changeOrderStatus(status);
        orderRepository.updateOrderStatus(order);
    }

    @Transactional
    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteOrder(orderId);
    }

}
