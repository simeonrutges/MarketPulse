package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.model.Role;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.RoleRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DtoMapperService {
    private final RoleRepository roleRepos;
    private final UserRepository userRepository;

    public DtoMapperService(RoleRepository roleRepos, UserRepository userRepository) {
        this.roleRepos = roleRepos;
        this.userRepository = userRepository;
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

    private static String[] getRoleNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getRolename)
                .toArray(String[]::new);
    }
}
