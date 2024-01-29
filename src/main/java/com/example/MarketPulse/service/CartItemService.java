package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.*;
import com.example.MarketPulse.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, DtoMapperService dtoMapperService, UserRepository userRepository, OrderRepository orderRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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

//    public CartItemDto addCartItem(Long userId, CartItemDto cartItemDto) {
//        // Zoek de gebruiker en zijn/haar winkelwagen
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + userId));
//
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseGet(() -> createCartForUser(user));
//
//        // Converteer CartItemDto naar CartItem entiteit
//        CartItem cartItem = dtoMapperService.cartItemDtoToCartItem(cartItemDto);
//        cartItem.setCart(cart);
//
//        // Voeg CartItem toe en sla het op
//        CartItem savedCartItem = cartItemRepository.save(cartItem);
//        // Converteer het opgeslagen CartItem terug naar CartItemDto
//        return dtoMapperService.cartItemToCartItemDto(savedCartItem);
//    }

    public CartItemDto addCartItem(Long userId, CartItemDto cartItemDto) {
        // Zoek de gebruiker en zijn/haar winkelwagen
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + userId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(user));

        // Haal het product op
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product niet gevonden met ID: " + cartItemDto.getProductId()));

        // Bereken de totale prijs voor het CartItem
        double totalPrice = product.getPrice() * cartItemDto.getQuantity();

        // Converteer CartItemDto naar CartItem entiteit
        CartItem cartItem = dtoMapperService.cartItemDtoToCartItem(cartItemDto);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setPricePerUnit(product.getPrice()); // Gebruik de berekende prijs

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
///////
    @Transactional
    public void removeCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
    @Transactional
    public CartItemDto updateCartItem(Long itemId, CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem niet gevonden met ID: " + itemId));

        // Bijwerken van de Cart relatie
        if (cartItemDto.getCartId() != null) {
            Cart cart = cartRepository.findById(cartItemDto.getCartId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart niet gevonden met ID: " + cartItemDto.getCartId()));
            cartItem.setCart(cart);
        }

        // Bijwerken van de Product relatie
        if (cartItemDto.getProductId() != null) {
            Product product = productRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product niet gevonden met ID: " + cartItemDto.getProductId()));
            cartItem.setProduct(product);
        }

        // Bijwerken van de Order relatie (als het optioneel is)
        if (cartItemDto.getOrderId() != null) {
            Order order = orderRepository.findById(cartItemDto.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order niet gevonden met ID: " + cartItemDto.getOrderId()));
            cartItem.setOrder(order);
        } else {
            cartItem.setOrder(null); // Als orderId null is, verwijder de koppeling
        }
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setPricePerUnit(cartItemDto.getPricePerUnit());


        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return dtoMapperService.cartItemToCartItemDto(updatedCartItem);
    }

//    @Transactional
//    public CartItemDto updateCartItemPartial(Long itemId, Map<String, Object> updates) {
//        CartItem cartItem = cartItemRepository.findById(itemId)
//                .orElseThrow(() -> new ResourceNotFoundException("CartItem niet gevonden met ID: " + itemId));
//
//        // Pas de velden van cartItem aan op basis van de 'updates' map
//        // Dit vereist een specifieke logica afhankelijk van de structuur van CartItem
//        // Bijvoorbeeld: als (updates.containsKey("quantity")) { cartItem.setQuantity((Integer) updates.get("quantity")); }
//
//        CartItem updatedCartItem = cartItemRepository.save(cartItem);
//        return dtoMapperService.cartItemToCartItemDto(updatedCartItem);
//    }
}
