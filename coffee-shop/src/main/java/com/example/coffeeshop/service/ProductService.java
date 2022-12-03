package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductCategory category,String name, long price, int amount, String description);

    Product updateTotalAmount(Product product);

    Product updateDescription(Product product);

    Optional<Product> findById(UUID productId);

    List<Product> findByCategory(ProductCategory category);

    void deleteProduct(Product product);

    List<Product> getAllProducts();

    List<ProductCategory> getAllCategory();
}
