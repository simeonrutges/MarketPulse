package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Category;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CategoryRepository;
import com.example.MarketPulse.repository.ProductRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, DtoMapperService dtoMapperService, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

    public List<ProductDto> getAllProducts() {
        List<Product>products = productRepository.findAll();
        return products.stream()
                .map(dtoMapperService :: productToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product niet gevonden met id: " + productId));
        return dtoMapperService.productToProductDto(product);
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product niet gevonden met ID: " + productId));

        if (productDto.getPrice() >= 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getSellerId() != null){
            User seller = userRepository.findById(productDto.getSellerId())
                    .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: " + productDto.getSellerId()));
            product.setSeller(seller);
        }
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()-> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(product);

        return dtoMapperService.productToProductDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
