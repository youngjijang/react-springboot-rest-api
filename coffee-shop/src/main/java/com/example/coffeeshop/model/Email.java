package com.example.coffeeshop.model;

public record Email(String address) {
    public Email(String address) {
        this.address = address;
    }

    private boolean checkAddress(String address){
        return true;
    }
}
