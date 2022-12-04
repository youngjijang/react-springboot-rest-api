package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.OrderStatus;
import com.example.coffeeshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String ordersPage(Model model){
        var orders = orderService.findAllOrder();
        model.addAttribute("orders",orders);
        return "orders-list";
    }

    @GetMapping( "/orders/{orderId}")
    public String findOrder(@PathVariable("orderId")UUID orderId, Model model){
        var maybeOrder = orderService.findOrderById(orderId);
        if(maybeOrder.isPresent()){
            model.addAttribute("order",maybeOrder.get());
            return "order-detail";
        }
        return "404";
    }

    @PostMapping( "/orders/{orderId}")
    public String updateOrderStatus(@PathVariable("orderId")UUID orderId,@RequestParam String orderStatus){
        orderService.updateOrderStatus(orderId,OrderStatus.valueOf(orderStatus));
        return "redirect:/orders";
    }
}
