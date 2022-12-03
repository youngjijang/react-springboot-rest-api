package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CreateOrderRequest;
import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.ProductCategory;
import com.example.coffeeshop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(OrderRestController.class);

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문을 조회한다.
    @GetMapping("/api/v1/myOrder")
    public List<Order> findOrderByEmail(@ModelAttribute Optional<Email> email){
        log.info(email.toString());
        return email.map(orderService::findOrderByEmail)
                .orElse(new ArrayList<>());
    }

    // 주문이 들어온다
    @PostMapping("/api/v1/order")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        log.info(createOrderRequest.toString());
        return orderService.createOrder(
                new Email(createOrderRequest.email()),
                createOrderRequest.address(),
                createOrderRequest.orderItems()
        );
    }

//    // 주문을 취소한다
//    @DeleteMapping("/api/v1/myOrder")
//    public void deleteOrder(){
//
//    }
//
//    // 주문을 수정한다.(주소) - fetch
//    @PostMapping()
//    public void updateOrderAddress(){
//
//    }

}
