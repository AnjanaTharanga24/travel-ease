package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest) throws NotFoundException {
        return userService.register(userRequest);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest loginRequest) throws NotFoundException{
        return userService.login(loginRequest);
    }
}
