package com.tomato.service;

import com.tomato.config.JwtConfig;
import com.tomato.dto.AuthRequest;
import com.tomato.dto.AuthResponse;
import com.tomato.dto.RegisterRequest;
import com.tomato.exception.DuplicateEmailException;
import com.tomato.exception.UnauthorizedException;
import com.tomato.model.Role;
import com.tomato.model.User;
import com.tomato.repository.UserRepository;
import com.tomato.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtConfig jwtConfig;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@tomato.com")
                .password("encodedPassword")
                .role(Role.USER)
                .phone("9876543210")
                .build();
    }

    @Test
    void register_success() {
        RegisterRequest request = RegisterRequest.builder()
                .name("Test User")
                .email("test@tomato.com")
                .password("password123")
                .phone("9876543210")
                .build();

        when(userRepository.existsByEmail("test@tomato.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtConfig.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("test@tomato.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicateEmail_throwsException() {
        RegisterRequest request = RegisterRequest.builder()
                .name("Test User")
                .email("test@tomato.com")
                .password("password123")
                .build();

        when(userRepository.existsByEmail("test@tomato.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> authService.register(request));
    }

    @Test
    void login_success() {
        AuthRequest request = AuthRequest.builder()
                .email("test@tomato.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtConfig.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("Test User", response.getName());
    }

    @Test
    void login_wrongPassword_throwsException() {
        AuthRequest request = AuthRequest.builder()
                .email("test@tomato.com")
                .password("wrongpassword")
                .build();

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> authService.login(request));
    }
}
