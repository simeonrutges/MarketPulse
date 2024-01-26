package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CartItemRepository;
import com.example.MarketPulse.repository.CartRepository;
import com.example.MarketPulse.repository.ProductRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, DtoMapperService dtoMapperService, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
    }

//    // Methode om een item toe te voegen aan een winkelwagen
//    public void addCartItem(CartItemDto cartItemDto) {
//        CartItem cartItem = dtoMapperService.cartItemDtoToCartItem(cartItemDto);
//
//        // Je moet mogelijk de cart en product ophalen op basis van hun ID's uit de DTO
//        Cart cart = cartRepository.findById(cartItemDto.getCartId())
//                .orElseThrow(() -> new ResourceNotFoundException("Winkelwagen niet gevonden met ID: " + cartItemDto.getCartId()));
//        Product product = productRepository.findById(cartItemDto.getProductId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product niet gevonden met ID: " + cartItemDto.getProductId()));
//
//        cartItem.setCart(cart);
//        cartItem.setProduct(product);
//        // Stel eventuele andere velden in die je nodig hebt vanuit de DTO
//
//        cartItemRepository.save(cartItem);
//    }

    public CartItemDto addCartItem(Long userId, CartItemDto cartItemDto) {
        // Zoek de gebruiker en zijn/haar winkelwagen
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + userId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(user));

        // Converteer CartItemDto naar CartItem entiteit
        CartItem cartItem = dtoMapperService.cartItemDtoToCartItem(cartItemDto);
        cartItem.setCart(cart);

        // Voeg CartItem toe en sla het op
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        // Converteer het opgeslagen CartItem terug naar CartItemDto
        return dtoMapperService.cartItemToCartItemDto(savedCartItem);
    }

    private Cart createCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }


    // Overige methoden voor het ophalen, bijwerken, verwijderen van winkelwagenitems ...
}
