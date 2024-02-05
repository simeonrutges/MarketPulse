package com.example.MarketPulse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {
    public Long id;
    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(max = 255, message = "Naam mag maximaal 255 tekens lang zijn")
    public String name;
    @Size(max = 1000, message = "Beschrijving mag maximaal 1000 tekens lang zijn")
    public String description;

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
}
