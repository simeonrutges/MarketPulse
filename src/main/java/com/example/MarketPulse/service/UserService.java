package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.exceptions.UserNotFoundException;
import com.example.MarketPulse.exceptions.UsernameAlreadyExistsException;
import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CartRepository;
import com.example.MarketPulse.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final DtoMapperService dtoMapperService;

    public UserService(UserRepository userRepository, CartRepository cartRepository, DtoMapperService dtoMapperService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.dtoMapperService = dtoMapperService;
    }

        public String createUser(UserDto userDto) {

            if (userRepository.existsByUsernameIgnoreCase(userDto.getUsername())) {
                throw new UsernameAlreadyExistsException("Gebruikersnaam is al in gebruik.");
            }

            User newUser = dtoMapperService.dtoToUser(userDto);
            userRepository.save(newUser);

            return newUser.getUsername();
        }

    public UserDto getUserById(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    return dtoMapperService.userToDto(user);
}

    public UserDto getUserByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Gebruiker met gebruikersnaam " + username + " niet gevonden."));
        return dtoMapperService.userToDto(user);
    }


    public User updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Gebruiker met ID " + userId + " niet gevonden."));

        // Voer hier de bijwerkingslogica uit op basis van userDto en update existingUser

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        // Implementeer logica om een gebruiker te verwijderen op basis van hun ID
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        // Implementeer logica om een lijst van alle gebruikers op te halen
        return userRepository.findAll();
    }
//    ----
public void assignCartToUser(Long userId, Long cartId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden met ID: " + userId));
    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new UserNotFoundException("Winkelwagen niet gevonden met ID: " + cartId));

    user.setCart(cart);
    userRepository.save(user);
}
}
