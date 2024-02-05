package com.example.MarketPulse.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CartDto {
    public Long id;
    @NotNull(message = "Gebruikers-ID mag niet null zijn")
    public Long userId; // Id van de gebruiker aan wie deze winkelwagen toebehoort
    public List<CartItemDto> items; // Lijst van winkelwagenitems

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
