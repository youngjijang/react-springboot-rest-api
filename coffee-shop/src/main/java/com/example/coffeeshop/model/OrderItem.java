package com.example.coffeeshop.model;

import java.util.UUID;

public record OrderItem (UUID productId, long price, int quantity) {
}
