package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.request.UserRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody RegisterRequestDTO dto){
        return userService.register(dto);
    }
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@RequestBody RegisterRequestDTO registerRequestDTO,@PathVariable Long userId){
        return userService.updateUser(registerRequestDTO,userId);
    }
}

