package com.example.coffeeshop.dto;

import java.util.UUID;

public record UpdateOrderRequest(UUID orderId, String address) {
}
