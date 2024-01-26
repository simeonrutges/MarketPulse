package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;

    public ProductService(ProductRepository productRepository, DtoMapperService dtoMapperService) {
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = dtoMapperService.productDtoToProduct(productDto);

        Product savedProduct = productRepository.save(product);

        return dtoMapperService.productToProductDto(savedProduct);
    }
}
