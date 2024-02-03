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

    public CartController(CartService cartService, DtoMapperService dtoMapperService) {
        this.cartService = cartService;
        this.dtoMapperService = dtoMapperService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = dtoMapperService.cartToDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        CartDto cartDto = cartService.getCartDtoByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }


    // deze methoden nog aanpassen en later doen....
//    @DeleteMapping("/users/{userId}/cart")
//    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
//        // Implementatie om alle items uit de winkelwagen van de gebruiker te verwijderen
//    }
//    @PostMapping("/users/{userId}/cart/checkout")
//    public ResponseEntity<OrderDto> checkoutCart(@PathVariable Long userId) {
//        // Implementatie om de winkelwagen af te rekenen en een bestelling te maken
//    }


}
