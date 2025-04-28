package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserResponse register(UserRequest userRequest) throws NotFoundException {

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        User registeredUser = userRepository.save(user);

        return UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .username(registeredUser.getUsername())
                .password(registeredUser.getPassword())
                .build();
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) throws NotFoundException {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Optional<User> userOptional = userRepository.findUserByUsernameAndAndPassword(username,password);

        if (!userOptional.isPresent()){
            throw new NotFoundException("invalid credentials");
        }

        User loggedUser = userOptional.get();

        return UserResponse.builder()
                .id(loggedUser.getId())
                .name(loggedUser.getName())
                .email(loggedUser.getEmail())
                .username(loggedUser.getUsername())
                .password(loggedUser.getPassword())
                .build();
    }
}
