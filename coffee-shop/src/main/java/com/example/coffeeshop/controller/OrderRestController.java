package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CreateOrderRequest;
import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.service.OrderService;
import com.example.coffeeshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {

    private final OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(OrderRestController.class);

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 주문을 조회한다.
     *
     * @param email
     * @return email에 해당하는 주문 list
     */
    @GetMapping("/myOrder")
    public List<Order> findOrderByEmail(@ModelAttribute Optional<Email> email) {
        return email.map(orderService::findOrderByEmail)
                .orElse(new ArrayList<>());
    }

    /**
     * 주문을 생성한다.
     *
     * @param createOrderRequest
     * @return order
     */
    @PostMapping("/order")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(
                new Email(createOrderRequest.email()),
                createOrderRequest.address(),
                createOrderRequest.orderItems()
        );
    }

    /**
     * 해당 order 삭제
     *
     * @param orderId
     */
    @DeleteMapping("/myOrder/{orderId}")
    public void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
    }

}
