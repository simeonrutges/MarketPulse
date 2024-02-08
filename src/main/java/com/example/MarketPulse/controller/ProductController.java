package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.exceptions.ExtensionNotSupportedException;
import com.example.MarketPulse.fileUploadResponse.FileUploadResponse;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.service.ProductService;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @PostMapping("/{productId}/uploadImage")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long productId) {
        try {
            Product fileDocument = productService.uploadFileDocument(file, productId);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("products/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
            String contentType = file.getContentType();
            return ResponseEntity.ok(new FileUploadResponse(fileDocument.getFileName(), url, contentType));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        } catch (ExtensionNotSupportedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not upload file: " + e.getMessage());
        }
    }

    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Optional<Product> productWithImage = productService.findImage(fileName);

        if (!productWithImage.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return productService.singleFileDownload(fileName, request);
    }

//    @DeleteMapping("/deleteProductImage/{productId}")
//    public ResponseEntity<?> deleteProductImage(@PathVariable("productId") Long productId) {
//        try {
//            productService.deleteProductImage(productId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete profile image: " + e.getMessage());
//        }
//    }

    @DeleteMapping("/deleteProductImage/{productId}")
    public ResponseEntity<?> deleteProductImage(@PathVariable("productId") Long productId) {
        productService.deleteProductImage(productId);
        return ResponseEntity.ok().build();
    }
}
