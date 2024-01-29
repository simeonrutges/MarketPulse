package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.*;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.*;
import com.example.MarketPulse.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DtoMapperService {
    private final RoleRepository roleRepos;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public DtoMapperService(RoleRepository roleRepos, UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CategoryRepository categoryRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository) {
        this.roleRepos = roleRepos;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
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
        cartItemDto.setPricePerUnit(cartItem.getPricePerUnit());

        return cartItemDto;
    }

//    public CartItem cartItemDtoToCartItem(CartItemDto cartItemDto) {
//        CartItem cartItem = new CartItem();
//        cartItem.setId(cartItemDto.id);
//
//        if (cartItemDto.cartId != null) {
//            Cart cart = cartRepository.findById(cartItemDto.cartId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartItemDto.cartId));
//            cartItem.setCart(cart);
//        }
//
//        if (cartItemDto.productId != null) {
//            Product product = productRepository.findById(cartItemDto.productId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + cartItemDto.productId));
//            cartItem.setProduct(product);
//        }
//
//        // Optioneel: Order afhandeling, afhankelijk van domeinlogica
//        // if (cartItemDto.orderId != null) { ... }
//        if (cartItemDto.orderId != null) {
//            Order order = orderRepository.findById(cartItemDto.orderId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + cartItemDto.orderId));
//            cartItem.setOrder(order);
//        }
//
//        cartItem.setQuantity(cartItemDto.quantity);
//        cartItem.setPrice(cartItemDto.getPrice());
//
//        return cartItem;
//    }
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

        // Bereken de prijs op basis van productprijs en hoeveelheid
        double totalPrice = product.getPrice() * cartItemDto.getQuantity();
        cartItem.setPricePerUnit(totalPrice);
    }

    if (cartItemDto.orderId != null) {
        Order order = orderRepository.findById(cartItemDto.orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + cartItemDto.orderId));
        cartItem.setOrder(order);
    }

    cartItem.setQuantity(cartItemDto.getQuantity());

    return cartItem;
}


    public Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        if (productDto.getSellerId() != null) {
            User seller = userRepository.findById(productDto.getSellerId())
                    .orElseThrow(() -> new RuntimeException("Verkoper niet gevonden met ID: " + productDto.getSellerId()));
            product.setSeller(seller);
        }

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categorie niet gevonden met ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        return product;
    }

    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        if (product.getSeller() != null) {
            productDto.setSellerId(product.getSeller().getId());
        }

        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }

        return productDto;
    }

    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        if (order.getBuyer() != null) {
            orderDto.setBuyerId(order.getBuyer().getId());
        }
        if (order.getCartItems() != null) {
            orderDto.setCartItemIds(order.getCartItems().stream()
                    .map(CartItem::getId)
                    .collect(Collectors.toList()));
        }
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }

    // Van OrderDto naar Order
    public Order orderDtoToOrder(OrderDto dto) {
        Order order = new Order();

        if (dto.getId() != null) {
            order.setId(dto.getId());
        }

        if (dto.getBuyerId() != null) {
            User buyer = userRepository.findById(dto.getBuyerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + dto.getBuyerId()));
            order.setBuyer(buyer);
        }

        if (dto.getCartItemIds() != null && !dto.getCartItemIds().isEmpty()) {
            List<CartItem> cartItems = dto.getCartItemIds().stream()
                    .map(id -> cartItemRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("CartItem niet gevonden met ID: " + id)))
                    .collect(Collectors.toList());
            order.setCartItems(cartItems);
        }

        order.setTotalAmount(dto.getTotalAmount());
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : new Date());
        order.setStatus(dto.getStatus());

        return order;
    }



}

