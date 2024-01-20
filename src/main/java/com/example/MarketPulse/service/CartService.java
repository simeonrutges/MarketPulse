package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CartDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final DtoMapperService dtoMapperService;

    public CartService(CartRepository cartRepository, DtoMapperService dtoMapperService) {
        this.cartRepository = cartRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public CartDto getUserCart(Long userId) {
        // Aannemend dat je een methode hebt in CartRepository om de cart op te halen via userId
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Winkelwagen niet gevonden voor gebruiker met ID: " + userId));

        // Gebruik de mapper-service om de Cart-entiteit om te zetten naar een CartDto
        return dtoMapperService.cartToDto(cart);
    }
}
