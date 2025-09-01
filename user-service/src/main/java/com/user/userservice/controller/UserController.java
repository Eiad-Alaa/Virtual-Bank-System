package com.user.userservice.controller;

import com.user.userservice.dto.*;
import com.user.userservice.producer.LogProducer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.user.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogProducer logProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest) {
        logAsJson(registerRequest, "Request");
        UserResponse user = userService.registerUser(registerRequest);
        logAsJson(user, "Response");
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        logAsJson(loginRequest, "Request");
        UserResponse user = userService.loginUser(loginRequest);
        logAsJson(user, "Response");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{UserId}/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("UserId") String userId) {
        logAsJson(userId, "Request");
        UserProfileResponse userProfile = userService.getUserProfileById(UUID.fromString(userId));
        logAsJson(userProfile, "Response");
        return ResponseEntity.ok(userProfile);
    }

    private void logAsJson(Object obj, String type) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            logProducer.sendLog(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
