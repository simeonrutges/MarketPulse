package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

//    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProductDto = productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }

    // Andere methoden voor productbeheer...
}
