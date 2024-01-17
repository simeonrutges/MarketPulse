package com.example.MarketPulse.controller;
//
//import com.example.MarketPulse.dto.UserDto;
//import com.example.MarketPulse.model.User;
//import com.example.MarketPulse.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import java.net.URI;
//import java.util.List;


import com.example.MarketPulse.dto.UserDto;
import com.example.MarketPulse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;



@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.passwordEncoder = encoder;
    }

    @PostMapping("")
    public ResponseEntity<String> klant(@Valid @RequestBody UserDto dto, BindingResult br) {

        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {

            dto.setPassword(passwordEncoder.encode(dto.password));

            String newUsername = userService.createUser(dto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(newUsername).toUri();

            return ResponseEntity.created(location).body(newUsername);
        }
    }
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
//        UserDto userDto = userService.getUserById(userId);
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }

//    @GetMapping("/username/{username}")
//    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
//        UserDto userDto = userService.getUserByUsername(username);
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
//        UserDto updatedUserDto = userService.updateUser(userId, userDto);
//        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//        userService.deleteUser(userId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<UserDto> userDtos = userService.getAllUsers();
//        return new ResponseEntity<>(userDtos, HttpStatus.OK);
//    }

}

