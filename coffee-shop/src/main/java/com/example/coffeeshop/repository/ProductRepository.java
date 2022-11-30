package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product createProduct(Product product);

    void deleteProduct(UUID productId);

    Optional<Product> findById(UUID productId);

    List<Product> findByCategory(ProductCategory category);

    void updateTotalAmount(Product product);

    void updateDescription(Product product);
}
