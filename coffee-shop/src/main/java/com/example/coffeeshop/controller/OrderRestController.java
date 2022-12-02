package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderStatus;
import com.example.coffeeshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문을 조회한다.
    @GetMapping("/api/v1/my_order")
    public List<Order> findOrderByEmail(@RequestBody Email email){
        return orderService.findOrderByEmail(email);
    }

    // 주문이 들어온다
    @PostMapping("/api/v1/order")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return orderService.createOrder(
                new Email(createOrderRequest.email()),
                createOrderRequest.address(),
                createOrderRequest.orderItems()
        );
    }

    // 주문을 취소한다
    @DeleteMapping("/api/v1/my_order")
    public void deleteOrder(){

    }

    // 주문을 수정한다.(주소) - fetch
    @PostMapping()
    public void updateOrderAddress(){

    }

}
