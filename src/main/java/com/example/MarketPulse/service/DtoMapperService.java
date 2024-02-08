package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.*;
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

    public DtoMapperService(RoleRepository roleRepos) {
        this.roleRepos = roleRepos;
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

    public Cart dtoToCart(CartDto cartDto, User user, List<CartItem> cartItems) {
        Cart cart = new Cart();
        cart.setId(cartDto.id);
        cart.setUser(user);
        cart.setItems(cartItems);

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

    public CartItem cartItemDtoToCartItem(CartItemDto cartItemDto, Cart cart, Product product, Order order) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.id);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setOrder(order); // Kan null zijn, afhankelijk van of de order is opgegeven
        cartItem.setQuantity(cartItemDto.getQuantity());
        double totalPrice = product.getPrice() * cartItemDto.getQuantity();
        cartItem.setPricePerUnit(totalPrice);

        return cartItem;
    }


    public Product productDtoToProduct(ProductDto productDto, User seller, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setSeller(seller);
        product.setCategory(category);

        product.setFileName(productDto.getFileName());
        product.setImageData(productDto.getImageData());

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

        productDto.setFileName(product.getFileName());
        productDto.setImageData(productDto.getImageData());

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

    public Order orderDtoToOrder(OrderDto dto, User buyer ,List<CartItem> cartItems) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setCartItems(cartItems);
        order.setTotalAmount(dto.getTotalAmount());
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : new Date());
        order.setStatus(dto.getStatus());

        return order;
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto, Order order, User user) {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setAmount(order.getTotalAmount());
        transaction.setTransactionDate(new Date());
        transaction.setStatus(transactionDto.getStatus());
        transaction.setUser(user);

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

    public Review reviewDtoToReview(ReviewDto reviewDto, User reviewer, Product product){
        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setReviewer(reviewer);
        review.setProduct(product);

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

