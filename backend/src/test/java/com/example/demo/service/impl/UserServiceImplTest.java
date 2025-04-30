package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_WithNewUser_ShouldReturnUserResponse() throws CustomException {
        UserRequest request = new UserRequest("John Doe", "john@example.com", "johndoe", "password");
        User savedUser = User.builder()
                .id("1")
                .name("John Doe")
                .email("john@example.com")
                .username("johndoe")
                .password("password")
                .build();
        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("johndoe", response.getUsername());

        verify(userRepository, times(1)).existsByUsername("johndoe");
        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_WithExistingUsername_ShouldThrowCustomException() {
        UserRequest request = new UserRequest("John Doe", "john@example.com", "johndoe", "password");

        when(userRepository.existsByUsername("johndoe")).thenReturn(true);
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.register(request));

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByUsername("johndoe");
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_WithExistingEmail_ShouldThrowCustomException() {
        UserRequest request = new UserRequest("John Doe", "john@example.com", "johndoe", "password");

        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class,
                () -> userService.register(request));

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByUsername("johndoe");
        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnUserResponse() throws CustomException {
        LoginRequest request = new LoginRequest("johndoe", "password");
        User user = User.builder()
                .id("1")
                .name("John Doe")
                .email("john@example.com")
                .username("johndoe")
                .password("password")
                .build();

        when(userRepository.findByUsernameAndPassword("johndoe", "password"))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("johndoe", response.getUsername());

        verify(userRepository, times(1))
                .findByUsernameAndPassword("johndoe", "password");
    }

    @Test
    void login_WithInvalidCredentials_ShouldThrowCustomException() {
        LoginRequest request = new LoginRequest("johndoe", "wrongpassword");

        when(userRepository.findByUsernameAndPassword("johndoe", "wrongpassword"))
                .thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> userService.login(request));

        assertEquals("Invalid username or password", exception.getMessage());
        verify(userRepository, times(1))
                .findByUsernameAndPassword("johndoe", "wrongpassword");
    }
}