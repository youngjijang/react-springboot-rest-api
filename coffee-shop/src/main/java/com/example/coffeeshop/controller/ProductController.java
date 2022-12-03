package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CreateProductRequest;
import com.example.coffeeshop.model.ProductCategory;
import com.example.coffeeshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model){
        var products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "products-list";
    }

    @GetMapping("/new-product")
    public String newProductPage(Model model){
        var categories = productService.getAllCategory();
        model.addAttribute("categories",categories);
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(@ModelAttribute CreateProductRequest createProductRequest){
        String[] categoryInfo = createProductRequest.category().split(" ");
        ProductCategory category = new ProductCategory(Integer.parseInt(categoryInfo[0]),categoryInfo[1]);
        productService.createProduct(category,createProductRequest.name(), createProductRequest.price(),
                createProductRequest.amount(),createProductRequest.description());
        return "redirect:/products";
    }


}
