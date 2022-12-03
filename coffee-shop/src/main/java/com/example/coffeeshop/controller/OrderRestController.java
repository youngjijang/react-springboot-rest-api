package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CreateOrderRequest;
import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
