package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Category;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.repository.CategoryRepository;
import com.example.MarketPulse.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, DtoMapperService dtoMapperService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = dtoMapperService.productDtoToProduct(productDto);

        Product savedProduct = productRepository.save(product);

        return dtoMapperService.productToProductDto(savedProduct);
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {

        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        List <Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
            .map(dtoMapperService :: productToProductDto)
            .collect(Collectors.toList());
    }
}
