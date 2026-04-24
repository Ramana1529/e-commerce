package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.Role;
import com.ecommerce.project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequestDTO requestDTO){
        return User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .phoneNumber(requestDTO.getContactNumber())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .build();
    }

    public UserResponseDTO toDTO(User user){
        if (user == null) return null;
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .contactNumber(user.getPhoneNumber())
                .role(user.getRoles() != null
                        ? user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet())
                        : Set.of())
                .createdAt(user.getCreatedAt())
                .build();
    }
}