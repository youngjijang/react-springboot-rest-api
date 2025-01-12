package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;
import com.example.coffeeshop.repository.ProductRepository;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductCategory category,String name, long price, int amount, String description ) {
        Product product = new Product(UUID.randomUUID(),category,name,price,amount,description);
        return productRepository.createProduct(product);
    }

    @Override
    public Product updateTotalAmount(Product product) {
        productRepository.updateTotalAmount(product);
        return product;
    }

    @Override
    public Product updateDescription(Product product) {
        productRepository.updateDescription(product);
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.deleteProduct(product.getProductId());
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductCategory> getAllCategory() {
        return productRepository.findAllCategory();
    }
}
