package com.user.userservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.user.userservice.repository.UserRepository;
import com.user.userservice.entity.User;
import com.user.userservice.mapper.UserMapper;
import com.user.userservice.dto.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.user.userservice.util.*;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserResponse registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username or email already exists");
        }
        User user = userMapper.toUserEntity(request);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserProfileResponse getUserProfileById(UUID userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return userMapper.toUserProfileResponse(user);
    }

    public UserResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                    .filter(u -> encoder.matches(request.getPassword(), u.getPasswordHash()))
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        return userMapper.toUserResponse(user);
    }


}
