package com.example.MarketPulse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {
    public Long id;
    @NotBlank(message = "Productnaam mag niet leeg zijn")
    @Size(min = 1, max = 100, message = "Productnaam moet tussen 1 en 100 tekens lang zijn")
    public String name;
    @NotBlank(message = "Beschrijving mag niet leeg zijn")
    @Size(min = 10, max = 1000, message = "Beschrijving moet tussen 10 en 1000 tekens lang zijn")
    public String description;
    @NotNull(message = "Prijs mag niet null zijn")
    @Min(value = 0, message = "Prijs moet groter dan of gelijk aan 0 zijn")
    public double price;
    @NotNull(message = "Verkoper ID mag niet null zijn")
    public Long sellerId;
//    @NotNull(message = "Categorie ID mag niet null zijn")
    public Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
