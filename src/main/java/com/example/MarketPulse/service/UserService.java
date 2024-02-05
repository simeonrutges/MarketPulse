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
import java.util.stream.Collectors;


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


    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Gebruiker met ID " + userId + " niet gevonden."));

        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword(userDto.getPassword());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        // Voeg hier nog logica toe voor het bijwerken van rollen, productIds, orderIds, reviewIds, en cartId
        // Opmerking: Dit zou complexer zijn, afhankelijk van hoe je relaties en eigendom beheert in je domein.
        // Bijvoorbeeld, het toevoegen/verwijderen van items uit lijsten kan vereisen dat je de huidige staat van de lijsten controleert,
        // items toevoegt die er nog niet zijn, en items verwijdert die niet langer meegegeven worden.

        User updatedUser = userRepository.save(existingUser);

        return dtoMapperService.userToDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(dtoMapperService :: userToDto)
                .collect(Collectors.toList());
    }

public void assignCartToUser(Long userId, Long cartId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden met ID: " + userId));
    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new UserNotFoundException("Winkelwagen niet gevonden met ID: " + cartId));

    user.setCart(cart);
    userRepository.save(user);
}
}
