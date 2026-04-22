package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.LoginRequestDTO;
import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.response.AuthResponseDTO;
import com.ecommerce.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}