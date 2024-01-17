package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoMapperService dtoMapperService;

    public UserService(UserRepository userRepository, DtoMapperService dtoMapperService) {
        this.userRepository = userRepository;
        this.dtoMapperService = dtoMapperService;
    }
//    public String createUser(UserDto userDto) {
//        // Implementeer logica om een nieuwe gebruiker aan te maken op basis van UserDto
//        User newUser = dtoMapperService.dtoToUser(userDto);
//        userRepository.save(newUser);
//
//        return "Done";

        public String createUser(UserDto userDto) {
            User newUser = dtoMapperService.dtoToUser(userDto);
            userRepository.save(newUser);

            return newUser.getUsername();
        }

        public UserDto getUserById (Long userId){
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return dtoMapperService.userToDto(user);
            }
            return null;
        }
    public User getUserByUsername(String username) {
        // Implementeer logica om een gebruiker op te halen op basis van hun gebruikersnaam
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Gebruiker met gebruikersnaam " + username + " niet gevonden."));
    }

    public User updateUser(Long userId, UserDto userDto) {
        // Implementeer logica om een bestaande gebruiker bij te werken met behulp van UserDto
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
    }
