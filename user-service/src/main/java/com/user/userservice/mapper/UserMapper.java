package com.user.userservice.mapper;

import com.user.userservice.dto.UserProfileResponse;
import com.user.userservice.dto.UserRegisterRequest;
import com.user.userservice.dto.UserResponse;
import com.user.userservice.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getUsername());
    }

    public UserProfileResponse toUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
    public User toUserEntity(UserRegisterRequest request) {
        return User.builder().
                username(request.getUsername()).
                passwordHash(encoder.encode(request.getPassword())).
                email(request.getEmail()).
                firstName(request.getFirstName()).
                lastName(request.getLastName()).
                isActive(true).
                build();
    }
}
