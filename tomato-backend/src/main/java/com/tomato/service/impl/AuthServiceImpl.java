package com.tomato.service.impl;

import com.tomato.config.JwtConfig;
import com.tomato.dto.AuthRequest;
import com.tomato.dto.AuthResponse;
import com.tomato.dto.RegisterRequest;
import com.tomato.dto.UserDTO;
import com.tomato.exception.DuplicateEmailException;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.exception.UnauthorizedException;
import com.tomato.model.Role;
import com.tomato.model.User;
import com.tomato.repository.UserRepository;
import com.tomato.service.AuthService;
import com.tomato.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .phone(request.getPhone())
                .build();

        user = userRepository.save(user);
        String token = jwtConfig.generateToken(user.getEmail(), user.getRole().name());
        return MapperUtil.toAuthResponse(user, token);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtConfig.generateToken(user.getEmail(), user.getRole().name());
        return MapperUtil.toAuthResponse(user, token);
    }

    @Override
    public UserDTO getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return MapperUtil.toUserDTO(user);
    }
}
