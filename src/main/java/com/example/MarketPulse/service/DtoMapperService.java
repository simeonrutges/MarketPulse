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
    private final TransactionRepository transactionRepository;

    public DtoMapperService(RoleRepository roleRepos, UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CategoryRepository categoryRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.roleRepos = roleRepos;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
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

// Lambda:
//        Optional.ofNullable(cart.getUser())
//                .ifPresent(user -> cartDto.userId = user.getId());
//
//        cartDto.items = Optional.ofNullable(cart.getItems())
//                .map(items -> items.stream()
//                        .map(this::cartItemToCartItemDto) // Je moet deze methode implementeren
//                        .collect(Collectors.toList()))
//                .orElse(new ArrayList<>());

        if (cart.getUser() != null) {
            cartDto.userId = cart.getUser().getId();
        }

        if (cart.getItems() != null) {
            cartDto.items = cart.getItems().stream()
                    .map(this::cartItemToCartItemDto)
                    .collect(Collectors.toList());
        } else {
            cartDto.items = new ArrayList<>(); // Initialiseer met een lege lijst als cart.getItems() null is
        }

        return cartDto;
    }

    public Cart dtoToCart(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setId(cartDto.id);

        if (cartDto.userId != null) {
            User user = userRepository.findById(cartDto.userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + cartDto.userId));
            cart.setUser(user);
        }
// Lambda
//        cart.setItems(Optional.ofNullable(cartDto.items)
//                .map(items -> items.stream()
//                        .map(this::cartItemDtoToCartItem) // Je moet deze methode implementeren
//                        .collect(Collectors.toList()))
//                .orElse(new ArrayList<>()));
        if (cartDto.items != null) {
            List<CartItem> cartItems = cartDto.items.stream()
                    .map(this::cartItemDtoToCartItem)
                    .collect(Collectors.toList());
            cart.setItems(cartItems);
        } else {
            cart.setItems(new ArrayList<>());
        }


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
//                    .orElseThrow(() -> new RuntimeException("Verkoper niet gevonden met ID: " + productDto.getSellerId()));
                    .orElseThrow(() -> new ResourceNotFoundException("Verkoper niet gevonden met ID: " + productDto.getSellerId()));
            product.setSeller(seller);
        }

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
//                    .orElseThrow(() -> new RuntimeException("Categorie niet gevonden met ID: " + productDto.getCategoryId()));
                    .orElseThrow(() -> new ResourceNotFoundException("Categorie niet gevonden met ID: " + productDto.getCategoryId()));
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

    // Van OrderDto naar Order ---> niet nodig
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


    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();

        // Omdat id meestal door de database wordt gegenereerd, moet u het wellicht niet instellen.
        // transaction.setId(transactionDto.getId()); // Alleen nodig als u bestaande transacties bijwerkt.

//        transaction.setAmount(transactionDto.getAmount());
        if (transactionDto.getOrderId() != null) {
            Order order = orderRepository.findById(transactionDto.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + transactionDto.getOrderId()));
            transaction.setOrder(order);
            transaction.setAmount(order.getTotalAmount());  // Stel het transactiebedrag in op het totale bedrag van de order
        }

        transaction.setTransactionDate(new Date());
        transaction.setStatus(transactionDto.getStatus());

        // U moet de gebruiker en order entiteiten ophalen en koppelen op basis van de ID's in transactionDto.
        // Dit vereist toegang tot de gebruikers- en orderrepository's.
        if (transactionDto.getUserId() != null) {
            User user = userRepository.findById(transactionDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + transactionDto.getUserId()));
            transaction.setUser(user);
        }

//        if (transactionDto.getOrderId() != null) {
//            Order order = orderRepository.findById(transactionDto.getOrderId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + transactionDto.getOrderId()));
//            transaction.setOrder(order);
//        }

        return transaction;
    }


    public TransactionDto transactionToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setId(transaction.getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setStatus(transaction.getStatus());

        if (transaction.getUser() != null) {
            transactionDto.setUserId(transaction.getUser().getId());
        }

        if (transaction.getOrder() != null) {
            transactionDto.setOrderId(transaction.getOrder().getId());
        }

        return transactionDto;
    }

    public Category categoryDtoToCategory (CategoryDto categoryDto){
        Category category = new Category();

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return category;
    }


    public CategoryDto categoryToDto (Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        return categoryDto;
    }
    public void updateCategoryFromDto(Category category, CategoryDto categoryDto) {
        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }
    }

    public Review reviewDtoToReview(ReviewDto reviewDto){
        Review review = new Review();

        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());

        if (reviewDto.getReviewerId() != null) {
            User reviewer = userRepository.findById(reviewDto.getReviewerId())
                    .orElseThrow(() -> new RuntimeException("Reviewer not found with ID: " + reviewDto.getReviewerId()));
            review.setReviewer(reviewer);
        }

        if (reviewDto.getProductId() != null) {
            Product product = productRepository.findById(reviewDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + reviewDto.getProductId()));
            review.setProduct(product);
        }

        return review;
    }

    public ReviewDto reviewToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(review.getId());
        reviewDto.setComment(review.getComment());
        reviewDto.setRating(review.getRating());

        if (review.getReviewer() != null) {
            reviewDto.setReviewerId(review.getReviewer().getId());
        }
        if (review.getProduct() != null) {
            reviewDto.setProductId(review.getProduct().getId());
        }

        return reviewDto;
    }

}

