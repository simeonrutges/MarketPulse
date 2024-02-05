package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.service.CartItemService;
import com.example.MarketPulse.service.DtoMapperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

@PostMapping("/users/{userId}/cart/items")
public ResponseEntity<CartItemDto> addCartItem(@Valid @PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
    CartItemDto createdItem = cartItemService.addCartItem(userId, cartItemDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
}
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        cartItemService.removeCartItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@Valid @PathVariable Long itemId, @RequestBody CartItemDto cartItemDto) {
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
