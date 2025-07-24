package com.garage.service;

import com.garage.entity.User;
import com.garage.repository.UserRepository;
import com.garage.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // AuthenticationManager for handling authentication
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenProvider;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // Constructor to inject dependencies
    // AuthenticationManager is used to authenticate users
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtTokenProvider,
                       UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to authenticate user and return JWT token
    // It uses AuthenticationManager to authenticate the user with username and password
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.generateToken(username);
    }

    // Method to register a new user
    // It encodes the user's password and saves the user to the repository
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
