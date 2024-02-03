package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CartDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.model.User;
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

    public Cart createCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    public CartDto getUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Winkelwagen niet gevonden voor gebruiker met ID: " + userId));

        return dtoMapperService.cartToDto(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Winkelwagen niet gevonden met ID: " + cartId));
    }

    public CartDto getCartDtoByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Winkelwagen niet gevonden voor gebruiker met ID: " + userId));

        return dtoMapperService.cartToDto(cart);
    }



}
