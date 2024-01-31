package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CategoryDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Category;
import com.example.MarketPulse.repository.CategoryRepository;
import com.example.MarketPulse.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, DtoMapperService dtoMapperService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoMapperService.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return dtoMapperService.categoryToDto(savedCategory);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(dtoMapperService :: categoryToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        return dtoMapperService.categoryToDto(category);
    }

    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        dtoMapperService.updateCategoryFromDto(category, categoryDto);
        Category updatedCategory = categoryRepository.save(category);
        return dtoMapperService.categoryToDto(updatedCategory);
    }
}
