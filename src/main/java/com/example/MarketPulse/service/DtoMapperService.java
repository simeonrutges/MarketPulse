package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.CartDto;
import com.example.MarketPulse.dto.CartItemDto;
import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.*;
import com.example.MarketPulse.repository.CartRepository;
import com.example.MarketPulse.repository.ProductRepository;
import com.example.MarketPulse.repository.RoleRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DtoMapperService {
    private final RoleRepository roleRepos;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public DtoMapperService(RoleRepository roleRepos, UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.roleRepos = roleRepos;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.username = user.getUsername();
        userDto.email = user.getEmail();
//        userDto.setRoles(getRoleNames((List<Role>) user.getRoles()));
        userDto.setRoles(List.of(getRoleNames((List<Role>) user.getRoles())));

        return userDto;
    }

    public User dtoToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.id);
        user.setUsername(userDto.username);
        user.setPassword(userDto.password);
        user.setEmail(userDto.email);

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById(rolename);

            userRoles.add(or.get());
        }
        user.setRoles(userRoles);

        return user;
    }

    public CartDto cartToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.id = cart.getId();

        Optional.ofNullable(cart.getUser())
                .ifPresent(user -> cartDto.userId = user.getId());

        cartDto.items = Optional.ofNullable(cart.getItems())
                .map(items -> items.stream()
                        .map(this::cartItemToCartItemDto) // Je moet deze methode implementeren
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        return cartDto;
    }

    public Cart dtoToCart(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setId(cartDto.id);

        if (cartDto.userId != null) {
            User user = new User();
            user.setId(cartDto.userId);
            cart.setUser(user);
        }

        cart.setItems(Optional.ofNullable(cartDto.items)
                .map(items -> items.stream()
                        .map(this::cartItemDtoToCartItem) // Je moet deze methode implementeren
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>()));

        return cart;
    }

    private static String[] getRoleNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getRolename)
                .toArray(String[]::new);
    }
    public CartItemDto cartItemToCartItemDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.id = cartItem.getId();
        cartItemDto.cartId = cartItem.getCart() != null ? cartItem.getCart().getId() : null;
        cartItemDto.productId = cartItem.getProduct() != null ? cartItem.getProduct().getId() : null;
        cartItemDto.orderId = cartItem.getOrder() != null ? cartItem.getOrder().getId() : null;
        cartItemDto.quantity = cartItem.getQuantity();
        cartItemDto.price = cartItem.getPrice();

        return cartItemDto;
    }
    public CartItem cartItemDtoToCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.id);

        if (cartItemDto.cartId != null) {
            Cart cart = cartRepository.findById(cartItemDto.cartId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartItemDto.cartId));
            cartItem.setCart(cart);
        }

        if (cartItemDto.productId != null) {
            Product product = productRepository.findById(cartItemDto.productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + cartItemDto.productId));
            cartItem.setProduct(product);
        }

        // Optioneel: Order afhandeling, afhankelijk van domeinlogica
        // if (cartItemDto.orderId != null) { ... }

        cartItem.setQuantity(cartItemDto.quantity);
        cartItem.setPrice(cartItemDto.price);

        return cartItem;
    }

}
