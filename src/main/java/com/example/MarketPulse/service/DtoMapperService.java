package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.model.User;
import org.springframework.stereotype.Service;

@Service
public class DtoMapperService {
    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.username = user.getUsername();
        userDto.email = user.getEmail();
        // Andere velden toewijzen

        return userDto;
    }

    public User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.id);
        user.setUsername(userDto.username);
        user.setPassword(userDto.password);
        user.setEmail(userDto.email);
        // Andere velden toewijzen

        return user;
    }
}
