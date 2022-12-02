package com.example.coffeeshop.model;

import java.util.Objects;

public record Email(String address) {
    public Email(String address) {
        this.address = address;
    }

    private boolean checkAddress(String address) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

}
