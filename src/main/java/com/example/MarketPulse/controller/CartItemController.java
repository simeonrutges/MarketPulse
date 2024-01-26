package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.service.CartItemService;
import com.example.MarketPulse.service.DtoMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

//    @PostMapping("/users/{userId}/cart/items")
//    public ResponseEntity<Void> addCartItem(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
//        cartItemService.addCartItem(userId, cartItemDto);
//        return ResponseEntity.ok().build();
//    }
@PostMapping("/users/{userId}/cart/items")
public ResponseEntity<CartItemDto> addCartItem(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
    CartItemDto createdItem = cartItemService.addCartItem(userId, cartItemDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
}
////////
    // Endpoint voor het verwijderen van een cart item
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        cartItemService.removeCartItem(itemId);
        return ResponseEntity.ok().build();
    }

    // Endpoint voor het bijwerken van een cart item
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long itemId, @RequestBody CartItemDto cartItemDto) {
        CartItemDto updatedItem = cartItemService.updateCartItem(itemId, cartItemDto);
        return ResponseEntity.ok(updatedItem);
    }

//    // Endpoint voor gedeeltelijke update van een cart item. later extra doen
//    @PatchMapping("/{itemId}")
//    public ResponseEntity<CartItemDto> updateCartItemPartial(@PathVariable Long itemId, @RequestBody Map<String, Object> updates) {
//        CartItemDto updatedItem = cartItemService.updateCartItemPartial(itemId, updates);
//        return ResponseEntity.ok(updatedItem);
//    }
}
