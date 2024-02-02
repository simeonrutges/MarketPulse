package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.service.ProductService;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProductDto = productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>>getAllProducts(){
        List<ProductDto> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable Long productId){
        ProductDto product = productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@Valid @PathVariable Long productId, @RequestBody ProductDto productDto){
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void>deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
