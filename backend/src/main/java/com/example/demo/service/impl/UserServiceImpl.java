package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.DuplicateEntryException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(UserRequest userRequest) throws CustomException {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new CustomException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new CustomException("Email already exists");
        }

        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .build();
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) throws CustomException {
        return userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .orElseThrow(() -> new CustomException("Invalid username or password"));
    }
}