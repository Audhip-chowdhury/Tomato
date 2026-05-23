package com.tomato.service;

import com.tomato.dto.AuthRequest;
import com.tomato.dto.AuthResponse;
import com.tomato.dto.RegisterRequest;
import com.tomato.dto.UserDTO;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    UserDTO getCurrentUser(String email);
}
