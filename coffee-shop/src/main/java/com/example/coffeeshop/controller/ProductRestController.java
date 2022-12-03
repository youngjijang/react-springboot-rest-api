package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;
import com.example.coffeeshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {
    private final ProductService productService;

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("api/v1/products")
    public List<Product> productList(@RequestParam Optional<ProductCategory> category){
        return category
                .map(productService::getByCategory)
                .orElse(productService.getAllProducts()); // optional map처리가 존재 할때만 동작?
    }
}
