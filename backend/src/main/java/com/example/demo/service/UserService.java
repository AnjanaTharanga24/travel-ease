package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.DuplicateEntryException;
import com.example.demo.exception.NotFoundException;

public interface UserService {
    UserResponse register(UserRequest userRequest) throws CustomException;
    UserResponse login(LoginRequest loginRequest) throws CustomException;
}