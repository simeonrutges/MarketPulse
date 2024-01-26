package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.CartDto;
import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.service.CartService;
import com.example.MarketPulse.service.DtoMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/carts")
public class CartController {
    private final CartService cartService;
    private final DtoMapperService dtoMapperService;

    @Autowired
    public CartController(CartService cartService, DtoMapperService dtoMapperService) {
        this.cartService = cartService;
        this.dtoMapperService = dtoMapperService;
    }

    // Ophalen van een specifieke winkelwagen
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = dtoMapperService.cartToDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    // Overige methoden (toevoegen, bijwerken, verwijderen)...
}
