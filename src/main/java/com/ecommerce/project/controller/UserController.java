package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.request.UserRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(){
        return userService.getCurrentUser();
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @PutMapping("/me")
    public UserResponseDTO updateUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){
        return userService.updateUser(registerRequestDTO);
    }
}

