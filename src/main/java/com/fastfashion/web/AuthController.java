package com.fastfashion.web;

import com.fastfashion.domain.User;
import com.fastfashion.security.JwtService;
import com.fastfashion.service.UserService;
import com.fastfashion.web.dto.AuthDtos.LoginRequest;
import com.fastfashion.web.dto.AuthDtos.RegisterRequest;
import com.fastfashion.web.dto.AuthDtos.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest req) {
        User u = userService.register(req.email, req.password, req.name, req.phone);
        String token = jwtService.generateToken(u.getEmail());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        var user = userService.loadUserByUsername(req.email);
        if (!userService.verifyPassword(req.password, user.getPassword()))
            throw new BadCredentialsException("Invalid credentials");
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        String token = jwtService.generateToken(req.email);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
