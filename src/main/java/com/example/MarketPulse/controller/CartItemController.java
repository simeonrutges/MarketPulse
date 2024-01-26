package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.service.CartItemService;
import com.example.MarketPulse.service.DtoMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cartItems")
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





    // Overige methoden ...
}
