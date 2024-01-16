package com.example.MarketPulse.dto;

import java.util.List;

public class CartDto {
    public Long id;
    public Long userId; // Id van de gebruiker aan wie deze winkelwagen toebehoort
    public List<CartItemDto> items; // Lijst van winkelwagenitems
}
