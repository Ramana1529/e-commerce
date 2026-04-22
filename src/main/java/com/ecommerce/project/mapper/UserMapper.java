package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.RoleOfUser;
import com.ecommerce.project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequestDTO requestDTO){
        return User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .contactNumber(requestDTO.getContactNumber())
                .role(RoleOfUser.USER)
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .build();
    }

    public UserResponseDTO toDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}