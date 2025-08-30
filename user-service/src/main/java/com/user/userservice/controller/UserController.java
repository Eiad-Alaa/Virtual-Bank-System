package com.user.userservice.controller;

import com.user.userservice.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.user.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest) {
        UserResponse user = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        UserResponse user = userService.loginUser(loginRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{UserId}/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("UserId") String userId) {
        UserProfileResponse userProfile = userService.getUserProfileById(UUID.fromString(userId));
        return ResponseEntity.ok(userProfile);
    }

}
